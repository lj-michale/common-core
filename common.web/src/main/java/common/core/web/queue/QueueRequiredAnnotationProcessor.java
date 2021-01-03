package common.core.web.queue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.method.HandlerMethod;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.web.interceptor.AnnotationProcessor;

public class QueueRequiredAnnotationProcessor implements AnnotationProcessor<QueueRequired> {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueRequiredAnnotationProcessor.class);

	private static Map<String, BlockingQueue<String>> COUNT_MAP = new ConcurrentHashMap<String, BlockingQueue<String>>();
	@Override
	public void preHandle(QueueRequired queueRequired, HandlerMethod handlerMethod) {
		int max = queueRequired.value();
		if(max==0){
			throw new QueueRequiredException(queueRequired.message());
		}
		
		String key = buildKey(handlerMethod);
		BlockingQueue<String> queue;
		synchronized(COUNT_MAP){
			queue = COUNT_MAP.get(key);
			if (null == queue){
				queue = new ArrayBlockingQueue<String>(max);;
				COUNT_MAP.put(key, queue);
			}
			LOGGER.warn("====queue.size:"+queue.size());
			boolean isPass = queue.offer(Thread.currentThread().getName());
			if(!isPass){
				throw new QueueRequiredException(queueRequired.message());
			}
		}
		
		
	}

	@Override
	public void afterHandle(QueueRequired queueRequired, HandlerMethod handlerMethod) {
		String key = buildKey(handlerMethod);
		BlockingQueue<String> queue = COUNT_MAP.get(key);
		queue.poll();
		
	}

	private String buildKey(HandlerMethod handlerMethod) {
		StringBuffer key = new StringBuffer();
		key.append(handlerMethod.getBeanType().getName()).append(".").append(handlerMethod.getMethod().getName());
		key.append(".").append(handlerMethod.getMethod().getParameterCount());
		return key.toString();
	}

	@Override
	public Class<QueueRequired> getAnnotation() {
		return QueueRequired.class;
	}

}
