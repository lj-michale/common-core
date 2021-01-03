package common.core.common.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ObjectUtilTest {
	int id = 123;
	String name = "test name";
	String email = "zhengmw@eascs.com";

	@Test
	public void toMap() {
		ClassUtilTestObject objectUtilTestObject = new ClassUtilTestObject();
		objectUtilTestObject.setId(id);
		objectUtilTestObject.setName(name);
		objectUtilTestObject.setEmail(email);
		Map<String, Object> data = ObjectUtil.toMap(objectUtilTestObject);
		Assert.assertEquals(id, data.get("id"));
		Assert.assertEquals(name, data.get("name"));
		Assert.assertEquals(email, data.get("Email"));
	}

	@Test
	public void fromMap() {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("id", id);
		mapData.put("name", name);
		mapData.put("Email", email);
		ClassUtilTestObject objectUtilTestObject = ObjectUtil.fromMap(ClassUtilTestObject.class, mapData);
		Assert.assertEquals(id, objectUtilTestObject.getId());
		Assert.assertEquals(name, objectUtilTestObject.getName());
		Assert.assertEquals(email, objectUtilTestObject.getEmail());
	}
}
