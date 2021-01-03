package commom.core.activemq.common;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class MQProductHelper {
	private static final Logger logger = LoggerFactory.getLogger(MQProductHelper.class);
	private static PooledConnectionFactory poolFactory;

	/**
	 * 获取单例的PooledConnectionFactory
	 * 
	 * @return
	 * @throws JMSException
	 */
	private static synchronized PooledConnectionFactory getPooledConnectionFactory() throws JMSException {
		logger.info("getPooledConnectionFactory");
		if (poolFactory != null)
			return poolFactory;
		logger.info("getPooledConnectionFactory create new");
		String userName = Constants.MQ_NAME;
		String password = Constants.MQ_PASSWORD;
		String url = Constants.MQ_BROKETURL;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, url);
		connectionFactory.createConnection();
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, url);
		poolFactory = new PooledConnectionFactory();
		poolFactory.setConnectionFactory(factory);
		// 池中借出的对象的最大数目
		poolFactory.setMaxConnections(100);
		// connection中最大连接seesion
		poolFactory.setMaximumActiveSessionPerConnection(50);
		// 后台对象清理时，休眠时间超过了3000毫秒的对象为过期
		poolFactory.setTimeBetweenExpirationCheckMillis(3000);
		logger.info("getPooledConnectionFactory create success");
		return poolFactory;
	}

	/**
	 * 1.对象池管理connection和session,包括创建和关闭等 2.PooledConnectionFactory缺省设置MaxIdle为1，
	 * 
	 * @return
	 * @throws JMSException
	 */
	public static Session createSession(Boolean bool, Integer ack_mode) throws JMSException {
		PooledConnectionFactory poolFactory = getPooledConnectionFactory();
		PooledConnection pooledConnection = (PooledConnection) poolFactory.createConnection();
		return pooledConnection.createSession(bool, ack_mode);
	}

}