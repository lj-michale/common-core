package common.core.app.dao.mongo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.Transient;

import org.bson.Document;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import common.core.app.dao.ColumnInfo;
import common.core.app.dao.DefaultIdBuilder;
import common.core.app.dao.EntityHelper;
import common.core.app.dao.EntityInfo;
import common.core.app.dao.JdbcSession;
import common.core.app.dao.PageResult;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatus;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StopWatch;
import common.core.common.util.StringUtil;

public class MongoSession implements InitializingBean, DisposableBean, ServiceStatusMonitor {

	private final Logger logger = LoggerFactory.getLogger(JdbcSession.class);

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	public MongoSession(MongoSetting mongoSetting) {
		StopWatch watch = new StopWatch();
		try {
			String url = null;
			if (StringUtil.hasText(mongoSetting.getUser())) {
				url = StringUtil.format("mongodb://{}:{}@{}:{}/{}", mongoSetting.getUser(), mongoSetting.getPassword(), mongoSetting.getHost(), mongoSetting.getPort(), mongoSetting.getDatabase());
			} else {
				url = StringUtil.format("mongodb://{}:{}/{}", mongoSetting.getHost(), mongoSetting.getPort(), mongoSetting.getDatabase());
			}
			MongoClientURI uri = new MongoClientURI(url);
			this.mongoClient = new MongoClient(uri);
			this.mongoDatabase = mongoClient.getDatabase(mongoSetting.getDatabase());
		} finally {
			logger.debug("MongoSession.init mongoSetting={},elapsedTime={}", mongoSetting.toString(), watch.elapsedTime());
		}
	}

