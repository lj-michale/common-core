package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class ClassUtilTest {

	@Test
	public void findAnnotationMethod() throws NoSuchMethodException, SecurityException {
		Assert.assertNotNull(ClassUtilTest.class.getMethod("findAnnotationMethod").getAnnotationsByType(Test.class));
		Assert.assertEquals(3, ClassUtil.findAnnotationMethods(ClassUtilTestObject.class, ClassUtilTestObjectAnnotation.class).size());
	}

	@Test
	public void findAnnotationFields() throws NoSuchMethodException, SecurityException {
		Assert.assertEquals(2, ClassUtil.findAnnotationFields(ClassUtilTestObject.class, ClassUtilTestObjectAnnotation.class).size());
	}
}
