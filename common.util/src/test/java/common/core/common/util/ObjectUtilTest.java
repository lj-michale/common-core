package common.core.common.util;

import java.util.Date;
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

	@Test
	public void fromJson() {
		String data = "{\"date1\":\"2018-08-10\"}";
		DateObject dateObject = ObjectUtil.fromJson(DateObject.class, data);
		Assert.assertNotNull(dateObject.getDate1());
	}

	static class DateObject {
		private Date date1;
		private Date date2;

		public Date getDate1() {
			return date1;
		}

		public void setDate1(Date date1) {
			this.date1 = date1;
		}

		public Date getDate2() {
			return date2;
		}

		public void setDate2(Date date2) {
			this.date2 = date2;
		}

	}
}
