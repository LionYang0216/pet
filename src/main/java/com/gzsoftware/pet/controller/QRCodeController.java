package com.gzsoftware.pet.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Controller
@RequestMapping("qrCode")
public class QRCodeController extends BaseController {
	private static final Log logger = LogFactory.getLog(QRCodeController.class);

	@RequestMapping("/generate")
	public void generate(String url, HttpServletResponse response, Integer width, Integer height) {

		try {
			int iWidth = (width == null ? 200 : width);
			int iHeight = (height == null ? 200 : height);
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, iWidth, iHeight, hints);// 生成矩阵
			// 将矩阵转为Image
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ImageIO.write(image, "png", response.getOutputStream());// 将BufferedImage转成out输出流

		} catch (Exception e) {

			logger.error(String.format("生成二维码失败： url： %s", url), e);

		}
	}
}
