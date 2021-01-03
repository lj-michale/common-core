package common.core.app.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import common.core.app.dao.converter.AllConverterFieldBuilder;
import common.core.app.dao.crypto.AllCryptoFieldBuilder;
import common.core.app.exception.NotSupportedException;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ClassUtil;
import common.core.common.util.StringUtil;

public class EntityHelper {

	private static final Map<String, EntityInfo> ENTITY_INFO_CACHE = new HashMap<>();
	private static final Map<String, Map<String, Field>> SET_COLUMN_MAPPINGS_CACHE = new HashMap<>();

	public static <T> void updateId(T entity, Object id) {
		AssertErrorUtils.assertNotNull(id, "id is null");
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			GeneratedValue generatedValue = columnInfo.getColumnField().getAnnotation(GeneratedValue.class);
			if (null != generatedValue) {
				try {
					if (int.class == columnInfo.getColumnField().getType() || Integer.class == columnInfo.getColumnField().getType()) {
						id = Integer.valueOf(id.toString());
					} else if (long.class == columnInfo.getColumnField().getType() || Long.class == columnInfo.getColumnField().getType()) {
						id = Long.valueOf(id.toString());
					} else {
						id = id.toString();
					}
					columnInfo.getColumnField().set(entity, id);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new NotSupportedException(e);
				}
				return;
			}

		}

	}

	public static <T> ExecuteInfo buildDeleteInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		String sql = entityInfo.getDeleteSql();
		List<Object> params = buildIdParamsInfo(entity, entityInfo);
		return new ExecuteInfo(sql, params.toArray());

	}

	public static <T> ExecuteInfo buildSelectInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		String sql = entityInfo.getSelectSql();
		List<Object> params = buildIdParamsInfo(entity, entityInfo);
		return new ExecuteInfo(sql, params.toArray());

	}

	public static <T> ExecuteInfo buildSelectAllInfo(Class<T> requiredType) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(requiredType);
		String sql = entityInfo.getSelectAllSql();
		return new ExecuteInfo(sql, new Object[] {});
	}

	public static <T> ExecuteInfo buildSelectWithAnyInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		StringBuffer sqlAny = new StringBuffer(entityInfo.getSelectAllSql());
		List<Object> params = new ArrayList<>();
		StringBuffer sqlWhere = new StringBuffer(" where ");
		int whereIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {

			// build where
			Object value = getColumnInfoValue(entity, columnInfo);
			if (null == value) {
				continue;
			}
			if (whereIndex++ > 0) {
				sqlWhere.append(" and ");
			}
			sqlWhere.append(columnInfo.getColumnName()).append("=?");
			params.add(value);
		}
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			Object value = getColumnInfoValue(entity, columnInfo);
			if (null == value) {
				continue;
			}
			if (whereIndex++ > 0) {
				sqlWhere.append(" and ");
			}
			sqlWhere.append(columnInfo.getColumnName()).append("=?");
			params.add(value);
		}
		sqlAny.append(sqlWhere.toString());
		AssertErrorUtils.assertTrue(params.size() > 0, "can't find any params field at {}", entity);
		return new ExecuteInfo(sqlAny.toString(), params.toArray());

	}

	public static <T> ExecuteInfo buildDeleteWithAnyInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		List<Object> params = new ArrayList<>();
		StringBuffer sqlWhere = new StringBuffer(" where ");
		int whereIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
			// build where
			Object value = getColumnInfoValue(entity, columnInfo);
			if (null == value) {
				continue;
			}
			if (whereIndex++ > 0) {
				sqlWhere.append(" and ");
			}
			sqlWhere.append(columnInfo.getColumnName()).append("=?");
			params.add(value);
		}
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			Object value = getColumnInfoValue(entity, columnInfo);
			if (null == value) {
				continue;
			}
			if (whereIndex++ > 0) {
				sqlWhere.append(" and ");
			}
			sqlWhere.append(columnInfo.getColumnName()).append("=?");
			params.add(value);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  ").append(entityInfo.getTableName()).append(sqlWhere.toString());
		AssertErrorUtils.assertTrue(params.size() > 0, "can't find any params field at {}", entity);
		return new ExecuteInfo(sql.toString(), params.toArray());

	}

	private static <T> List<Object> buildIdParamsInfo(T entity, EntityInfo entityInfo) {
		List<Object> params = new ArrayList<>();
		try {
			for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
				Object id = getColumnInfoValue(entity, columnInfo);
				AssertErrorUtils.assertNotNull(id, "can't get id from field {} of {}", columnInfo.getColumnField().getName(), entity.getClass());
				params.add(id);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		return params;
	}

	public static <T> ExecuteInfo buildUpdateInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		String sql = entityInfo.getUpdateSql();
		List<Object> params = new ArrayList<>();
		try {
			for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
				params.add(getColumnInfoValue(entity, columnInfo));
			}
			for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
				Object id = getColumnInfoValue(entity, columnInfo);
				AssertErrorUtils.assertNotNull(id, "can't get id from field {} of {}", columnInfo.getColumnField().getName(), entity.getClass());
				params.add(id);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		return new ExecuteInfo(sql, params.toArray());

	}

	public static <T> ExecuteInfo buildInsertInfo(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		String sql = entityInfo.getInsertSql();
		List<Object> params = new ArrayList<>();
		try {
			for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
				GeneratedValue generatedValue = columnInfo.getColumnField().getAnnotation(GeneratedValue.class);
				if (null != generatedValue) {
					continue;
				}

				Object id = getColumnInfoValue(entity, columnInfo);
				if (id == null) {
					id = DefaultIdBuilder.build();
					columnInfo.getColumnField().set(entity, id);
				}
				params.add(id);
			}
			for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
				params.add(getColumnInfoValue(entity, columnInfo));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return new ExecuteInfo(sql, params.toArray());

	}

	private static <T> Object getColumnInfoValue(T entity, ColumnInfo columnInfo) {
		Object value = null;
		try {
			value = columnInfo.getColumnField().get(entity);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new DaoException(e);
		}
		value = AllCryptoFieldBuilder.get().encode(entity, columnInfo.getColumnField(), value);
		value = AllConverterFieldBuilder.get().intercept(entity, columnInfo.getColumnField(), value);
		return value;
	}

	public static EntityInfo getEntityInfo(Class<?> clazz) {
		EntityInfo entityInfo = ENTITY_INFO_CACHE.get(clazz.getName());
		if (null != entityInfo)
			return entityInfo;
		entityInfo = new EntityInfo(clazz);

		// build column
		Map<String, Field> columnFieldMap = EntityHelper.getColumnFieldMap(clazz);
		for (Entry<String, Field> item : columnFieldMap.entrySet()) {
			ColumnInfo columnInfo = new ColumnInfo(item.getKey(), item.getValue());
			if (null == columnInfo.getColumnField().getAnnotation(Id.class))
				entityInfo.getColumnInfos().add(columnInfo);
			else if (null == columnInfo.getColumnField().getAnnotation(Transient.class))
				entityInfo.getIdColumnInfos().add(columnInfo);
		}
		AssertErrorUtils.assertTrue(entityInfo.getIdColumnInfos().size() > 0, "can't find id column, please config @Id field to {}", clazz.getName());

		// build sql
		buildInsertSql(entityInfo);
		buildUpdateSql(entityInfo);
		buildDeleteSql(entityInfo);
		buildSelectSql(entityInfo);
		buildSelectAllSql(entityInfo);

		ENTITY_INFO_CACHE.put(clazz.getName(), entityInfo);
		return entityInfo;
	}

	private static void buildDeleteSql(EntityInfo entityInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(entityInfo.getTableName()).append(" where ");
		int columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(" and ");
			sql.append(columnInfo.getColumnName()).append("=?");
		}
		entityInfo.setDeleteSql(sql.toString());
	}

	private static void buildUpdateSql(EntityInfo entityInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(entityInfo.getTableName()).append(" set ");
		int columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append(columnInfo.getColumnName()).append("=?");
		}

		sql.append(" where ");
		columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(" and ");
			sql.append(columnInfo.getColumnName()).append("=?");
		}

		entityInfo.setUpdateSql(sql.toString());
	}

	private static void buildInsertSql(EntityInfo entityInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(entityInfo.getTableName()).append("(");
		int columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			GeneratedValue generatedValue = columnInfo.getColumnField().getAnnotation(GeneratedValue.class);
			if (null != generatedValue) {
				if (GenerationType.IDENTITY == generatedValue.strategy()) {
					continue;
				} else if (GenerationType.SEQUENCE == generatedValue.strategy()) {
					AssertErrorUtils.assertTrue(StringUtil.isNotBlank(generatedValue.generator()), "please put generator to GeneratedValue on class {} field {}", columnInfo.getColumnField().getDeclaringClass(), columnInfo.getColumnField().getName());
				} else
					throw new NotSupportedException("Not support strategy of GeneratedValue!");
			}
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append(columnInfo.getColumnName());
		}
		for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append(columnInfo.getColumnName());
		}
		sql.append(") values (");
		columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			GeneratedValue generatedValue = columnInfo.getColumnField().getAnnotation(GeneratedValue.class);
			if (null != generatedValue) {
				if (GenerationType.IDENTITY == generatedValue.strategy()) {
					continue;
				} else if (GenerationType.SEQUENCE == generatedValue.strategy()) {
					if (columnIndex++ > 0)
						sql.append(",");
					sql.append(generatedValue.generator()).append(".nextval");
					continue;
				}
			}
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append("?");
		}
		for (@SuppressWarnings("unused")
		ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append("?");
		}
		sql.append(")");
		entityInfo.setInsertSql(sql.toString());
	}

	private static void buildSelectSql(EntityInfo entityInfo) {
		StringBuffer sql = new StringBuffer(getSelectAllSql(entityInfo));
		int columnIndex = 0;
		sql.append(" where ");
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(" and ");
			sql.append(columnInfo.getColumnName()).append("=?");
		}
		entityInfo.setSelectSql(sql.toString());
	}

	public static void buildSelectAllSql(EntityInfo entityInfo) {
		StringBuffer sql = getSelectAllSql(entityInfo);
		entityInfo.setSelectAllSql(sql.toString());
	}

	private static StringBuffer getSelectAllSql(EntityInfo entityInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		int columnIndex = 0;
		for (ColumnInfo columnInfo : entityInfo.getColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append(columnInfo.getColumnName());
		}
		for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
			if (columnIndex++ > 0)
				sql.append(",");
			sql.append(columnInfo.getColumnName());
		}
		sql.append(" from ").append(entityInfo.getTableName()).append("  ");
		return sql;
	}

	public static Map<String, Field> getColumnFieldMap(Class<?> clazz) {
		Map<String, Field> columnFieldMap = new HashMap<>();
		Class<?> superclass = clazz.getSuperclass();
		if (null != superclass && !Object.class.equals(superclass)) {
			columnFieldMap.putAll(EntityHelper.getColumnFieldMap(superclass));
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!ClassUtil.isSimpaleType(field.getType()))
				continue;
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isNative(field.getModifiers()))
				continue;
			if (null != field.getAnnotation(Transient.class))
				continue;
			String columnName = field.getName();
			Column column = field.getAnnotation(Column.class);
			if (null != column) {
				columnName = column.name();
			} else {
				XmlElement xmlElement = field.getAnnotation(XmlElement.class);
				if (null != xmlElement) {
					columnName = xmlElement.name();
				}
			}
			field.setAccessible(true);
			columnFieldMap.put(columnName, field);
		}
		return columnFieldMap;
	}

	public static <T> Map<String, Field> getSetColumnMappings(Class<T> targetClass) {
		Map<String, Field> setColumnMappings = SET_COLUMN_MAPPINGS_CACHE.get(targetClass.getName());
		if (null != setColumnMappings)
			return setColumnMappings;

		setColumnMappings = new HashMap<>();
		if (!ClassUtil.isObjectSuperclass(targetClass))
			setColumnMappings.putAll(EntityHelper.getSetColumnMappings(targetClass.getSuperclass()));
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			String columnName = field.getName().toLowerCase();
			field.setAccessible(true);
			setColumnMappings.put(columnName, field);

			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				columnName = column.name().toLowerCase();
			} else {
				XmlElement xmlElement = field.getAnnotation(XmlElement.class);
				if (null != xmlElement) {
					columnName = xmlElement.name().toLowerCase();
				}
			}
			setColumnMappings.put(columnName, field);
		}

		SET_COLUMN_MAPPINGS_CACHE.put(targetClass.getName(), setColumnMappings);
		return setColumnMappings;
	}

	public static <T> Map<String, Object> buildNotNullParams(T entity) {
		List<ColumnInfo> cols = EntityHelper.getEntityInfo(entity.getClass()).getColumnInfos();
		Map<String, Object> params = new HashMap<>();
		for (ColumnInfo col : cols) {
			Field field = col.getColumnField();
			if (ClassUtil.isSimpaleType(field.getType())) {
				try {
					Object value = field.get(entity);
					if (null == value)
						continue;
					params.put(col.getColumnName(), value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return params;
	}

}
