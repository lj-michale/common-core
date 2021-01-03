package common.core.common.doc.excel.format;

import org.junit.Assert;
import org.junit.Test;

public class WanyuanFormatTest {
	@Test
	public void format() {
		WanyuanFormat wanyuanFormat = new WanyuanFormat();
		Assert.assertEquals("12345.6789", wanyuanFormat.format(123456789).toString());
		Assert.assertEquals("12345.67", wanyuanFormat.format(123456700).toString());
	}
}
