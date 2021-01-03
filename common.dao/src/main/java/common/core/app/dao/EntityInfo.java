package common.core.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import common.core.common.util.StringUtil;

public class EntityInfo {
	private Class<?> entityClass;
	private List<ColumnInfo> idColumnInfos = new ArrayList<>();
	private List<ColumnInfo> columnInfos = new ArrayList<>();
	private String insertSql;
	private String updateSql;
	private String selectSql;
	private String selectAllSql;
	private String deleteSql;
	private String tableName;

	public EntityInfo(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
		tableName = this.entityClass.getSimpleName();
		Table table = this.entityClass.getAnnotation(Table.class);
		if (null != table) {
			tableName = table.name();
			if (StringUtil.hasText(table.catalog())) {
				tableName = table.catalog() + "." + tableName;
			}
			if (StringUtil.hasText(table.schema())) {
				tableName = table.schema() + "." + tableName;
			}
		} else {
			Entity entity = this.entityClass.getAnnotation(Entity.class);
			if (null != entity && StringUtil.isNotBlank(entity.name())) {
				tableName = entity.name();
			}
		}

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public List<ColumnInfo> getIdColumnInfos() {
		return idColumnInfos;
	}

	public void setIdColumnInfos(List<ColumnInfo> idColumnInfos) {
		this.idColumnInfos = idColumnInfos;
	}

	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public String getSelectAllSql() {
		return selectAllSql;
	}

	public void setSelectAllSql(String selectAllSql) {
		this.selectAllSql = selectAllSql;
	}

}
