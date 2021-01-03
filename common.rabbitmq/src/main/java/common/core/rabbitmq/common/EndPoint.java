package common.core.rabbitmq.common;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class EndPoint {
	protected static final String WF_NOTIFY_QUEUE_NAME = "wf_notify_queue";
	protected ExecutorService executorService = Executors.newFixedThreadPool(2);

	@Value("${rabbit.host}")
	private String host;
	@Value("${rabbit.port}")
	private int port;
	@Value("${rabbit.userName}")
	private String userName;
	@Value("${rabbit.password}")
	private String password;

	public Channel buildChannel() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setAutomaticRecoveryEnabled(true);
		Channel channel = null;
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(WF_NOTIFY_QUEUE_NAME, true, false, false, null);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		return channel;
	}
}
