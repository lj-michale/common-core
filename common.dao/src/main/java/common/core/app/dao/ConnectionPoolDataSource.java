package common.core.app.dao;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.status.ServiceStatus;
import common.core.common.util.StringUtil;

public class ConnectionPoolDataSource extends BasicDataSource implements InitializingBean, DisposableBean, ServiceStatusMonitor {
    private String databaseName;
    private final JdbcSession jdbcAccess = new JdbcSession();

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtil.hasText(databaseName))
            throw new IllegalArgumentException("databaseName is required");
        if (!StringUtil.hasText(getValidationQuery()))
            throw new IllegalArgumentException("validation query is required for connection pool");

        jdbcAccess.setDataSource(this);
    }

    @Override
    public void destroy() throws Exception {
        close();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("not supported");
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        jdbcAccess.findString(getValidationQuery());
        return ServiceStatus.UP;
    }

    @Override
    public String getServiceName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
