package common.core.app.dao;

import org.junit.Assert;
import org.junit.Test;

public class EntityHelperTest {

	@Test
	public void getField() {
		TestEntity testEntity = new TestEntity();
		ExecuteInfo insertInfo = EntityHelper.buildInsertInfo(testEntity);
		Assert.assertEquals("insert into TestEntity(id,name,BIRTHDATE,gender,isVip) values (?,?,?,?,?)", insertInfo.getSql());
		Assert.assertEquals(5, insertInfo.getParams().length);

		UpdateTestEntity updateTestEntity = new UpdateTestEntity();
		updateTestEntity.setId("my id");
		ExecuteInfo updateInfo = EntityHelper.buildUpdateInfo(updateTestEntity);
		Assert.assertEquals("update my_table_name set name=?,BIRTHDATE=?,gender=?,isVip=? where id=?", updateInfo.getSql());
		Assert.assertEquals(5, updateInfo.getParams().length);

		TestEntityIde testEntityIde = new TestEntityIde();
		ExecuteInfo insertInfoTestEntityIde = EntityHelper.buildInsertInfo(testEntityIde);
		Assert.assertEquals("insert into TestEntityIde(gender,name,BIRTHDATE,isVip) values (?,?,?,?)", insertInfoTestEntityIde.getSql());
		Assert.assertEquals(4, insertInfoTestEntityIde.getParams().length);

		TestEntitySeq testEntitySeq = new TestEntitySeq();
		ExecuteInfo insertInfoTestEntitySeq = EntityHelper.buildInsertInfo(testEntitySeq);
		Assert.assertEquals("insert into TestEntitySeq(id,gender,name,BIRTHDATE,isVip) values (Seq1.nextval,?,?,?,?)", insertInfoTestEntitySeq.getSql());
		Assert.assertEquals(4, insertInfoTestEntitySeq.getParams().length);

	}

}
