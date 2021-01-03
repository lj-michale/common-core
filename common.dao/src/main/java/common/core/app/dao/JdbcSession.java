package common.core.app.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import common.core.app.context.ConfigContext;
import common.core.app.dao.extractor.ColumnLabelsResult;
import common.core.app.dao.extractor.ColumnLabelsResultSetExtractor;
import common.core.app.dao.mapper.RowMapperHelper;
import common.core.app.dao.page.NextPageQuery;
import common.core.app.dao.page.NextPageResult;
import common.core.app.dao.sql.dynamic.NamedParameterPageQuery;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StopWatch;
import common.core.common.util.StringUtil;

public class JdbcSession {
	private final Logger logger = LoggerFactory.getLogger(JdbcSession.class);
	private static final String SQL_PREFIX = "sql.";
	private JdbcHelper jdbcHelper;

	@SuppressWarnings("unchecked")
	public <T> T getByIdWithEntity(T entity) {
		ExecuteInfo selectInfo = EntityHelper.buildSelectInfo(entity);
		return (T) this.findEntity(selectInfo.getSql(), entity.getClass(), selectInfo.getParams());
	}

	public <T> int deleteByIdWithEntity(T entity) {
		ExecuteInfo deleteInfo = EntityHelper.buildDeleteInfo(entity);
		return this.execute(deleteInfo.getSql(), deleteInfo.getParams());
	}

	public <T> T getById(Class<T> entityClass, Object... id) {
		AssertErrorUtils.assertTrue(id.length > 0 && null != id[0], "please put one id");
		String sql = EntityHelper.getEntityInfo(entityClass).getSelectSql();
		return this.findEntity(sql, entityClass, id);
	}

	public <T> int deleteById(Class<T> entityClass, Object... id) {
		AssertErrorUtils.assertTrue(id.length > 0 && null != id[0], "please put one id");
		String sql = EntityHelper.getEntityInfo(entityClass).getDeleteSql();
		return this.execute(sql, id);
	}

	public <T> int insert(T entity) {
		EntityInfo entityInfo = EntityHelper.getEntityInfo(entity.getClass());
		ExecuteInfo insertInfo = EntityHelper.buildInsertInfo(entity);
		if (entityInfo.getColumnInfos().size() + entityInfo.getIdColumnInfos().size() > insertInfo.getParams().length) {
			logger.debug("insert with insertWithReturnKey");
			String id = this.insertWithReturnKey(insertInfo.getSql(), insertInfo.getParams());
			if (StringUtil.isEmpty(id))
				return 0;
			EntityHelper.updateId(entity, id);
			return 1;
		} else {
			logger.debug("insert with execute");
			return this.execute(insertInfo.getSql(), insertInfo.getParams());
		}
	}

	public <T> int updateById(T entity) {
		ExecuteInfo updateInfo = EntityHelper.buildUpdateInfo(entity);
		return this.execute(updateInfo.getSql(), updateInfo.getParams());

	}

