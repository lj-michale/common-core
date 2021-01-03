package common.core.common.crypto;

import org.junit.Assert;
import org.junit.Test;

public class AESUtilTest {

	@Test
	public void aes() {
		String key = "ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223ajjlaosdfuowj3!@223";
		String content = "what is this,中国";
		Assert.assertEquals(content, AESUtil.decrypt(AESUtil.encrypt(content, key), key));
	}

}
