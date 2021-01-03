package common.core.app.dao.sql;

import java.util.Map;

public interface SqlFilter {

	String filter(String sql,Map<String,?> param);
}
