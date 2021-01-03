package common.core.common.doc.qr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.UuidUtil;

public class QrWriterTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(QrWriterTest.class);

	@Test
	public void writeToStream() throws FileNotFoundException {
		String content = "http://www.cnblogs.com/jtmjx/archive/2012/06/18/2545209.html";
		File file = new File(new File(System.getProperty("java.io.tmpdir")), UuidUtil.generateFullUuid() + ".png");
		LOGGER.debug("build to file {}", file);
		OutputStream outputStream = new FileOutputStream(file);
		QrEncodeFormat qrWriterFormat = new QrEncodeFormat(400, 400);
		QrCodeHelper.encodeToStream(content, qrWriterFormat, outputStream);
		Assert.assertEquals(content, QrCodeHelper.decodeFromStream(new FileInputStream(file)));
		Assert.assertEquals("http://weixin.qq.com/r/6MAqMjbEkWvurXJR95X6", QrCodeHelper.decodeFromStream(QrWriterTest.class.getResourceAsStream("/br.gif")));
	}

}
