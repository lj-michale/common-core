package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class StringEscapeUtilTest {

	@Test
	public void escapeHtml4() {
		Assert.assertEquals("&lt;a&gt;", StringEscapeUtil.escapeHtml4("<a>"));
		Assert.assertEquals(null, StringEscapeUtil.escapeHtml4(null));
	}
}
