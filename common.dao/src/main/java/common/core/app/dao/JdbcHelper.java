package common.core.app.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import common.core.app.dao.sql.dynamic.DynamicSqlFilter;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ClassUtil;
import common.core.common.util.ObjectUtil;

public class JdbcHelper extends JdbcTemplate {
	private final Logger logger = LoggerFactory.getLogger(JdbcHelper.class);

	NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
	private DynamicSqlFilter dynamicSqlFilter = new DynamicSqlFilter();

	public JdbcHelper() {
		super();
	}

	public JdbcHelper(DataSource dataSource, boolean lazyInit) {
		super(dataSource, lazyInit);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public JdbcHelper(DataSource dataSource) {
		super(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {
		if (args.length == 1) {
			Object arg0 = args[0];
			if (!ClassUtil.isSimpaleType(arg0.getClass()) && !(arg0 instanceof Map)) {
				arg0 = ObjectUtil.toMap(arg0);
			}
			if (arg0 instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, ?> arg = (Map<String, ?>) arg0;
				String filterSql = dynamicSqlFilter.filter(sql, arg);
				if (!filterSql.equals(sql)) {
					logger.debug("dynamicSqlFilter: sql={} ", sql);
					logger.debug("dynamicSqlFilter: filterSql={}; arg={} ", filterSql, arg);
				}
				return this.namedParameterJdbcTemplate.query(filterSql, arg, rse);
			}
		}
		args = filterArgs(args);
		return super.query(sql, rse, args);
	}

	@Override
	public void query(String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {
		args = filterArgs(args);
		super.query(sql, rch, args);
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
		if (args.length == 1) {
			Object arg0 = args[0];
			if (!ClassUtil.isSimpaleType(arg0.getClass()) && !(arg0 instanceof Map)) {
				arg0 = ObjectUtil.toMap(arg0);
			}
			if (arg0 instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, ?> arg = (Map<String, ?>) arg0;
				String filterSql = dynamicSqlFilter.filter(sql, arg);
				if (!filterSql.equals(sql)) {
					logger.debug("dynamicSqlFilter: sql={} ", sql);
					logger.debug("dynamicSqlFilter: filterSql={}; arg={} ", filterSql, arg);
				}
				return this.namedParameterJdbcTemplate.query(filterSql, arg, rowMapper);
			}
		}
		args = filterArgs(args);
		return super.query(sql, rowMapper, args);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.queryForObject(sql, rowMapper, args);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
		if (args.length == 1) {
			Object arg0 = args[0];
			if (!ClassUtil.isSimpaleType(arg0.getClass()) && !(arg0 instanceof Map)) {
				arg0 = ObjectUtil.toMap(arg0);
			}
			if (arg0 instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, ?> arg = (Map<String, ?>) arg0;
				String filterSql = dynamicSqlFilter.filter(sql, arg);
				if (!filterSql.equals(sql)) {
					logger.debug("dynamicSqlFilter: sql={} ", sql);
					logger.debug("dynamicSqlFilter: filterSql={}; arg={} ", filterSql, arg);
				}
				return this.namedParameterJdbcTemplate.queryForObject(filterSql, arg, requiredType);
			}
		}
		args = filterArgs(args);
		return super.queryForObject(sql, requiredType, args);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.queryForMap(sql, args);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.queryForList(sql, elementType, args);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.queryForList(sql, args);
	}

	@Override
	public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.queryForRowSet(sql, args);
	}

	@Override
	public int update(String sql, Object... args) throws DataAccessException {
		args = filterArgs(args);
		return super.update(sql, args);
	}

	private Object[] filterArgs(Object... args) {
		if (null != args && args.length == 1 && args[0] instanceof List) {
			List<?> argsList = (List<?>) args[0];
			Object[] results = new Object[argsList.size()];
			for (int i = 0; i < results.length; i++) {
				results[i] = argsList.get(i);
			}
			return results;
		}
		return args;
	}
}
