package common.core.common.doc.qr;

public class QrEncodeFormat {
	private int width;
	private int height;
	private String imageFormat = "png";
	private String characterSet = "UTF-8";
	private String barcodeFormat = "QR_CODE";

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}

	public String getBarcodeFormat() {
		return barcodeFormat;
	}

	public void setBarcodeFormat(String barcodeFormat) {
		this.barcodeFormat = barcodeFormat;
	}

	public QrEncodeFormat(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public QrEncodeFormat() {
		super();
	}

	@Override
	public String toString() {
		return "QrWriterFormat [width=" + width + ", height=" + height + ", imageFormat=" + imageFormat + ", characterSet=" + characterSet + ", barcodeFormat=" + barcodeFormat + "]";
	}

}