	public <T> T findOne(Class<T> entityClass, String keyName, Object keyValue) {
		StopWatch watch = new StopWatch();
		T returnObject = null;
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entityClass);
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			FindIterable<Document> findIterable = collection.find(Filters.eq(keyName, keyValue));
			Document doc = findIterable.first();
			if (null == doc)
				return null;
			returnObject = MongoDbObjectUtil.buildEntity(doc, entityClass);
			return returnObject;
		} finally {
			logger.debug("MongoSession.findOne entityClass={},keyName={},keyValue={},returnObject={},elapsedTime={}", entityClass, keyName, keyValue, returnObject, watch.elapsedTime());
		}
	}

	public <T> T findOne(Class<T> entityClass, Object id) {
		StopWatch watch = new StopWatch();
		T returnObject = null;
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entityClass);
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			FindIterable<Document> findIterable = collection.find(Filters.eq(entityInfo.getIdColumnInfos().get(0).getColumnName(), id));
			Document doc = findIterable.first();
			if (null == doc)
				return null;
			returnObject = MongoDbObjectUtil.buildEntity(doc, entityClass);
			return returnObject;
		} finally {
			logger.debug("MongoSession.findOne entityClass={},id={},returnObject={},elapsedTime={}", entityClass, id, returnObject, watch.elapsedTime());
		}
	}

	public <T> T deleteOne(Class<T> entityClass, Object id) {
		StopWatch watch = new StopWatch();
		T returnObject = null;
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entityClass);
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			Document doc = collection.findOneAndDelete(Filters.eq(entityInfo.getIdColumnInfos().get(0).getColumnName(), id));
			if (null == doc)
				return null;
			returnObject = MongoDbObjectUtil.buildEntity(doc, entityClass);
			return returnObject;
		} finally {
			logger.debug("MongoSession.deleteOne entityClass={},id={},returnObject={},elapsedTime={}", entityClass, id, returnObject, watch.elapsedTime());
		}
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public <T> void update(T entity) {
		StopWatch watch = new StopWatch();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			collection.updateOne(MongoDbObjectUtil.buildIdDocument(entity), new Document("$set", MongoDbObjectUtil.buildUpdateDocument(entity)));
		} finally {
			logger.debug("MongoSession.update entity={},elapsedTime={}", entity, watch.elapsedTime());
		}
	}

	/**
	 * 替换
	 * 
	 * @param entity
	 */
	@Deprecated
	public <T> void replace(T entity) {
		StopWatch watch = new StopWatch();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			collection.replaceOne(MongoDbObjectUtil.buildIdDocument(entity), MongoDbObjectUtil.buildUpdateDocument(entity));
		} finally {
			logger.debug("MongoSession.update entity={},elapsedTime={}", entity, watch.elapsedTime());
		}
	}

	public <T> void insert(T entity) {
		StopWatch watch = new StopWatch();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
			for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
				Field idField = columnInfo.getColumnField();
				if (null != idField.get(entity))
					continue;
				idField.set(entity, DefaultIdBuilder.build());
			}
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			Document doc = MongoDbObjectUtil.buildInsertDocument(entity);
			collection.insertOne(doc);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("MongoSession.insert entityClass={},entity={},elapsedTime={}", entity.getClass(), entity, watch.elapsedTime());
		}
	}

	public <T> PageResult<T> findDataWithPage(MongoPageQuery<T> sqlPageQuery) {
		StopWatch watch = new StopWatch();
		String collectionName = sqlPageQuery.getCollectionName();
		PageResult<T> pageResutl = null;
		AssertErrorUtils.assertNotNull(collectionName, "collectionName is null");
		List<T> datas = new ArrayList<>();
		try {
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

			FindIterable<Document> findIterable = collection.find();
			if (sqlPageQuery.getParams().size() > 0) {
				findIterable = findIterable.filter(new Document(sqlPageQuery.getParams()));
			}
			if (sqlPageQuery.getSorts().size() > 0) {
				findIterable = findIterable.sort(new Document(sqlPageQuery.getSorts()));
			}
			int dbRowCount = 0;
			// 是否需要走分页
			if (sqlPageQuery.getPageSize() < Integer.MAX_VALUE && sqlPageQuery.getPageSize() > 0) {
				findIterable = findIterable.skip(sqlPageQuery.getPageSize() * sqlPageQuery.getPageIndex()).limit(sqlPageQuery.getPageSize());
			}
			for (Document document : findIterable) {
				datas.add(MongoDbObjectUtil.buildEntity(document, sqlPageQuery.getReturnType()));
			}
			// count
			if (sqlPageQuery.getPageSize() < Integer.MAX_VALUE && sqlPageQuery.getPageSize() > 0) {
				long count = collection.count(new Document(sqlPageQuery.getParams()));
				AssertErrorUtils.assertTrue(count < Long.valueOf(Integer.MAX_VALUE), "count return too big count={}", count);
				dbRowCount = Integer.valueOf(String.valueOf(count));
			} else {
				dbRowCount = datas.size();
			}

			pageResutl = new PageResult<>(sqlPageQuery.getPageSize(), sqlPageQuery.getPageIndex(), dbRowCount);
			pageResutl.setData(datas);
			return pageResutl;
		} finally {
			logger.debug("MongoSession.insert sqlPageQuery={},pageResutl={},elapsedTime={}", sqlPageQuery, pageResutl, watch.elapsedTime());
		}
	}

	@Override
	public ServiceStatus getServiceStatus() throws Exception {
		this.mongoDatabase.listCollectionNames();
		return ServiceStatus.UP;
	}

	@Override
	public String getServiceName() {
		return this.getClass().getName() + ":" + this.mongoDatabase.getName();
	}

	@Override
	public void destroy() throws Exception {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AssertErrorUtils.assertNotNull(this.mongoDatabase, "mongoDatabase is null");
		AssertErrorUtils.assertNotNull(this.mongoDatabase.getName(), "mongoDatabase name is null");
	}

	/**
	 * 根据指定条件查询
	 * 
	 * @param entityClass
	 * @param filter
	 * @return
	 */
	public <T> List<T> findAll(Class<T> entityClass, Map<String, String> filter) {
		StopWatch watch = new StopWatch();
		List<T> list = new ArrayList<>();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entityClass);
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			BasicDBObject bo = new BasicDBObject();
			for (String key : filter.keySet()) {
				if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(filter.get(key))) {
					Pattern patExpAdd = Pattern.compile(filter.get(key), Pattern.CASE_INSENSITIVE);
					bo.append(key, patExpAdd);
				}
			}
			FindIterable<Document> findIterable = collection.find(bo);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				T returnObject = MongoDbObjectUtil.buildEntity(mongoCursor.next(), entityClass);
				list.add(returnObject);
			}
			return list;
		} finally {
			logger.debug("MongoSession.findAll entityClass={},id={},list.size={},elapsedTime={}", entityClass, list.size(), watch.elapsedTime());
		}
	}
	
	/**
	 * 可用于支持插入带有JSON属性的实体，并过滤Transient
	 * @param entity
	 */
	public <T> void insertOneHasJson(T entity){
		StopWatch watch = new StopWatch();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
			for (ColumnInfo columnInfo : entityInfo.getIdColumnInfos()) {
				Field idField = columnInfo.getColumnField();
				if (null != idField.get(entity))
					continue;
				idField.set(entity, DefaultIdBuilder.build());
			}
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			
			Document doc = new Document();
			Field[] fields = entity.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				if(field.getAnnotation(Transient.class) == null)
					doc.put(field.getName(), field.get(entity));
			}
			collection.insertOne(doc);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("MongoSession.insert entityClass={},entity={},elapsedTime={}", entity.getClass(), entity, watch.elapsedTime());
		}
	}
	
	//不进行模糊匹配，适用复杂匹配规则,$or,$and等
	public <T> List<T> findAllDeep(Class<T> entityClass, Map<String, Object> filter) {
		StopWatch watch = new StopWatch();
		List<T> list = new ArrayList<>();
		try {
			EntityInfo entityInfo = EntityHelper.getEntityInfo(entityClass);
			String collectionName = entityInfo.getTableName();
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			logger.debug("mongoDatabase.getCollection collectionName={}", collectionName);
			BasicDBObject bo = new BasicDBObject();
			for (String key : filter.keySet()) 
					bo.append(key, filter.get(key));
			
			FindIterable<Document> findIterable = collection.find(bo);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				T returnObject = MongoDbObjectUtil.buildEntity(mongoCursor.next(), entityClass);
				list.add(returnObject);
			}
			return list;
		} finally {
			logger.debug("MongoSession.findAll entityClass={},id={},list.size={},elapsedTime={}", entityClass, list.size(), watch.elapsedTime());
		}
	}
}
