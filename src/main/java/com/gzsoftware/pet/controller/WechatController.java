package com.gzsoftware.pet.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.StreamUtil;
import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserBalanceLog;
import com.gzsoftware.pet.entity.vo.PayProduct;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UserBalanceLogService;
import com.gzsoftware.pet.service.WechatPayService;
import com.gzsoftware.pet.utils.WechatPayConfigUtil;
import com.gzsoftware.pet.utils.WechatPayCommonUtil;
import com.gzsoftware.pet.utils.XMLUtil;

/**
 * 这里放 A. 微信版入口 B.微信支付相关接口方法
 * 
 * @author pango leung
 *
 */
@Controller
@RequestMapping("/wechat")
public class WechatController extends BaseController {

	@Resource
	private WechatPayService wechatPayService;

	@Resource
	private UserBalanceLogService userBalanceLogService;

	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

	/**
	 * 微信产品列表页
	 * 
	 * @return
	 */
	@RequestMapping("/wechatIndex")
	public String wechatIndex() {
		return "/wechatIndex";
	}
	

	/**
	 * 微信支付: 扫二维码支付调用入口
	 * 
	 * @param product
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	@ResponseBody
	public Result pay(HttpServletRequest request) {

		logger.info("[WECHAT][PAY][CALL] 微信二维码支付接口调用");

		// 登录用户检查
		User currentUser = super.getCurrentUser(request.getSession());
		if (currentUser == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "会话超时，请重新登陆！！");
		}

		Double testFee = Double.parseDouble("0.01"); // 这里设置测试值为1分钱
		Double totalFeeD = testFee * 100; // 微信转换成分为单位, 这里是测试值,1分钱,进入数据库时改为1元

		if (request.getParameter("totalFee") != null) { // 如果前台传入数值
			totalFeeD = Double.parseDouble(request.getParameter("totalFee")) * 100;
		}
		String orderNo = null;
		if (request.getParameter("orderNum") != null) { // 订单号
			orderNo = request.getParameter("orderNum");
		}

		if (orderNo == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "订单号未生成！！");
		}

		String spbillCreateIp = getRequestIP(); // 获取发起电脑 ip

		PayProduct payProduct = new PayProduct();
		payProduct.setAttach(currentUser.getId().toString()); // 附加信息: 放充值用户id
		payProduct.setTotalFee(totalFeeD.toString()); // 充值金额
		payProduct.setBody("PET会员微信充值"); // 充值内容
		payProduct.setOutTradeNo(orderNo); // 订单号
		payProduct.setSpbillCreateIp(spbillCreateIp); // 调用者IP

		logger.info("[WECHAT][PAY][CALL] 帐号: " + currentUser.getAccount() + " 会员ID: " + currentUser.getId() + ""
				+ " 支付单号: " + orderNo + " IP: " + spbillCreateIp + " 金额: " + (totalFeeD / 100) + " ");

		Result result = wechatPayService.weixinPay(payProduct);
		return result;
	}

	/**
	 * 微信支付: 支付后 回调地址 该方法为微信自动回调接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 读取参数
		logger.info("[WECHAT][NOTIFY][CALL] 微信二维码支付结果回调");
		InputStream inputStream = request.getInputStream();
		StringBuffer sb = new StringBuffer();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());
		logger.info("[WECHAT][NOTIFY][CALL] Notify Result XML: " + m.toString() + "  ");

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);
			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		// 账号信息
		String key = WechatPayConfigUtil.API_KEY; // key
		// 判断签名是否正确
		if (WechatPayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			// 处理业务开始
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				String outTradeNo = (String) packageParams.get("out_trade_no"); // 支付单号(调用时传入)
				String totalFee = (String) packageParams.get("total_fee"); // 支付金额(调用时传入)
				String userId = (String) packageParams.get("attach"); // 会员ID (调用时传入)

				UserBalanceLog userBalanceLog = new UserBalanceLog();
				userBalanceLog.setUserId(Integer.parseInt(userId));
				userBalanceLog.setChangeAmount(Double.parseDouble(totalFee) / 100); // 单位转换,把分转换为元

				userBalanceLog.setChangeType((short) 6); // 微信充值类型
				userBalanceLog.setChargeOrderNumber(outTradeNo); // 微信支付订单号
				userBalanceLog
						.setDescription("微信充值" + Double.parseDouble(totalFee) / 100 + "元, 支付单号: " + outTradeNo + " ");// 充值描述
				userBalanceLog.setChangeTime(new Date());
				userBalanceLogService.addUserBalanceLog(userBalanceLog);

				logger.info("[WECHAT][NOTIFY][RESULT] 微信扫码支付成功!  会员ID: " + userId + " 支付单号: " + outTradeNo.toString()
						+ " 金额: " + Double.parseDouble(totalFee) / 100 + "元");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

			} else {
				logger.info("[WECHAT][NOTIFY][RESULT] 微信扫码支付失败! 错误信息: " + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			logger.info("[WECHAT][NOTIFY][RESULT] 通知签名验证失败!");
		}

	}

	@RequestMapping(value = "/getHeadInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getHeadInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return new Result(Result.RESULT_FLAG_SUCCESS, map);
	}

}
