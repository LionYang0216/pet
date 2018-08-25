package com.gzsoftware.pet.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.gzsoftware.pet.entity.vo.PayProduct;
import com.gzsoftware.pet.utils.AlipayConfigUtil;

@Service("alipayService")
public class AlipayService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WechatPayService.class);

	/***
	 * 获取支付宝支付html页面
	 * @param payProduct
	 * @param width
	 * @return
	 */
	public String pay(PayProduct payProduct,Long width) {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigUtil.GATEWAY_URL, AlipayConfigUtil.APP_ID,
				AlipayConfigUtil.MERCHANT_PRIVATE_KEY, AlipayConfigUtil.FORTMAT, AlipayConfigUtil.CHAR_SET,
				AlipayConfigUtil.ALIPAY_PUBLIC_KEY, AlipayConfigUtil.SIGN_TYPE);

		//组装请求参数
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setTotalAmount(payProduct.getTotalFee());
		try {
			model.setPassbackParams(URLEncoder.encode(payProduct.getAttach(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("([ALIPAY][PAY] 订单号：{}  加密返回参数失败: {}", payProduct.getOutTradeNo(), e);
			e.printStackTrace();
			return "加密返回参数失败";
		}
		model.setProductCode(AlipayConfigUtil.DEFAULT_PRODUCT_CODE);
		model.setOutTradeNo(payProduct.getOutTradeNo());
		model.setSubject(payProduct.getBody());
		model.setQrPayMode(AlipayConfigUtil.QR_PAY_MODEL_SELF);
		model.setQrcodeWidth(width);
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		//request.setReturnUrl(AlipayConfig.return_url);
		request.setNotifyUrl(AlipayConfigUtil.NOTIFY_URL);
		request.setBizModel(model);
		try {
			AlipayTradePagePayResponse response=alipayClient.pageExecute(request);
			logger.info("[ALIPAY][PAY][CALL] requestHTML: " + response.getBody() + " ");
			return response.getBody();
		} catch (AlipayApiException e) {
			logger.error("([ALIPAY][PAY] 订单号：{}  生成支付宝支付页面失败: {}", payProduct.getOutTradeNo(), e);
			e.printStackTrace();
			return "生成支付宝支付页面失败"+e.getErrMsg();
		}
	

	}
}
