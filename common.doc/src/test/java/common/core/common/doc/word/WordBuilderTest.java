package common.core.common.doc.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import common.core.common.util.UuidUtil;

public class WordBuilderTest {

	@Test
	public void processClassPathTempate() throws FileNotFoundException {
		File outFile = new File(System.getProperty("java.io.tmpdir") + "/processClassPathTempate01.doc");
		FileOutputStream fileOutputStream = new FileOutputStream(outFile);
		Map<String, Object> data = new HashMap<>();
		data.put("title", "标题是什么");
		data.put("context", "内容" + UuidUtil.generateFullUuid());
		WordBuilder.buildDocFromXml(fileOutputStream, data, "/wordTemplate/xmltodoc.doc.xml");
		System.out.println("processClassPathTempate:" + outFile.getPath());
	}
}
