package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void split() {
		Assert.assertTrue(StringUtil.split("a,b,c", ",").length == 3);
		Assert.assertTrue(StringUtil.split("a|b|c", "|").length == 3);
		Assert.assertTrue(StringUtil.split("|a|b|c", "|").length == 3);
	}
}
