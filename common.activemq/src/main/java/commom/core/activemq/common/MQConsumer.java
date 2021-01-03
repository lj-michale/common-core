package commom.core.activemq.common;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

@Component
public class MQConsumer implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);
	private String name;
	private Boolean boolType;
	private MQConsumerListerner mqConsumerListerner;
	public static String receiveMessage(String name, Boolean boolType, MQConsumerListerner mqConsumerListerner) {
		logger.info("MQConsumer receive queueName or topicName: {} ", name);
		if (StringUtils.isEmpty(name)) {
			logger.warn("队列名称或者广播名称不能为空。");
			return null;
		}
		String result = null;
		ConnectionFactory connectionFactory;
		Session session;
		Destination destination;
		Destination destinationT;
		connectionFactory = new ActiveMQConnectionFactory(Constants.MQ_NAME, Constants.MQ_PASSWORD, Constants.MQ_BROKETURL);
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			if (boolType) {
				// 创建队列
				destination = session.createQueue(name);
			} else {
				// 创建广播
				destination = session.createTopic(name);
			}
//			Destination destinationT = session.createTopic("QUEUE_ORDER_STATUS");
			// 创建消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			// //注册消息监听
			// consumer.setMessageListener(mqListerner);
			while (true) {
				TextMessage message = (TextMessage) consumer.receive();
				if (null != message) {
					result = message.getText();
					logger.info("MQConsumer receive message ==="+result);
					mqConsumerListerner.receiveMsg(result);
				} else {
					logger.info("MQConsumer receive message is null or '' ");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String receiveMessageByToptic(String name, MQConsumerListerner mqConsumerListerner) {
		logger.info("MQConsumer receive queueName or topicName: {} ", name);
		if (StringUtils.isEmpty(name)) {
			logger.warn("队列名称或者广播名称不能为空。");
			return null;
		}
		String result = null;
		ConnectionFactory connectionFactory;
		Session session;
		Destination destination;
		connectionFactory = new ActiveMQConnectionFactory(Constants.MQ_NAME, Constants.MQ_PASSWORD, Constants.MQ_BROKETURL);
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 创建广播
			destination = session.createTopic(name);
			// 创建消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			// //注册消息监听
			// consumer.setMessageListener(mqListerner);
			while (true) {
				TextMessage message = (TextMessage) consumer.receive();
				if (null != message) {
					result = message.getText();
					message.getJMSType();
					logger.info("MQConsumer receive message ==="+result);
					mqConsumerListerner.receiveMsg(result);
				} else {
					logger.info("MQConsumer receive message is null or '' ");
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public void run() {
		receiveMessage(name, boolType, mqConsumerListerner);
	}


	public MQConsumer(String name, Boolean boolType, MQConsumerListerner mqConsumerListerner) {
		super();
		this.name = name;
		this.boolType = boolType;
		this.mqConsumerListerner = mqConsumerListerner;
	}
}