	public int updateBySql(String sql, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			int resluts = this.execute(sql, params);
			return resluts;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("updateBySql did not find any result", e);
			return 0;
		} finally {
			logger.debug("updateBySql, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}

	}

	/**
	 * @param sql
	 * @param requiredType
	 *            return row data type, such as User.class,Object[].class
	 * @param params
	 * @return
	 */
	public <T> List<T> findEntitys(String sql, Class<T> requiredType, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			List<T> resluts = jdbcHelper.query(sql, RowMapperHelper.create(requiredType), params);
			return resluts;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findEntitys did not find any result", e);
			return null;
		} finally {
			logger.debug("findEntitys, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * 根据sql、指定的返回数据类型和参数值返回 ColumnLabelsResult对象
	 * 
	 * @param sql
	 * @param requiredType
	 * @param params
	 * @return ColumnLabelsResult
	 */
	public <T> ColumnLabelsResult<T> findColumnLabelsResult(String sql, Class<T> requiredType, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			ColumnLabelsResultSetExtractor<T> columnLabelsResultSetExtractor = new ColumnLabelsResultSetExtractor<T>(requiredType);
			ColumnLabelsResult<T> columnLabelsResult = jdbcHelper.query(sql, columnLabelsResultSetExtractor, params);
			return columnLabelsResult;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findColumnLabelsResult did not find any result", e);
			return null;
		} finally {
			logger.debug("findColumnLabelsResult, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * 根据entity的非空字段生成查询sql，并执行查询
	 * 
	 * @param entity
	 *            实体对象
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findEntitysWithAny(T entity) {
		StopWatch watch = new StopWatch();
		ExecuteInfo executeInfo = EntityHelper.buildSelectWithAnyInfo(entity);
		String sql = executeInfo.getSql();
		Object[] params = executeInfo.getParams();
		Class<T> requiredType = (Class<T>) entity.getClass();
		try {
			sql = this.getSql(sql);
			List<T> resluts = jdbcHelper.query(sql, RowMapperHelper.create(requiredType), params);
			return resluts;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findEntitysWithAny did not find any result", e);
			return null;
		} finally {
			logger.debug("findEntitysWithAny, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * 查询该对象的所有记录
	 * 
	 * @param requiredType
	 *            返回对象类型
	 * @return 对象列表
	 */
	public <T> List<T> findAllEntitys(Class<T> requiredType) {
		StopWatch watch = new StopWatch();
		ExecuteInfo executeInfo = EntityHelper.buildSelectAllInfo(requiredType);
		String sql = executeInfo.getSql();
		Object[] params = executeInfo.getParams();
		try {
			sql = this.getSql(sql);
			List<T> resluts = jdbcHelper.query(sql, RowMapperHelper.create(requiredType), params);
			return resluts;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findAllEntitys did not find any result", e);
			return null;
		} finally {
			logger.debug("findAllEntitys, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * 根据entity的非空字段生成删除条件，并执行删除
	 * 
	 * @param entity
	 *            实体对象
	 * @return 删除行数
	 */
	public <T> int deleteEntitysWithAny(T entity) {
		StopWatch watch = new StopWatch();
		ExecuteInfo executeInfo = EntityHelper.buildDeleteWithAnyInfo(entity);
		String sql = executeInfo.getSql();
		Object[] params = executeInfo.getParams();
		if (null == params || params.length == 0) {
			throw new DaoException("No parameters found");
		}
		try {
			return this.execute(sql, params);
		} finally {
			logger.debug("deleteEntitysWithAny, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * 根据entity的非空字段生成查询sql，并执行查询，返回超过1行将抛出异常
	 * 
	 * @param entity
	 *            实体对象
	 * @return 查询的单行数据对象
	 */
	public <T> T findEntityWithAny(T entity) {
		List<T> resultList = this.findEntitysWithAny(entity);
		if (resultList.size() == 1) {
			return resultList.get(0);
		} else if (resultList.size() > 1) {
			throw new DaoException("get one more result from db");
		}
		return null;
	}

	/**
	 * 根据sql和指定的参数查询，并返回指定封装的实体对象
	 * 
	 * @param sql
	 *            sql
	 * @param requiredType
	 *            指定封装的实体
	 * @param params
	 *            sql参数
	 * @return 指定封装的实体对象
	 */
	public <T> T findEntity(String sql, Class<T> requiredType, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			List<T> resluts = jdbcHelper.query(sql, RowMapperHelper.create(requiredType), params);
			if (null == resluts || resluts.size() == 0)
				return null;
			return resluts.get(0);
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findUniqueResult did not find any result", e);
			return null;
		} finally {
			logger.debug("findUniqueResult, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * @param sql
	 * @param requiredType
	 * @param params
	 * @return list of string,date,int,long,float,double . as [1,2,3] or
	 *         ["abc","efg"]
	 */
	public <T> List<T> findSimpleObjectList(String sql, Class<T> requiredType, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			List<T> resluts = jdbcHelper.queryForList(sql, requiredType, params);
			return resluts;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findSimpleObjectList did not find any result", e);
			return null;
		} finally {
			logger.debug("findSimpleObjectList, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	/**
	 * @param sql
	 * @param requiredType
	 * @param params
	 * @return string,date,int,long,float,double
	 */
	public <T> T findSimpleObject(String sql, Class<T> requiredType, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			return jdbcHelper.queryForObject(sql, requiredType, params);
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findString did not find any result", e);
			return null;
		} finally {
			logger.debug("findSimpleObject, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	public Integer findInteger(String sql, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			return jdbcHelper.queryForObject(sql, Integer.class, params);
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findString did not find any result", e);
			return null;
		} finally {
			logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	public String findString(String sql, Object... params) {
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			return jdbcHelper.queryForObject(sql, String.class, params);
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findString did not find any result", e);
			return null;
		} finally {
			logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
	}

	public TableData findTableData(String sql, Object... params) {
		TableData tableData = new TableData();
		StopWatch watch = new StopWatch();
		try {
			sql = this.getSql(sql);
			TableRowMapperResultSetExtractor tableRowMapperResultSetExtractor = TableRowMapperResultSetExtractor.rowMapper();
			List<Object[]> resluts = jdbcHelper.query(sql, tableRowMapperResultSetExtractor, params);
			tableData.setColumnNames(tableRowMapperResultSetExtractor.getColumnNames());
			tableData.setObjectsList(resluts);
		} catch (EmptyResultDataAccessException e) {
			logger.debug("findEntitys did not find any result", e);
			return null;
		} finally {
			logger.debug("findEntitys, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
		}
		return tableData;
	}

	/**
	 * @param sql
	 * @param params
	 * @return as {["abc",1],["efg",2],}
	 */
	public List<Object[]> findObjectsList(String sql, Object... params) {
		List<Object[]> resluts = this.findEntitys(sql, Object[].class, params);
		return resluts;
	}

	public int execute(String sql, Object... params) {
		StopWatch watch = new StopWatch();
		int updatedRows = 0;
		try {
			sql = this.getSql(sql);
			updatedRows = jdbcHelper.update(sql, params);
			return updatedRows;
		} finally {
			logger.debug("execute, sql={}, params={}, updatedRows={}, elapsedTime={}", sql, params, updatedRows, watch.elapsedTime());
		}
	}

	public int[] batchExecute(String sql, List<Object[]> params) {
		StopWatch watch = new StopWatch();
		int totalUpdatedRows = 0;
		try {
			sql = this.getSql(sql);
			int[] results = jdbcHelper.batchUpdate(sql, params);
			for (int updatedRows : results) {
				totalUpdatedRows += updatedRows;
			}
			return results;
		} finally {
			logger.debug("batchExecute, sql={}, params={}, totalUpdatedRows={}, elapsedTime={}", sql, params, totalUpdatedRows, watch.elapsedTime());
		}
	}

	/**
	 * for calling sp without named out parameter, will preserve the order of
	 * rowMappers, use regular query if only return single result set
	 */
	public ResultSets callSP(StoreProcedure storeProcedure) {
		StopWatch watch = new StopWatch();
		try {
			return storeProcedure.execute(jdbcHelper);
		} finally {
			logger.debug("callSP, sp={}, elapsedTime={}", storeProcedure.getName(), watch.elapsedTime());
		}
	}

	/**
	 * 插入成功，返回主键（单个主键的表）
	 */
	public String insertWithReturnKey(String insertSql, Object... params) {
		StopWatch watch = new StopWatch();
		String sql = this.getSql(insertSql);
		String id = null;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcHelper.update(new PreparedStatementCreator() {
				public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					java.sql.PreparedStatement ps = null;
					try {
						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						for (int i = 0; i < params.length; i++) {
							ps.setObject(i + 1, params[i]);
						}
					} finally {
						if (ps != null) {
							ps.close();
						}
					}

					return ps;
				}
			}, keyHolder);
			id = keyHolder.getKey().toString();
			return id;
		} finally {
			logger.debug("insertBackKey, sql={}, params={},key={},elapsedTime={}", sql, params, id, watch.elapsedTime());
		}
	}

	public String getSql(String sql) {
		if (null == sql)
			return null;
		String result = sql;
		if (result.startsWith(SQL_PREFIX)) {
			result = ConfigContext.getStringValue(result);
			logger.debug("get sql from config {}={}", sql, result);
		}
		List<SqlFilter> sqlFilters = ApplicationContextUtil.getBeans(SqlFilter.class);
		if (null != sqlFilters) {
			logger.debug("filter source sql {}:", result);
			for (SqlFilter sqlFilter : sqlFilters) {
				result = sqlFilter.filterSql(result);
				logger.debug("filter result sql {}:", result);
			}
		}
		return result;
	}

	/**
	 * 分页查询
	 * 
	 * @param pageQuery
	 *            分页查询参数
	 * @return 分页查询的数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> PageResult<T> findDataWithPage(PageQuery<T> pageQuery) {
		if (pageQuery instanceof SqlPageQuery)
			return findDataWithSqlPageQuery((SqlPageQuery) pageQuery);
		if (pageQuery instanceof NamedParameterPageQuery)
			return findDataWithNamedParameterPageQuery((NamedParameterPageQuery) pageQuery);
		return null;
	}

	private <T> PageResult<T> findDataWithNamedParameterPageQuery(NamedParameterPageQuery<T> namedParameterPageQuery) {
		String sql = this.getSql(namedParameterPageQuery.getQuerySql());

		// get count
		String countSql = StringUtil.format("select count(1) from ({}) t__", sql);
		logger.debug("get count : countSql = {}", countSql);
		int count = this.findInteger(countSql, namedParameterPageQuery.getParams());
		logger.debug("get count : count = {}", count);
		PageResult<T> pageResult = new PageResult<T>(namedParameterPageQuery.getPageSize(), namedParameterPageQuery.getPageIndex(), count);
		logger.debug("get count : count  pageResult= {}", pageResult.toString());
		if (count == 0) {
			pageResult.setData(new ArrayList<>());
			return pageResult;
		}

		if (pageResult.getPageTotal() <= namedParameterPageQuery.getPageIndex())
			logger.warn("page index({}) is over total page({})", namedParameterPageQuery.getPageIndex(), pageResult.getPageTotal());

		// get data
		Map<String, Object> pageParams = namedParameterPageQuery.getParams();
		String pageSqlTemplate = null;
		ConnectionPoolDataSource dataScource = (ConnectionPoolDataSource) this.getJdbcTemplate().getDataSource();
		if (dataScource.getDriverClassName().toLowerCase().indexOf("oracle") >= 0) {
			pageSqlTemplate = "select * from(select t__.*,ROWNUM rn from({}) t__ where ROWNUM<=:_ROWNUM) where rn>_rn";
			pageParams.put("_ROWNUM", namedParameterPageQuery.getEndRowIndex());
			pageParams.put("_rn", namedParameterPageQuery.getStartRowIndex());
		} else if (dataScource.getDriverClassName().toLowerCase().indexOf("mysql") >= 0 || dataScource.getDriverClassName().toLowerCase().indexOf("h2") >= 0) {
			pageSqlTemplate = "select t__.* from ({}) t__ limit :_StartRowIndex,:_PageSize";
			pageParams.put("_StartRowIndex", namedParameterPageQuery.getStartRowIndex());
			pageParams.put("_PageSize", namedParameterPageQuery.getPageSize());
		}
		AssertErrorUtils.assertNotNull(pageSqlTemplate, "can't support page with {}", dataScource.getDriverClassName());
		String pageSql = StringUtil.format(pageSqlTemplate, sql);
		logger.debug("get page query : data type = {} , pageSql = {}", namedParameterPageQuery.getReturnType(), pageSql);
		ColumnLabelsResult<T> columnLabelsResult = this.findColumnLabelsResult(pageSql, namedParameterPageQuery.getReturnType(), pageParams);
		buildSumResults(namedParameterPageQuery, sql, pageResult, columnLabelsResult);
		logger.debug("get page query : data number = {}", pageResult.getReturnDataSize());
		return pageResult;
	}

	private <T> void buildSumResults(NamedParameterPageQuery<T> namedParameterPageQuery, String sql, PageResult<T> pageResult, ColumnLabelsResult<T> columnLabelsResult) {
		List<T> data = columnLabelsResult.getDataList();
		pageResult.setData(data);
		int[] sumColumnIndexs = namedParameterPageQuery.getSumColumnIndexs();
		if (null != sumColumnIndexs && sumColumnIndexs.length > 0) {
			logger.debug("get sum data with {}", sumColumnIndexs);
			List<String> columnLabels = columnLabelsResult.getColumnLabels();
			StringBuffer sumSqlStringBuffer = new StringBuffer();
			sumSqlStringBuffer.append("select ");
			for (int i = 0; i < sumColumnIndexs.length; i++) {
				if (i > 0) {
					sumSqlStringBuffer.append(" , ");
				}
				sumSqlStringBuffer.append(" sum(").append(columnLabels.get(sumColumnIndexs[i])).append(") ");
			}
			sumSqlStringBuffer.append("  from ({}) _sum_ ");
			String sumSql = StringUtil.format(sumSqlStringBuffer.toString(), sql);

			pageResult.setSumData(new Object[columnLabels.size()]);
			Object[] sumResults = this.findEntity(sumSql, Object[].class, namedParameterPageQuery.getParams());
			logger.debug("get sum datas {}", sumResults);
			for (int i = 0; i < sumColumnIndexs.length; i++) {
				pageResult.getSumData()[sumColumnIndexs[i]] = sumResults[i];
			}

		}
	}

	private <T> PageResult<T> findDataWithSqlPageQuery(SqlPageQuery<T> sqlPageQuery) {
		String sql = this.getSql(sqlPageQuery.getQuerySql());

		// get count
		String countSql = StringUtil.format("select count(1) from ({}) t__", sql);
		logger.debug("get count : countSql = {}", countSql);
		int count = this.findInteger(countSql, sqlPageQuery.getParams());
		logger.debug("get count : count = {}", count);
		PageResult<T> pageResult = new PageResult<T>(sqlPageQuery.getPageSize(), sqlPageQuery.getPageIndex(), count);
		logger.debug("get count : count  pageResult= {}", pageResult.toString());
		if (count == 0) {
			pageResult.setData(new ArrayList<>());
			return pageResult;
		}

		if (pageResult.getPageTotal() <= sqlPageQuery.getPageIndex())
			logger.warn("page index({}) is over total page({})", sqlPageQuery.getPageIndex(), pageResult.getPageTotal());

		// get data
		List<Object> pageParams = new ArrayList<>(Arrays.asList(sqlPageQuery.getParams()));
		String pageSqlTemplate = null;
		ConnectionPoolDataSource dataScource = (ConnectionPoolDataSource) this.getJdbcTemplate().getDataSource();
		if (dataScource.getDriverClassName().toLowerCase().indexOf("oracle") >= 0) {
			pageSqlTemplate = "select * from(select t__.*,ROWNUM rn from({}) t__ where ROWNUM<=?) where rn>?";
			pageParams.add(sqlPageQuery.getEndRowIndex());
			pageParams.add(sqlPageQuery.getStartRowIndex());
		} else if (dataScource.getDriverClassName().toLowerCase().indexOf("mysql") >= 0 || dataScource.getDriverClassName().toLowerCase().indexOf("h2") >= 0) {
			pageSqlTemplate = "select t__.* from ({}) t__ limit ?,?";
			pageParams.add(sqlPageQuery.getStartRowIndex());
			pageParams.add(sqlPageQuery.getPageSize());
		}
		AssertErrorUtils.assertNotNull(pageSqlTemplate, "can't support page with {}", dataScource.getDriverClassName());
		String pageSql = StringUtil.format(pageSqlTemplate, sql);
		logger.debug("get page query : data type = {} , pageSql = {}", sqlPageQuery.getReturnType(), pageSql);
		ColumnLabelsResult<T> columnLabelsResult = this.findColumnLabelsResult(pageSql, sqlPageQuery.getReturnType(), pageParams.toArray());
		List<T> data = columnLabelsResult.getDataList();
		pageResult.setData(data);
		int[] sumColumnIndexs = sqlPageQuery.getSumColumnIndexs();
		if (null != sumColumnIndexs && sumColumnIndexs.length > 0) {
			logger.debug("get sum data with {}", sumColumnIndexs);
			List<String> columnLabels = columnLabelsResult.getColumnLabels();
			StringBuffer sumSqlStringBuffer = new StringBuffer();
			sumSqlStringBuffer.append("select ");
			for (int i = 0; i < sumColumnIndexs.length; i++) {
				if (i > 0) {
					sumSqlStringBuffer.append(" , ");
				}
				sumSqlStringBuffer.append(" sum(").append(columnLabels.get(sumColumnIndexs[i])).append(") ");
			}
			sumSqlStringBuffer.append("  from ({}) _sum_ ");
			String sumSql = StringUtil.format(sumSqlStringBuffer.toString(), sql);

			pageResult.setSumData(new Object[columnLabels.size()]);
			Object[] sumResults = this.findEntity(sumSql, Object[].class, sqlPageQuery.getParams());
			logger.debug("get sum datas {}", sumResults);
			for (int i = 0; i < sumColumnIndexs.length; i++) {
				pageResult.getSumData()[sumColumnIndexs[i]] = sumResults[i];
			}

		}

		logger.debug("get page query : data number = {}", pageResult.getReturnDataSize());
		return pageResult;
	}

	public <T> NextPageResult<T> findDataWithNextSqlPageQuery(NextPageQuery<T> nextPageQuery) {
		NextPageResult<T> pageResult = new NextPageResult<T>(nextPageQuery.getPageSize());
		String sql = this.getSql(nextPageQuery.getQuerySql());
		logger.debug("nextPageQuery sql : {}", sql);

		// build sql with IndexColumnName
		if (StringUtil.hasText(nextPageQuery.getIndexColumnName()) && !StringUtil.isEmpty(nextPageQuery.getIndexColumnValue())) {
			nextPageQuery.addParam(nextPageQuery.getIndexColumnValue());
			sql = StringUtil.format("select i__.* from ({}) i__ where i__.{} > ?", sql, nextPageQuery.getIndexColumnName());
			logger.debug("build nextPageQuery: sql={}; IndexColumnName={},IndexColumnValue={}", sql, nextPageQuery.getIndexColumnName(), nextPageQuery.getIndexColumnValue());
		}

		// get data
		List<Object> pageParams = new ArrayList<>(Arrays.asList(nextPageQuery.getParams()));
		String pageSqlTemplate = null;
		ConnectionPoolDataSource dataScource = (ConnectionPoolDataSource) this.getJdbcTemplate().getDataSource();
		if (dataScource.getDriverClassName().toLowerCase().indexOf("oracle") >= 0) {
			pageSqlTemplate = "select t__.*  from({}) t__ where ROWNUM<=?";
			pageParams.add(nextPageQuery.getEndRowIndex());
		} else if (dataScource.getDriverClassName().toLowerCase().indexOf("mysql") >= 0 || dataScource.getDriverClassName().toLowerCase().indexOf("h2") >= 0) {
			pageSqlTemplate = "select t__.* from ({}) t__ limit ?";
			pageParams.add(nextPageQuery.getPageSize());
		}
		AssertErrorUtils.assertNotNull(pageSqlTemplate, "can't support page with {}", dataScource.getDriverClassName());
		String pageSql = StringUtil.format(pageSqlTemplate, sql);
		logger.debug("get nextPageQuery : data type = {} , pageSql = {}", nextPageQuery.getReturnType(), pageSql);
		ColumnLabelsResult<T> columnLabelsResult = this.findColumnLabelsResult(pageSql, nextPageQuery.getReturnType(), pageParams.toArray());
		List<T> data = columnLabelsResult.getDataList();
		pageResult.setData(data);
		// 按索引
		if (StringUtil.hasText(nextPageQuery.getIndexColumnName()) && null != data && data.size() > 0 && data.get(0).getClass().isArray()) {
			pageResult.setIndexColumnName(nextPageQuery.getIndexColumnName());
			Object[] lastRowData = (Object[]) data.get(data.size() - 1);
			pageResult.setIndexColumnValue(lastRowData[0].toString());
		}
		logger.debug("get nextPageQuery : data number = {}", pageResult.getReturnDataSize());
		return pageResult;
	}

	public void setDataSource(DataSource dataSource) {
		jdbcHelper = new JdbcHelper(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcHelper;
	}

}
