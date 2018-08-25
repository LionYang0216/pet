package com.gzsoftware.pet.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.gzsoftware.pet.utils.PropertiesUtil;

/**
 * wechat payment configs
 * @author pango leung
 *
 */
public class WechatPayConfigUtil {
	
	private static PropertiesUtil pu = new PropertiesUtil("/properties/wechat.properties");
	/**
	 * 项目配置
	 */
	public  static String APP_ID = 			pu.getProperty("APP_ID");				// 服务号的应用ID
	public  static String MCH_ID = 			pu.getProperty("MCH_ID");				// 商户号
	public  static String API_KEY=			pu.getProperty("API_KEY");				// API密钥
	public  static String NOTIFY_URL=		pu.getProperty("NOTIFY_URL") ;			// 回调地址
	public  static String SIGN_TYPE;												// 签名加密方式
	public  static String CERT_PATH ;												// 微信支付证书
	public  static String APP_SECRET;												// 服务号的应用密钥
	public  static String TOKEN;													// 服务号的配置token


	/**
	 * 公共配置
	 */
	public final static String TOKEN_URL = pu.getProperty("TOKEN_URL");						// 获取token接口(GET)
	public final static String OAUTH2_URL = pu.getProperty("OAUTH2_URL");					// oauth2授权接口(GET)
	public final static String REFRESH_TOKEN_URL = pu.getProperty("REFRESH_TOKEN_URL");		// 刷新access_token接口（GET）

	/**
	 * 微信支付接口
	 */
	public final static String UNIFIED_ORDER_URL = 				pu.getProperty("UNIFIED_ORDER_URL");	// 微信支付统一接口(POST)
	public final static String REFUND_URL = 					pu.getProperty("REFUND_URL");			// 微信退款接口(POST)
	public final static String CHECK_ORDER_URL = 				pu.getProperty("CHECK_ORDER_URL");		// 订单查询接口(POST)
	public final static String CLOSE_ORDER_URL = 				pu.getProperty("CLOSE_ORDER_URL");		// 关闭订单接口(POST)
	public final static String CHECK_REFUND_URL = 				pu.getProperty("CHECK_REFUND_URL");		// 退款查询接口(POST)
	public final static String DOWNLOAD_BILL_URL = 				pu.getProperty("DOWNLOAD_BILL_URL");	// 对账单接口(POST)
	public final static String SHORT_URL = 						pu.getProperty("SHORT_URL");			// 短链接转换接口(POST)
	public final static String REPORT_URL = 					pu.getProperty("REPORT_URL");			// 接口调用上报接口(POST)
	
	/**
	 * 基础参数
	 */
	public static void commonParams(SortedMap<Object, Object> packageParams) {
		// 账号信息
		String appid = WechatPayConfigUtil.APP_ID; // appid
		String mch_id = WechatPayConfigUtil.MCH_ID; // 商业号
		// 生成随机字符串
		String currTime = WechatPayCommonUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = WechatPayCommonUtil.buildRandom(4) + "";
		String nonce_str = strTime + strRandom;
		packageParams.put("appid", appid);// 公众账号ID
		packageParams.put("mch_id", mch_id);// 商户号
		packageParams.put("nonce_str", nonce_str);// 随机字符串
	}

	/**
	 * 该接口主要用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量，提升扫描速度和精确度
	 */
	@SuppressWarnings("rawtypes")
	public static void shorturl(String urlCode) {
		try {
			String key = WechatPayConfigUtil.API_KEY; // key

			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			WechatPayConfigUtil.commonParams(packageParams);
			packageParams.put("long_url", urlCode);// URL链接
			String sign = WechatPayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = WechatPayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(WechatPayConfigUtil.SHORT_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if ("SUCCESS".equals(returnCode)) {
				String resultCode = (String) map.get("return_code");
				if ("SUCCESS".equals(resultCode)) {
					urlCode = (String) map.get("short_url");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
