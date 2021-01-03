package commom.core.activemq.common;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

/**
 * <p>
 * MSProduct 点对点模型-消息生产者
 * <p>
 */
@Component
public class MQProduct {
	private static final Logger logger = LoggerFactory.getLogger(MQProduct.class);

	private static void sendMessage(Boolean bool, Integer ack_mode, String name, String message, Boolean boolType) {
		logger.info("MQProduct send queueName or topicName: {} ", name);
		if (StringUtils.isEmpty(name)) {
			logger.warn("队列名称或者广播名称不能为空。");
			return;
		}
		logger.info("MQProduct send msg: {} ", message);
		if (StringUtils.isEmpty(message)) {
			logger.warn("发送消息不能为空。");
			return;
		}
		// 收发的线程实例
		Session session = null;
		// 消息发送目标地址
		Destination destination;
		// 消息创建者
		MessageProducer messageProducer;
		try {
			// 创建接收或发送的线程实例（创建session的时候定义是否要启用事务，且事务类型是Auto_ACKNOWLEDGE也就是消费者成功在Listern中获得消息返回时，会话自动确定用户收到消息）
			session = MQProductHelper.createSession(bool, ack_mode);
			if (boolType) {
				// 创建队列（消息的目的地;消息发送给谁）
				destination = session.createQueue(name);
			} else {
				// 创建广播
				destination = session.createTopic(name);
			}
			// 创建消息生产者，消息发送者
			messageProducer = session.createProducer(destination);
			// 创建TextMessage消息实体
			TextMessage testMessage = session.createTextMessage(message);
			messageProducer.send(testMessage);
			if (bool) {
				session.commit();
			}
			logger.info("MQProduct send message success");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessageByQueue(String queueName, String message) {
		sendMessage(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE, queueName, message, Boolean.TRUE);
	}

	public static void sendMessageByQueue(Boolean bool, Integer ack_mode, String queueName, String message) {
		sendMessage(bool, ack_mode, queueName, message, Boolean.TRUE);
	}

	public static void sendMessageByTopic(String topicName, String message) throws JMSException {
		sendMessage(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE, topicName, message, Boolean.FALSE);
	}

	public void sendMessageByTopic(Boolean bool, Integer ack_mode, String topicName, String message) throws JMSException {
		sendMessage(bool, ack_mode, topicName, message, Boolean.FALSE);
	}
}
