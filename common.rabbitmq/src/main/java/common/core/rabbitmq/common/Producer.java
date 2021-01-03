package common.core.rabbitmq.common;

import java.io.IOException;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.rabbitmq.common.EndPoint;
import common.core.rabbitmq.entity.Message;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * 发送者
 */
@Component
public class Producer extends EndPoint {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	Channel channel = null;
	
	public void start(){
		logger.info("rabbitmq producer start");
		this.channel = buildChannel();
		logger.info("rabbitmq producer start success");
	}

	public void sendMessage(Message message) {
		try {
			logger.info("rabbitmq sendMessage start.[{}]",message.toString());
			if (channel == null) {
				logger.info("Producer.sendMessage get channel failed.");
				return;
			}
			String sendMessage = JSON.toJSONString(message);
			channel.basicPublish("", WF_NOTIFY_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, sendMessage.getBytes());
			logger.info("rabbitmq sendMessage end.");
		} catch (IOException e) {
			logger.info("rabbitmq sendMessage err.",e);
		}
	}
}
