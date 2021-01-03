package common.core.app.dao.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import common.core.app.dao.EntityHelper;
import common.core.app.dao.converter.AllConverterFieldBuilder;
import common.core.app.dao.crypto.AllCryptoFieldBuilder;
import common.core.common.assertion.util.AssertErrorUtils;

public class EntityRowMapper<T> implements RowMapper<T> {

	private final Constructor<T> constructor;
	private final Map<String, Field> columnMappings;
	private Map<Integer, Field> resultSetFieldMappings;

	public EntityRowMapper(Class<T> entityClass) {
		constructor = getConstructor(entityClass);
		this.columnMappings = EntityHelper.getSetColumnMappings(entityClass);
	}

	@Override
	public T mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		if (resultSetFieldMappings == null) {
			resultSetFieldMappings = buildResultSetFieldMappings(resultSet);
		}
		try {
			T result = constructor.newInstance();
			assignColumnValues(resultSet, result, resultSetFieldMappings);
			return result;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new SQLException("failed to create instance, constructor=" + constructor, e);
		}
	}

	private Map<Integer, Field> buildResultSetFieldMappings(ResultSet resultSet) throws SQLException {
		Map<Integer, Field> resultSetFieldMappings = new HashMap<>();
		ResultSetMetaData meta = resultSet.getMetaData();
		int count = meta.getColumnCount();
		for (int i = 1; i < count + 1; i++) {
			String column = meta.getColumnLabel(i);// getColumnName改为getColumnLabel
													// 否则使用别名时取不到值
			Field field = columnMappings.get(column.toLowerCase());
			if (field != null) {
				resultSetFieldMappings.put(i, field);
			}
		}

		return resultSetFieldMappings;
	}

	private Constructor<T> getConstructor(Class<T> entityClass) {
		AssertErrorUtils.assertNotNull(entityClass, "entityClass can not be null");
		try {
			return entityClass.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("entityClass must have empty constructor", e);
		}
	}

	private void assignColumnValues(ResultSet resultSet, T result, Map<Integer, Field> resultSetFieldMappings) throws SQLException, IllegalAccessException {
		for (Map.Entry<Integer, Field> entry : resultSetFieldMappings.entrySet()) {
			Field field = entry.getValue();
			Object value = JdbcUtils.getResultSetValue(resultSet, entry.getKey(), field.getType());
			if (null != value) {
				value = AllCryptoFieldBuilder.get().decode(result, field, value);
				value = AllConverterFieldBuilder.get().convert(result, field, value);
				field.set(result, value);
			}
		}
	}
}
