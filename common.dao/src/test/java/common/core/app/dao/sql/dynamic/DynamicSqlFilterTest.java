package common.core.app.dao.sql.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DynamicSqlFilterTest {
	DynamicSqlFilter dynamicSqlFilter = new DynamicSqlFilter();

	@Test
	public void filter() {
		Map<String, Object> param = new HashMap<>();
		Assert.assertEquals("select * from table1 where 1=1 ", dynamicSqlFilter.filter("select * from table1 where 1=1 ", param));
		Assert.assertEquals("select * from table1 where 1=1 ", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty()*/", param));
		Assert.assertEquals("select * from table1 where 1=1 ", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty(:name_key1=2 and :name1>6)*/", param));
		param.put("name1", "name1");
		Assert.assertEquals("select * from table1 where 1=1 ", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty(:name_key1=2 and :name1>6)*/", param));
		Assert.assertEquals("select * from table1 where 1=1  ", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty(and :name_key1=2 and :name1>6)*/ /*@NotEmpty(and :name_key1=2 and :name1>6)*/", param));

		param.put("name1", "name1");
		param.put("name_key1", "name_key1");
		Assert.assertEquals("select * from table1 where 1=1 and :name_key1=2 and :name1>6", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty(and :name_key1=2 and :name1>6)*/", param));

		param.put("name1", "name1");
		param.put("name_key1", "name_key1");
		Assert.assertEquals("select * from table1 where 1=1 and :name_key1=2 and :name1>6 and :name_key1=2 and :name1>6", dynamicSqlFilter.filter("select * from table1 where 1=1 /*@NotEmpty(and :name_key1=2 and :name1>6)*/ /*@NotEmpty(and :name_key1=2 and :name1>6)*/", param));

	}
}
