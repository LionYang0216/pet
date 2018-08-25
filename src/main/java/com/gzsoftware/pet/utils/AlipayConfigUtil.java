package com.gzsoftware.pet.utils;

/**
 * put alipay config
 * @author pango leung
 *
 */
public class AlipayConfigUtil {

	private static PropertiesUtil pu = new PropertiesUtil("/properties/alipay.properties");


	public static String APP_ID = pu.getProperty("APP_ID"); 							// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	
	public static String MERCHANT_PRIVATE_KEY = pu.getProperty("MERCHANT_PRIVATE_KEY");	// 商户私钥，您的PKCS8格式RSA2私钥

	public static String ALIPAY_PUBLIC_KEY = pu.getProperty("ALIPAY_PUBLIC_KEY");		//支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
			
	public static String NOTIFY_URL = pu.getProperty("NOTIFY_URL");						// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

	public static String RETURN_URL= pu.getProperty("RETURN_URL");						// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，目前没有使用

	public static String SIGN_TYPE = pu.getProperty("SIGN_TYPE");						// 签名方式

	public static String CHAR_SET = pu.getProperty("CHAR_SET");							// 字符编码格式

	public static String FORTMAT = pu.getProperty("FORTMAT");							// 格式 仅支持JSON 

	public static String QR_PAY_MODEL_SELF = pu.getProperty("QR_PAY_MODEL_SELF");		// 二维码生成模式自定义
	
	public static String DEFAULT_PRODUCT_CODE = pu.getProperty("DEFAULT_PRODUCT_CODE"); // 销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY

	public static String GATEWAY_URL = pu.getProperty("GATEWAY_URL");					// 支付宝网关

}
