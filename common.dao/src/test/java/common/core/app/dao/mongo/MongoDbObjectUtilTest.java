package common.core.app.dao.mongo;

import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.Assert;
import org.junit.Test;

import common.core.app.dao.TestEntity;

public class MongoDbObjectUtilTest {

	static class IdObject {
		@Id
		@Column(name = "_id")
		private String id;
		private String code;
		@Column(name = "Name")
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@Test
	public void buildDocment() {
		TestEntity testEntity = new TestEntity();
		Assert.assertEquals(MongoDbObjectUtil.buildUpdateDocument(testEntity).size() + 1, MongoDbObjectUtil.buildInsertDocument(testEntity).size());
		Assert.assertEquals(MongoDbObjectUtil.buildUpdateDocument(new IdObject()).size() + 1, MongoDbObjectUtil.buildInsertDocument(new IdObject()).size());
	}
}
