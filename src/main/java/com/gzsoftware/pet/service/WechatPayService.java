package com.gzsoftware.pet.service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.entity.vo.PayProduct;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.utils.CommonUtil;
import com.gzsoftware.pet.utils.WechatPayConfigUtil;
import com.gzsoftware.pet.utils.HttpUtil;
import com.gzsoftware.pet.utils.WechatPayCommonUtil;
import com.gzsoftware.pet.utils.XMLUtil;
import com.gzsoftware.pet.utils.ZxingUtils;

/**
 * 微信支付服务类
 * @author pango leung
 *
 */
@Service("wechatPayService")
public class WechatPayService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(WechatPayService.class);
	public Result weixinPay(PayProduct payProduct) {

		try {
			// 账号信息
			String key = WechatPayConfigUtil.API_KEY; // key
			String tradeType = "NATIVE";// 交易类型 原生扫码支付
			
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			WechatPayConfigUtil.commonParams(packageParams);
			packageParams.put("attach", payProduct.getAttach());					// 附加信息: 存储userid
			packageParams.put("body", payProduct.getBody());						// 商品描述
			packageParams.put("out_trade_no", payProduct.getOutTradeNo());			// 商户订单号
			String totalFee = payProduct.getTotalFee();
			totalFee = CommonUtil.subZeroAndDot(totalFee);
			packageParams.put("total_fee", totalFee);							// 总金额
			packageParams.put("spbill_create_ip", payProduct.getSpbillCreateIp());	// 发起人IP地址
			packageParams.put("notify_url", WechatPayConfigUtil.NOTIFY_URL);	// 回调地址
			packageParams.put("trade_type", tradeType);// 交易类型
			String sign = WechatPayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = WechatPayCommonUtil.getRequestXml(packageParams);
			
			logger.info("[WECHAT][PAY][CALL] requestXML: " + requestXML + " ");
			
			
			String resXml = HttpUtil.postData(WechatPayConfigUtil.UNIFIED_ORDER_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if ("SUCCESS".equals(returnCode)) {
				String resultCode = (String) map.get("result_code");
				if ("SUCCESS".equals(resultCode)) {
					logger.info("[WECHAT][PAY][RESULT] 订单号：{} 生成微信支付码成功! ", payProduct.getOutTradeNo());
					
					String urlCode = (String) map.get("code_url");
					logger.info("[WECHAT][PAY][RESULT] 二维码URL生成成功：" + payProduct.getOutTradeNo() + " qrCodeURL: " + urlCode + " ");
					
					WechatPayConfigUtil.shorturl(urlCode);			// 转换为短链接
					payProduct.setQrCodeUrl(urlCode);				// 赋值payProduct
					
					return new Result(Result.RESULT_FLAG_SUCCESS, "调用成功", payProduct);
				} else {
					String errCodeDes = (String) map.get("err_code_des");
					logger.error("[WECHAT][PAY][RESULT] 订单号：{}  生成微信支付码(系统)失败: {}", payProduct.getOutTradeNo(), errCodeDes);
					return new Result(Result.RESULT_FLAG_FAIL, "订单号：" + payProduct.getOutTradeNo() + "  生成微信支付码(系统)失败:" + errCodeDes);
				}
			} else {
				String returnMsg = (String) map.get("return_msg");
				logger.error("([WECHAT][PAY][RESULT] 订单号：{}  生成微信支付码(通信)失败: {}", payProduct.getOutTradeNo(), returnMsg);
				return new Result(Result.RESULT_FLAG_FAIL,"订单号：" + payProduct.getOutTradeNo() + "  生成微信支付码(通信)失败:" + returnMsg);
			}
		} catch (Exception e) {
			logger.error("[WECHAT][PAY][RESULT] 订单号：{}  生成微信支付码失败(系统异常))", payProduct.getOutTradeNo(), e);
			return new Result(Result.RESULT_FLAG_FAIL, "订单号：" + payProduct.getOutTradeNo() + "  生成微信支付码失败(系统异常):" + e);
		}

	}
}
