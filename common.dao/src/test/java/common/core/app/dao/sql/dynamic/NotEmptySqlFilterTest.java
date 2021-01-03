package common.core.app.dao.sql.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class NotEmptySqlFilterTest {
	NotEmptySqlFilter notEmptySqlFilter = new NotEmptySqlFilter();

	@Test
	public void buildBody() {
		Map<String, Object> param = new HashMap<>();

		Assert.assertEquals("", notEmptySqlFilter.buildBody("", param));
		Assert.assertEquals("", notEmptySqlFilter.buildBody(":name1>6", param));
		Assert.assertEquals("", notEmptySqlFilter.buildBody(":name_key1=2", param));

		param.put("name1", "name1");
		Assert.assertEquals(":name1>6", notEmptySqlFilter.buildBody(":name1>6", param));
		Assert.assertEquals("", notEmptySqlFilter.buildBody(":name_key1=2", param));

		param.put("name1", "name1");
		param.put("name_key1", "name_key1");
		Assert.assertEquals(":name1>6", notEmptySqlFilter.buildBody(":name1>6", param));
		Assert.assertEquals(":name_key1=2", notEmptySqlFilter.buildBody(":name_key1=2", param));

		Assert.assertEquals(":name_key1=2 and :name1>6", notEmptySqlFilter.buildBody(":name_key1=2 and :name1>6", param));
	}
}
