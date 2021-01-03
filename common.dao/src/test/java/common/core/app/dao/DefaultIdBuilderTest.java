package common.core.app.dao;

import org.junit.Assert;
import org.junit.Test;

public class DefaultIdBuilderTest {
	@Test
	public void build() {
		Assert.assertEquals(13, DefaultIdBuilder.build().length());
	}
}
