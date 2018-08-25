package com.gzsoftware.pet.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.PayProduct;
import com.gzsoftware.pet.service.AlipayService;
import com.gzsoftware.pet.service.UserBalanceLogService;
import com.gzsoftware.pet.utils.AlipayConfigUtil;
import com.gzsoftware.pet.utils.WechatPayCommonUtil;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("alipay")
public class AlipayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);
	@Autowired
	private AlipayService alipayService;
	@Resource
	private UserBalanceLogService userBalanceLogService;

	/**
	 * 微信支付: 扫二维码支付调用入口
	 * 
	 * @param product
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public void pay(HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[ALIPAY][PAY][CALL] 支付宝二维码支付接口调用");

		// 登录用户检查
		User currentUser = super.getCurrentUser(request.getSession());
		if (currentUser == null) {
			httpResponse.setContentType("text/html;charset=UTF-8");
			httpResponse.getWriter().write("会话超时，请重新登陆！！");// 直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}

		Double testFee = Double.parseDouble("0.01"); // 这里设置测试值为1分钱
		Double totalFeeD = testFee * 100; // 支付宝使用元为单位, 这里是测试值,1分钱,进入数据库时改为1元

		if (request.getParameter("totalFee") != null) { // 如果前台传入数值
			totalFeeD = Double.parseDouble(request.getParameter("totalFee"));
		}
		String orderNo=null;
		if (request.getParameter("orderNum") != null) { // 订单号
			orderNo = request.getParameter("orderNum");
		}

		if (orderNo == null) {
			httpResponse.setContentType("text/html;charset=UTF-8");
			httpResponse.getWriter().write("订单号未生成！！");// 直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}

		String spbillCreateIp = getRequestIP(); // 获取发起电脑 ip

		PayProduct payProduct = new PayProduct();
		payProduct.setAttach(currentUser.getId().toString()); // 附加信息: 放充值用户id
		payProduct.setTotalFee(totalFeeD.toString()); // 充值金额
		payProduct.setBody("PET会员支付宝充值"); // 充值内容
		payProduct.setOutTradeNo(orderNo); // 订单号
		payProduct.setSpbillCreateIp(spbillCreateIp); // 调用者IP

		logger.info("[ALIPAY][PAY][CALL] 帐号: " + currentUser.getAccount() + " 会员ID: " + currentUser.getId() + "" + " 支付单号: " + orderNo + " IP: " + spbillCreateIp + " 金额: " + totalFeeD + " ");

		Long width = 400L;
		if (request.getParameter("width") != null) { // 如果前台传入二维码大小
			width = Long.parseLong(request.getParameter("width"));
		}
		String form = alipayService.pay(payProduct, width);
		httpResponse.setContentType("text/html;charset=UTF-8");
		httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
		httpResponse.getWriter().flush();
		httpResponse.getWriter().close();
	}

	/**
	 * 支付宝支付: 支付后 回调地址 该方法为支付宝自动回调接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 读取参数
		logger.info("[APLIPAY][NOTIFY][CALL] 支付宝二维码支付结果回调");
		
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
		//	valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		logger.info("[APLIPAY][NOTIFY][CALL] " + JSONObject.toJSONString(params).toString());
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfigUtil.ALIPAY_PUBLIC_KEY, AlipayConfigUtil.CHAR_SET,AlipayConfigUtil.SIGN_TYPE); // 调用SDK验证签名
		if (signVerified) {// 验证成功
			logger.info("[APLIPAY][NOTIFY][CALL] 验证成功!!!!");
			// 商户订单号
			String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 用户信息存放在回调参数里面
			String userId = new String(request.getParameter("passback_params").getBytes("ISO-8859-1"), "UTF-8");
			// 支付金额
			String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			if (tradeStatus.equals("TRADE_SUCCESS")) {
				UserBalanceLog userBalanceLog = new UserBalanceLog();
				userBalanceLog.setUserId(Integer.parseInt(userId));
				userBalanceLog.setChangeAmount(Double.parseDouble(totalAmount));

				userBalanceLog.setChangeType((short) 7); // 支付宝充值类型
				userBalanceLog.setChargeOrderNumber(outTradeNo); // 支付订单号
				userBalanceLog.setDescription("支付宝充值" + Double.parseDouble(totalAmount) + "元, 支付单号: " + outTradeNo + " ");// 充值描述
				userBalanceLog.setChangeTime(new Date());
				userBalanceLogService.addUserBalanceLog(userBalanceLog);
				
				logger.info("[APLIPAY][NOTIFY][RESULT] 支付宝扫码支付成功!  会员ID: " + userId + " 支付单号: " + outTradeNo + " 金额: " + totalAmount + "元");  
				response.getWriter().println("success");
			}

		} else {// 验证失败
			 logger.info("[APLIPAY][NOTIFY][RESULT] 通知签名验证失败!");  
			response.getWriter().println("fail");

		}

	}
}
