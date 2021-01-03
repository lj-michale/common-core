package common.core.app.dao.sql.dynamic;

import java.util.Map;

import common.core.app.dao.sql.SqlFilter;

public class DynamicSqlFilter implements SqlFilter {

	NotEmptySqlFilter notEmptySqlFilter = new NotEmptySqlFilter();

	@Override
	public String filter(String sql,  Map<String, ?> param) {
		StringBuilder result = new StringBuilder();
		int beginIndex = 0, endIndex = 0, length = sql.length(), bodyIndex = 0;
		String body = null;
		beginIndex = sql.indexOf(notEmptySqlFilter.getPrefix(), beginIndex);
		if (beginIndex < 0)
			return sql;
		while (beginIndex >= 0 && beginIndex < length) {
			result.append(sql.substring(bodyIndex, beginIndex));
			endIndex = sql.indexOf(notEmptySqlFilter.getSuffix(), beginIndex + notEmptySqlFilter.getPrefix().length());
			body = sql.substring(beginIndex + notEmptySqlFilter.getPrefix().length(), endIndex);
			result.append(notEmptySqlFilter.buildBody(body, param));
			beginIndex = sql.indexOf(notEmptySqlFilter.getPrefix(), ++beginIndex);
			bodyIndex = endIndex + notEmptySqlFilter.getSuffix().length();
		}
		result.append(sql.substring(bodyIndex));
		return result.toString();
	}

}
