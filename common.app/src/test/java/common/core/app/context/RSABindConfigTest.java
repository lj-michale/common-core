package common.core.app.context;

import org.junit.Test;

import junit.framework.Assert;

public class RSABindConfigTest {

	RSABindConfig bindConfig = new RSABindConfig();

	@Test
	public void getPrivateKey() {
		Assert.assertEquals("asdfsadsadfasdsadf", bindConfig.getPrivateKey("classpath:/test.key"));
		Assert.assertEquals("asdfsadsadfasdsadf", bindConfig.getPrivateKey("key.rsa.db"));
	}
}
