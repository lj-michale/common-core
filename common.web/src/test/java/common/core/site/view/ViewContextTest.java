package common.core.site.view;

import org.junit.Assert;
import org.junit.Test;

import common.core.common.assertion.exception.ErrorAssertionException;

public class ViewContextTest {

	@Test(expected = ErrorAssertionException.class)
	public void put() {
		Assert.assertTrue(ViewContext.IGNORE_KEY.length > 0);
		ViewContext.initContext();
		ViewContext.put("htmlEditor", "/");
	}

}
