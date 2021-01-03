package common.core.common.doc.qr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.exception.RuntimeIOException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QrCodeHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeHelper.class);

	private static BitMatrix toBitMatrix(String content, QrEncodeFormat qrEncodeFormat) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, qrEncodeFormat.getCharacterSet());
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.valueOf(qrEncodeFormat.getBarcodeFormat()), qrEncodeFormat.getWidth(), qrEncodeFormat.getHeight(), hints);
		} catch (WriterException e) {
			throw new RuntimeIOException(e.getMessage(), e);
		}
		return bitMatrix;
	}

	public static void encodeToStream(String content, QrEncodeFormat qrEncodeFormat, OutputStream outputStream) {
		LOGGER.debug("QrWriter writeToStream content={}, qrWriterFormat={}", content, qrEncodeFormat);
		BitMatrix bitMatrix = QrCodeHelper.toBitMatrix(content, qrEncodeFormat);
		try {
			MatrixToImageWriter.writeToStream(bitMatrix, qrEncodeFormat.getImageFormat(), outputStream);// 输出图像
		} catch (IOException e) {
			throw new RuntimeIOException(e.getMessage(), e);
		}
	}

	public static String decodeFromStream(InputStream inputStream) {
		LOGGER.debug("QrWriter decodeFromStream");
		try {
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			return result.getText();
		} catch (IOException | NotFoundException e) {
			throw new RuntimeIOException(e.getMessage(), e);
		}
	}

}
