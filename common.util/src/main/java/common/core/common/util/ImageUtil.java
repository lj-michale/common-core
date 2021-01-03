package common.core.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.exception.RuntimeIOException;

/**
 * 图片处理工具类
 *
 */
public class ImageUtil {

	/**
	 * 格式转换
	 * 
	 * @param formatForm
	 * @param formatFormSuffix
	 * @param formatToSuffix
	 * @param outputStream
	 */
	public static void format(String formatFormSuffix, String formatToSuffix, InputStream inputStream, OutputStream outputStream) {

	}

	/**
	 * 图片缩放
	 * 
	 * @param inputStream
	 * @param formatSuffix
	 * @param width
	 * @param height
	 */
	public static void thumbnailImage(InputStream inputStream, OutputStream outputStream, String formatSuffix, int width, int height) {
		String formatName = ImageUtil.getThumbnailImageFormatName(formatSuffix);
		AssertErrorUtils.assertNotNull(formatName, "can't thumbnailImage formatSuffix={}", formatSuffix);
		try {
			Image image = ImageIO.read(inputStream);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.drawImage(image, 0, 0, width, height, null);
			graphics.dispose();
			ImageIO.write(bufferedImage, formatName, outputStream);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	/**
	 * 根据格式后缀获取缩放图格式名
	 * 
	 * @param formatSuffix
	 * @return 缩放图格式名
	 */
	public static String getThumbnailImageFormatName(String formatSuffix) {
		String[] names = ImageIO.getReaderFormatNames();
		for (String name : names) {
			if (formatSuffix.equalsIgnoreCase(name))
				return name;
		}
		return null;
	}

	/**
	 * 判断是否支持缩放
	 * 
	 * @param formatSuffix
	 * @return 是否
	 */
	public static boolean isThumbnailImageFormat(String formatSuffix) {
		return ImageUtil.getThumbnailImageFormatName(formatSuffix) != null;
	}
}
