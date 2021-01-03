package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUiltTest {
	@Test
	public void format() {
		Assert.assertEquals("abc", StringUtil.format("abc"));
		Assert.assertEquals("abc{}", StringUtil.format("abc{}"));
		Assert.assertEquals("abcabc", StringUtil.format("abc{}", "abc"));
		Assert.assertEquals("abcabcabcabcabc", StringUtil.format("{}abc{}abc{}", "abc", "abc", "abc"));
		Assert.assertEquals("1", StringUtil.convert("1", String.class));
		Assert.assertTrue(1 == StringUtil.convert("1", Integer.class));
		Assert.assertTrue(true == StringUtil.convert("true", Boolean.class));
	}

	@Test
	public void convertChar16To32() {
		Assert.assertEquals("0", StringUtil.convertChar16To32("00"));
		Assert.assertEquals("9", StringUtil.convertChar16To32("09"));
		Assert.assertEquals("a", StringUtil.convertChar16To32("0a"));
		Assert.assertEquals("f", StringUtil.convertChar16To32("0f"));
		Assert.assertEquals("g", StringUtil.convertChar16To32("10"));
		Assert.assertEquals("h", StringUtil.convertChar16To32("11"));
		Assert.assertEquals("v", StringUtil.convertChar16To32("ff"));
	}

	@Test
	public void trimBlank() {
		Assert.assertEquals("0", StringUtil.trimBlank(" 0 "));
		Assert.assertEquals("0", StringUtil.trimBlank(" \t0\n "));
		Assert.assertEquals("0", StringUtil.trimBlank(" \t0\n\t "));
		Assert.assertEquals("0", StringUtil.trimBlank(" \t0\n "));
		Assert.assertEquals("0 0", StringUtil.trimBlank(" \t0 0\n "));
	}

	@Test
	public void splitWithRegx() {
		Assert.assertNull(StringUtil.splitWithRegx(null, "\\,|\\;"));
		Assert.assertEquals(3, StringUtil.splitWithRegx("a,b;c", "[,;]").length);
		Assert.assertEquals(3, StringUtil.splitWithRegx("a,b;c;", "\\,|\\;").length);
		Assert.assertEquals(4, StringUtil.splitWithRegx("1;a,b;c;", "\\,|\\;").length);
		Assert.assertEquals(1, StringUtil.splitWithRegx("", "\\,|\\;").length);
	}

	@Test
	public void split() {
		Assert.assertNull(StringUtil.split(null));
		Assert.assertEquals(3, StringUtil.split("a,b;c").length);
		Assert.assertEquals(3, StringUtil.split("a,b;c;").length);
		Assert.assertEquals(4, StringUtil.split("1;a,b;c;").length);
		Assert.assertEquals(1, StringUtil.split("").length);
	}

}
