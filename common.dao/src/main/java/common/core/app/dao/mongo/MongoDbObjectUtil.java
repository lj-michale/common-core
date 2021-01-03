package common.core.app.dao.mongo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.bson.Document;

import common.core.app.dao.ColumnInfo;
import common.core.app.dao.EntityHelper;
import common.core.app.dao.converter.AllConverterFieldBuilder;
import common.core.app.dao.crypto.AllCryptoFieldBuilder;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ClassUtil;

public class MongoDbObjectUtil {

	public static <T> T buildEntity(Document doc, Class<T> entityClass) {
		T entity = ClassUtil.newInstance(entityClass);
		Map<String, Field> cols = EntityHelper.getColumnFieldMap(entityClass);
		for (Map.Entry<String, Field> col : cols.entrySet()) {
			if (!doc.containsKey(col.getKey()))
				continue;
			Object value = doc.get(col.getKey());
			if (null == value)
				continue;

			Field field = col.getValue();
			if (ClassUtil.isSimpaleType(field.getType())) {
				try {
					value = AllCryptoFieldBuilder.get().decode(entity, field, value);
					value = AllConverterFieldBuilder.get().convert(entity, field, value);
					field.set(entity, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return entity;
	}

	public static Map<String, Object> buildObjectMap(Document doc) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, Object> item : doc.entrySet()) {
			result.put(item.getKey(), item.getValue());
		}
		return result;
	}

	public static Object[] buildObjectArray(Document doc, String[] attributeNames) {
		Object[] objs = new Object[attributeNames.length];
		for (int i = 0; i < objs.length; i++) {
			if (!doc.containsKey(doc))
				continue;
			objs[i] = doc.get(attributeNames[i]);
		}
		return objs;
	}

	@SuppressWarnings("unchecked")
	public static <T> Document buildInsertDocument(T entity) {
		return MongoDbObjectUtil.buildDocument(entity);
	}

	@SuppressWarnings("unchecked")
	public static <T> Document buildUpdateDocument(T entity) {
		return MongoDbObjectUtil.buildDocument(entity, Id.class);
	}

	@SuppressWarnings("unchecked")
	private static <T, A extends Annotation> Document buildDocument(T entity, Class<A>... ignoreAnnotationClasz) {
		if (entity instanceof Map) {
			return buildDocumentFromMap((Map<String, Object>) entity);
		}
		Map<String, Field> cols = EntityHelper.getColumnFieldMap(entity.getClass());
		Document doc = new Document();
		for (Map.Entry<String, Field> col : cols.entrySet()) {
			Field field = col.getValue();

			boolean ignore = false;
			for (Class<A> annotationClass : ignoreAnnotationClasz) {
				if (null != field.getAnnotation(annotationClass))
					ignore = true;
			}
			if (ignore)
				continue;

			if (ClassUtil.isSimpaleType(field.getType())) {
				try {
					Object value = field.get(entity);
					value = AllCryptoFieldBuilder.get().encode(entity, field, value);
					doc.put(col.getKey(), value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return doc;
	}

	public static Document buildDocumentFromMap(Map<String, Object> objMap) {
		Document doc = new Document(objMap);
		return doc;
	}

	public static <T> Document buildIdDocument(T entity) {
		List<ColumnInfo> cols = EntityHelper.getEntityInfo(entity.getClass()).getIdColumnInfos();
		Document doc = new Document();
		for (ColumnInfo col : cols) {
			Field field = col.getColumnField();
			if (ClassUtil.isSimpaleType(field.getType())) {
				try {
					Object value = field.get(entity);
					AssertErrorUtils.assertNotNull(value, "field id {} is null", field);
					doc.put(col.getColumnName(), value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

		}
		AssertErrorUtils.assertFalse(doc.isEmpty(), "can't build buildIdDocument {}", entity);
		return doc;
	}

	public static <T> Document buildNotNullDocument(T entity) {
		List<ColumnInfo> cols = EntityHelper.getEntityInfo(entity.getClass()).getColumnInfos();
		Document doc = new Document();
		for (ColumnInfo col : cols) {
			Field field = col.getColumnField();
			if (ClassUtil.isSimpaleType(field.getType())) {
				try {
					Object value = field.get(entity);
					if (null == value)
						continue;
					doc.put(col.getColumnName(), value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return doc;
	}

}
