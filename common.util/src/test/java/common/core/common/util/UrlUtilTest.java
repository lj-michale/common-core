package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest {

	@Test
	public void getRequestFullServer() {
		Assert.assertTrue(UrlUtil.SERVER_NAME_PATTERN.matcher("http://www.abc.com/").find());
		Assert.assertEquals("http://www.abc.com", UrlUtil.getRequestFullServer("http://www.abc.com/"));
		Assert.assertEquals("https://www.abc.com:8080", UrlUtil.getRequestFullServer("https://www.abc.com:8080/index.html"));
		Assert.assertEquals("https://www.abc.com:8080", UrlUtil.getRequestFullServer("https://www.abc.com:8080/sys/index.html"));
		Assert.assertEquals("http://www.abc.com", UrlUtil.getRequestFullServer("http://www.abc.com/"));
	}

	@Test
	public void removeParam() {
		Assert.assertEquals("http://www.abc.com/", UrlUtil.removeParam("http://www.abc.com/", "p"));
		Assert.assertEquals("http://www.abc.com/?p", UrlUtil.removeParam("http://www.abc.com/?p", "p"));
		Assert.assertEquals("http://www.abc.com/", UrlUtil.removeParam("http://www.abc.com/?p=", "p"));
		Assert.assertEquals("http://www.abc.com/", UrlUtil.removeParam("http://www.abc.com/?p=1", "p"));
		Assert.assertEquals("http://www.abc.com/?a=2", UrlUtil.removeParam("http://www.abc.com/?a=2&p=1", "p"));
		Assert.assertEquals("http://www.abc.com/?b=2&c=d", UrlUtil.removeParam("http://www.abc.com/?b=2&p=1&c=d", "p"));
	}

	@Test
	public void getRequestUri() {
		Assert.assertEquals("/", UrlUtil.getRequestUri("http://www.abc.com/"));
		Assert.assertEquals("/", UrlUtil.getRequestUri("http://www.abc.com"));
		Assert.assertEquals("/abc", UrlUtil.getRequestUri("http://www.abc.com/abc"));
		Assert.assertEquals("/abc/efg", UrlUtil.getRequestUri("https://www.abc.com/abc/efg"));
	}

}
