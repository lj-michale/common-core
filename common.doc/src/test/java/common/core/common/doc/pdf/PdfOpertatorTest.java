package common.core.common.doc.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class PdfOpertatorTest {

	@Test
	public void mergePdfFiles() throws FileNotFoundException {
		String[] files={"/DEMO13.pdf","/DEMO13.pdf"};
		File outFile = new File(System.getProperty("java.io.tmpdir") + "/DEMO17-out.pdf");
		OutputStream outputStream = new FileOutputStream(outFile);
		PdfOpertator.mergePdfFiles(files, outputStream);
		Assert.assertTrue(outFile.length() > 0);
		System.out.println(outFile.getAbsolutePath());
	}

}
