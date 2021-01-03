package common.core.common.doc.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class PdfBuilderTest {
	@Test
	public void build() throws FileNotFoundException {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "oto-2980-898");
		data.put("partyA", "甲方姓名-张三");
		data.put("partyB", "壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万");
		File outFile = new File(System.getProperty("java.io.tmpdir") + "/DEMO13-out.pdf");
		OutputStream outputStream = new FileOutputStream(outFile);
		PdfBuilder.build(PdfBuilderTest.class.getResourceAsStream("/DEMO13.pdf"), data, outputStream);
		Assert.assertTrue(outFile.length() > 0);
		System.out.println(outFile.getAbsolutePath());
	}

	@Test
	public void buildcontract() throws FileNotFoundException {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "oto-2980-898");
		data.put("partyA", "甲方姓名-张三");
		data.put("partyB", "壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万");
		File outFile = new File(System.getProperty("java.io.tmpdir") + "/gacz_contract.pdf");
		OutputStream outputStream = new FileOutputStream(outFile);
		PdfBuilder.build(PdfBuilderTest.class.getResourceAsStream("/gacz_contract.pdf"), data, outputStream);
		Assert.assertTrue(outFile.length() > 0);
		System.out.println(outFile.getAbsolutePath());
	}

	@Test
	public void mergePdfFilesAndBuild() throws FileNotFoundException {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "oto-2980-898");
		data.put("partyA", "甲方姓名-张三");
		data.put("partyB", "乙方姓名-李四");

		String[] classPathFiles = { "/DEMO13.pdf", "/DEMO13.pdf" };
		File outFile = new File(System.getProperty("java.io.tmpdir") + "/DEMO16-out.pdf");
		OutputStream outputStream = new FileOutputStream(outFile);

		PdfBuilder.mergePdfFilesAndBuild(classPathFiles, data, outputStream);

		Assert.assertTrue(outFile.length() > 0);
		System.out.println(outFile.getAbsolutePath());
	}

}
