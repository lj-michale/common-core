package common.core.app.dao.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StringUtil;

public class SqlBuilder {
	private StringBuffer sqlBuffer = new StringBuffer();
	private String orderSql;
	private String mainSql;
	private String appendTo;
	private List<String> groupColumns;
	private List<Object> params = new ArrayList<>();

	private static final Map<SqlExpression, String> SQL_EXPRESSION_MAP = new HashMap<>();
	static {
		SQL_EXPRESSION_MAP.put(SqlExpression.EQUAL, " = ");
		SQL_EXPRESSION_MAP.put(SqlExpression.EQUAL_OR_GREATER, " >= ");
		SQL_EXPRESSION_MAP.put(SqlExpression.GREATER, " > ");
		SQL_EXPRESSION_MAP.put(SqlExpression.EQUAL_OR_LESSER, " <= ");
		SQL_EXPRESSION_MAP.put(SqlExpression.LESSER, " < ");
		SQL_EXPRESSION_MAP.put(SqlExpression.LIKE, " LIKE ");
		SQL_EXPRESSION_MAP.put(SqlExpression.LIKE_START, " like ");
		SQL_EXPRESSION_MAP.put(SqlExpression.IS_NOT, " <> ");
		SQL_EXPRESSION_MAP.put(SqlExpression.IS_NULL, " is null ");
		SQL_EXPRESSION_MAP.put(SqlExpression.IN, " in ");
		SQL_EXPRESSION_MAP.put(SqlExpression.NOT_IN, " not in ");
	}

	public static SqlBuilder create(String mainSql) {
		SqlBuilder sqlBuilder = new SqlBuilder(mainSql);
		return sqlBuilder;
	}

	public static SqlBuilder createWithOrderSql(String mainSql, String orderSql) {
		SqlBuilder sqlBuilder = new SqlBuilder(mainSql);
		sqlBuilder.setOrderSql(orderSql);
		return sqlBuilder;
	}

	public static SqlBuilder createWithAppendTo(String mainSql, String appendTo) {
		SqlBuilder sqlBuilder = new SqlBuilder(mainSql);
		sqlBuilder.appendTo = appendTo;
		return sqlBuilder;
	}

	public static SqlBuilder createWithOrderSqlAndAppendTo(String mainSql, String orderSql, String appendTo) {
		SqlBuilder sqlBuilder = new SqlBuilder(mainSql);
		sqlBuilder.setOrderSql(orderSql);
		sqlBuilder.appendTo = appendTo;
		return sqlBuilder;
	}

	private SqlBuilder(String mainSql) {
		this.mainSql = mainSql;
	}

	public List<String> getGroupColumns() {
		return groupColumns;
	}

	public void setGroupColumns(List<String> groupColumns) {
		this.groupColumns = groupColumns;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void appendIsNull(String column) {
		this.appendParam(column, SqlExpression.IS_NULL, null);
	}

	public void appendIsNot(String column, Object value) {
		this.appendParam(column, SqlExpression.IS_NOT, value);
	}

	public void appendEqualParam(String column, Object value) {
		this.appendParam(column, SqlExpression.EQUAL, value);
	}

	public void appendEqualOrGreaterParam(String column, Object value) {
		this.appendParam(column, SqlExpression.EQUAL_OR_GREATER, value);
	}

	public void appendEqualOrLesserParam(String column, Object value) {
		this.appendParam(column, SqlExpression.EQUAL_OR_LESSER, value);
	}

	public void appendGreaterParam(String column, Object value) {
		this.appendParam(column, SqlExpression.GREATER, value);
	}

	public void appendLesserParam(String column, Object value) {
		this.appendParam(column, SqlExpression.LESSER, value);
	}

	public void appendLikeParam(String column, Object value) {
		this.appendParam(column, SqlExpression.LIKE, value);
	}

	public void appendLikeStartParam(String column, Object value) {
		this.appendParam(column, SqlExpression.LIKE_START, value);
	}

	public void appendParam(String column, SqlExpression sqlExpression, Object value) {
		if (SqlExpression.IS_NULL.equals(sqlExpression)) {
			sqlBuffer.append(" and ").append(column).append(SqlBuilder.SQL_EXPRESSION_MAP.get(sqlExpression));
			return;
		} else if (SqlExpression.IN.equals(sqlExpression) || SqlExpression.NOT_IN.equals(sqlExpression)) {
			if (value instanceof String) {
				List<String> listValue = new ArrayList<>();
				for (String stringValue : StringUtil.split((String) value)) {
					listValue.add(stringValue);
				}
				value = listValue;
			}
			AssertErrorUtils.assertTrue(value instanceof List, "IN和NOT_IN表达式值必须为List类型");
			List<?> values = (List<?>) value;
			if (values.isEmpty())
				return;
			sqlBuffer.append(" and ").append(column).append(SqlBuilder.SQL_EXPRESSION_MAP.get(sqlExpression)).append(" ( ");
			for (int i = 0; i < values.size(); i++) {
				if (i > 0)
					sqlBuffer.append(",");
				sqlBuffer.append("?");
				this.params.add(values.get(i));
			}
			sqlBuffer.append(" ) ");
			return;
		}
		sqlBuffer.append(" and ").append(column).append(SqlBuilder.SQL_EXPRESSION_MAP.get(sqlExpression)).append(" ? ");
		if (SqlExpression.LIKE.equals(sqlExpression)) {
			this.params.add("%" + value.toString() + "%");
		} else if (SqlExpression.LIKE_START.equals(sqlExpression)) {
			this.params.add(value.toString() + "%");
		} else {
			this.params.add(value);
		}
	}

	public String buildSql() {
		String sql = mainSql;
		if (StringUtil.isBlank(this.appendTo) || sql.indexOf(this.appendTo) < 0) {
			sql = sql + sqlBuffer.toString();
		} else {
			int pos = sql.indexOf(this.appendTo) + this.appendTo.length();
			sql = sql.substring(0, pos) + sqlBuffer.toString() + sql.substring(pos);
		}

		if (null != this.groupColumns && !this.groupColumns.isEmpty()) {
			StringBuffer groupSql = new StringBuffer("select ");
			for (int i = 0; i < this.groupColumns.size(); i++) {
				if (i > 0)
					groupSql.append(" , ");
				groupSql.append(this.groupColumns.get(i));
			}
			groupSql.append(" ,count(1) as c ").append(" from (").append(sql).append(" ) _g ").append(" group by ");
			for (int i = 0; i < this.groupColumns.size(); i++) {
				if (i > 0)
					groupSql.append(" , ");
				groupSql.append(this.groupColumns.get(i));
			}
			groupSql.append("  order by count(1) desc ");
			return groupSql.toString();
		} else if (StringUtil.hasText(this.orderSql)) {
			sql = sql + " " + this.orderSql;
		}
		return sql;
	}

}
