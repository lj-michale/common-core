package common.core.web.api;

import org.junit.Assert;
import org.junit.Test;

public class ApiRequestContextTest {

	@Test
	public void match() {
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setUserAgent("{\"platform\":\"ios\",\"softwareVersion\":\"1.0.1\"}");
		ApiRequestContext.set(apiRequest);

		Assert.assertTrue(ApiRequestContext.match("ios=1.0.1"));
		Assert.assertTrue(ApiRequestContext.match("ios==1.0.1"));
		Assert.assertTrue(ApiRequestContext.match("ios>=1.0.1"));
		Assert.assertTrue(ApiRequestContext.match("ios<=1.0.1"));
		Assert.assertTrue(ApiRequestContext.match("ios<=1.0.2"));
		Assert.assertTrue(ApiRequestContext.match("ios>=1.0.0"));
		Assert.assertTrue(ApiRequestContext.match("ios<1.0.2"));
		Assert.assertTrue(ApiRequestContext.match("ios>1.0.0"));
		Assert.assertTrue(ApiRequestContext.match("ios=*"));

		Assert.assertFalse(ApiRequestContext.match("ios!=1.0.1"));
		Assert.assertFalse(ApiRequestContext.match("ios<>1.0.1"));
		Assert.assertFalse(ApiRequestContext.match("ios<1.0.1"));
		Assert.assertFalse(ApiRequestContext.match("ios<1.0.1"));
		Assert.assertFalse(ApiRequestContext.match("ios=1.0.2"));
	}
}
