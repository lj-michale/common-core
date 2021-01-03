package common.core.web.interceptor;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.web.queue.QueueRequired;
import common.core.web.queue.QueueRequiredException;

public class QueueRequiredAnnotationInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(QueueRequiredAnnotationInterceptor.class);
	private static Map<String, BlockingQueue<String>> COUNT_MAP = new ConcurrentHashMap<String, BlockingQueue<String>>();
	 @Override  
	    public boolean preHandle(HttpServletRequest request,  
	            HttpServletResponse response, Object handler) throws Exception {  
		 if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				QueueRequired queueRequired = handlerMethod.getMethodAnnotation(QueueRequired.class);
				if(queueRequired!=null){
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
						logger.warn("====queue.size:"+queue.size());
						boolean isPass = queue.offer(Thread.currentThread().getName());
						if(!isPass){
							throw new QueueRequiredException(queueRequired.message());
						}
					}
				}
		 }
	        return true;  
	    }  
	 	@Override  
	    public void afterCompletion(HttpServletRequest request,  
	            HttpServletResponse response, Object handler, Exception ex)  
	    throws Exception { 
	 		if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				QueueRequired queueRequired = handlerMethod.getMethodAnnotation(QueueRequired.class);
				if(queueRequired!=null){
			 		String key = buildKey(handlerMethod);
					BlockingQueue<String> queue = COUNT_MAP.get(key);
					queue.poll();
					logger.warn("!!!!!queue size:"+queue.size()); 
				}
	 		}
	    }  
	 	private String buildKey(HandlerMethod handlerMethod) {
			StringBuffer key = new StringBuffer();
			key.append(handlerMethod.getBeanType().getName()).append(".").append(handlerMethod.getMethod().getName());
			key.append(".").append(handlerMethod.getMethod().getParameterCount());
			return key.toString();
		}

}
