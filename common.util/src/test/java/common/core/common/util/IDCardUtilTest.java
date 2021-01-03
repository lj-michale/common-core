package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class IDCardUtilTest {
	@Test
	public void verify() {
		Assert.assertTrue(IDCardUtil.verify("512501197203035172"));
		Assert.assertTrue(IDCardUtil.verify("512501197506045175"));
		Assert.assertTrue(IDCardUtil.verify("51052119850508797x"));
		Assert.assertTrue(IDCardUtil.verify("512501196512305186"));
		Assert.assertTrue(IDCardUtil.verify("332521196902060091"));
		Assert.assertTrue(IDCardUtil.verify("330702195705210412"));
		Assert.assertTrue(IDCardUtil.verify("330723197307302179"));
		Assert.assertTrue(IDCardUtil.verify("330702195705210412"));

		Assert.assertFalse(IDCardUtil.verify("330702195705210479"));
		Assert.assertFalse(IDCardUtil.verify("330702195705210411"));
	}
}
