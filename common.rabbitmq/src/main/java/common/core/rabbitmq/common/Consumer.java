package common.core.rabbitmq.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ApplicationContextUtil;
import common.core.rabbitmq.entity.Message;
import common.core.rabbitmq.service.ConsumerService;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class Consumer extends EndPoint {
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	private Map<String, ConsumerService> serviceMap = new HashMap<String, ConsumerService>();
	private Channel channel;

	public void start() {
		setServiceMap();
		consumer();
	}

	public void consumer() {
		logger.info("rabbitmq consumer start");
		try {
			channel = buildChannel();
			channel.basicQos(1);
			// 消息消费完成确认
			channel.basicConsume(WF_NOTIFY_QUEUE_NAME, false, new ConsumerHandler(channel));
			logger.info("rabbitmq consumer start success");
		} catch (Exception e) {
			logger.error("rabbitmq consumer start err", e);
		}
	}

	
	/*
	@Autowired
	public void setServiceMap(List<ConsumerService> interfaceList) {
		for (ConsumerService service : interfaceList) {
			serviceMap.put(service.getServiceName(), service);
		}
	}*/
	
	private void setServiceMap(){
		List<ConsumerService> interfaceList = ApplicationContextUtil.getBeans(ConsumerService.class);
		for (ConsumerService service : interfaceList) {
			serviceMap.put(service.getServiceName(), service);
		}
	}

	/**
	 * comsumer handler
	 */
	class ConsumerHandler extends DefaultConsumer {

		public ConsumerHandler(Channel channel) {
			super(channel);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {
			String consumeMessage = new String(body, "UTF-8");
			logger.info("rabbitmq consumer received msg :[{}]", consumeMessage);
			Boolean result = Boolean.TRUE;
			try {
				Message message = JSON.parseObject(consumeMessage, Message.class);
				ConsumerService transService = serviceMap.get(message.getMsgType());
				result = transService.exec(message.getMsgBody());
			} catch (Exception e1) {
				logger.error("rabbitmq consumer exec error", e1);
			} finally {
				logger.info("rabbitmq consumer exec result:[{}]", result);
				if (result) {
					// 确认消息
					channel.basicAck(envelope.getDeliveryTag(), false);
				} else {
					// 丢掉消息
					channel.basicNack(envelope.getDeliveryTag(), false, false);
				}
			}
		}
	}

}
