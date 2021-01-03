package common.core.app.dao.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.core.app.dao.sql.filter.ValueFilter;
import common.core.common.util.ClassUtil;
import common.core.common.util.StringUtil;

public class ObjectSqlBuilder {
	private SqlBuilder sqlBuilder;
	private static final Map<Class<?>, List<Field>> SQL_BUILDER_EXPRESSION_FIELD_CACHE = new ConcurrentHashMap<>();
	private static final Map<Class<?>, List<Field>> SQL_ORDER_EXPRESSION_FIELD_CACHE = new ConcurrentHashMap<>();
	private static final Map<Class<?>, List<Field>> SQL_GROUP_EXPRESSION_FIELD_CACHE = new ConcurrentHashMap<>();

	public static ObjectSqlBuilder create(Object obj, String mainSql) {
		return new ObjectSqlBuilder(obj, mainSql, null, null);
	}

	public static ObjectSqlBuilder createWithOrderSql(Object obj, String mainSql, String orderSql) {
		return new ObjectSqlBuilder(obj, mainSql, orderSql, null);
	}

	public static ObjectSqlBuilder createWithAppendTo(Object obj, String mainSql, String appendTo) {
		return new ObjectSqlBuilder(obj, mainSql, null, appendTo);
	}

	private ObjectSqlBuilder(Object obj, String mainSql, String orderSql, String appendTo) {
		super();
		this.sqlBuilder = SqlBuilder.createWithOrderSqlAndAppendTo(mainSql, orderSql, appendTo);
		List<Field> sqlBuilderExpressionFields = getSqlBuilderExpressionFields(obj);
		buildSqlBuilderExpression(obj, sqlBuilderExpressionFields);
		if (StringUtil.isEmpty(orderSql)) {
			buildOrderExprssion(obj);
		}
		buildGroupExprssion(obj);
	}

	private void buildGroupExprssion(Object obj) {
		List<Field> sqlGroupExpressionFields = getSqlGroupExpressionFields(obj);
		List<String> groupColumns = new ArrayList<>();
		for (Field field : sqlGroupExpressionFields) {
			field.setAccessible(true);
			try {
				String groupBy = (String) field.get(obj);
				if (StringUtil.isEmpty(groupBy))
					continue;
				groupColumns.add(groupBy);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		if (groupColumns.size() > 0) {
			this.sqlBuilder.setGroupColumns(groupColumns);
		}
	}

	private void buildOrderExprssion(Object obj) {
		List<Field> sqlOrderExpressionFields = getSqlOrderExpressionFields(obj);
		StringBuffer orderBuffer = new StringBuffer();
		for (Field field : sqlOrderExpressionFields) {
			field.setAccessible(true);
			try {
				String orderBy = (String) field.get(obj);
				if (StringUtil.isEmpty(orderBy))
					continue;
				if (orderBuffer.length() == 0)
					orderBuffer.append(" order by ");
				else
					orderBuffer.append(" , ");
				orderBuffer.append(orderBy);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		if (orderBuffer.length() > 0) {
			this.sqlBuilder.setOrderSql(orderBuffer.toString());
		}
	}

	public static List<Field> getSqlBuilderExpressionFields(Object obj) {
		Class<?> clz = obj.getClass();
		List<Field> fields = SQL_BUILDER_EXPRESSION_FIELD_CACHE.get(clz);
		if (null == fields) {
			fields = ClassUtil.findAnnotationFields(clz, SqlBuilderExpression.class);
			SQL_BUILDER_EXPRESSION_FIELD_CACHE.put(clz, fields);
		}
		return fields;
	}

	public static List<Field> getSqlOrderExpressionFields(Object obj) {
		Class<?> clz = obj.getClass();
		List<Field> fields = SQL_ORDER_EXPRESSION_FIELD_CACHE.get(clz);
		if (null == fields) {
			fields = ClassUtil.findAnnotationFields(clz, SqlOrderExpression.class);
			SQL_ORDER_EXPRESSION_FIELD_CACHE.put(clz, fields);
		}
		return fields;
	}

	public static List<Field> getSqlGroupExpressionFields(Object obj) {
		Class<?> clz = obj.getClass();
		List<Field> fields = SQL_GROUP_EXPRESSION_FIELD_CACHE.get(clz);
		if (null == fields) {
			fields = ClassUtil.findAnnotationFields(clz, SqlGroupExpression.class);
			SQL_GROUP_EXPRESSION_FIELD_CACHE.put(clz, fields);
		}
		return fields;
	}

	private void buildSqlBuilderExpression(Object obj, List<Field> fields) {
		for (Field field : fields) {
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (null == value) {
				continue;
			}

			SqlBuilderExpression sqlBuilderExpression = field.getAnnotation(SqlBuilderExpression.class);
			SqlExpression sqlExpression = sqlBuilderExpression.value();
			String column = StringUtil.hasText(sqlBuilderExpression.columnName()) ? sqlBuilderExpression.columnName() : field.getName();
			if (null != value) {
				ValueFilterField valueFilterField = field.getAnnotation(ValueFilterField.class);
				if (null != valueFilterField) {
					for (Class<?> valueFilterClass : valueFilterField.valueFilters()) {
						ValueFilter valueFilter = (ValueFilter) ClassUtil.newInstance(valueFilterClass);
						value = valueFilter.doFilter(value, field, obj);
					}
				}
			}
			sqlBuilder.appendParam(column, sqlExpression, value);
		}
	}

	public String getSql() {
		return sqlBuilder.buildSql();
	}

	public List<Object> getParams() {
		return sqlBuilder.getParams();
	}

}
