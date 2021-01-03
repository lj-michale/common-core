package common.core.common.doc.html.reader;

import org.junit.Test;

import common.core.common.util.ObjectUtil;

public class HtmlReaderTest {

	@Test
	public void bind() {
		HtmlReader htmlReader = new HtmlReader(HtmlReaderTest.class.getResourceAsStream("/zx1.html"), "GBK", "http://www.baidu.com");
		Zx zx = htmlReader.bindToType(Zx.class);
		System.out.println(zx);
		System.out.println(ObjectUtil.toJson(zx));
	}
}
