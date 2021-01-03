package common.core.dubbo.filter;

import java.util.Arrays;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.log.TraceLogger;
import common.core.common.util.UuidUtil;

@Activate(group = Constants.CONSUMER, order = -10000 + 10)
public class EaConsumerFilter implements Filter {
	public final static String DUBBO_REQUEST_ID = "dubboRequestId";
	private final Logger logger = LoggerFactory.getLogger(EaConsumerFilter.class);

	public EaConsumerFilter() {
		super();
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		long start = System.currentTimeMillis();
		try {
			String dubboRequestId = UuidUtil.generateUuid() + "-" + Thread.currentThread().getId();
			invocation.getAttachments().put(DUBBO_REQUEST_ID, dubboRequestId);
			logger.debug("dubbo consumer invoke start thread:{} sendDubboRequestId:{} method:{} arguments:{} , url:{}", Thread.currentThread().getId(), dubboRequestId, invocation.getMethodName(), Arrays.toString(invocation.getArguments()), invoker.getUrl());
			Result result = invoker.invoke(invocation);
			return result;
		} finally {
			long elapsed = System.currentTimeMillis() - start;
			long timeout = invoker.getUrl().getMethodParameter(invocation.getMethodName(), "timeout", Integer.MAX_VALUE);
			logger.debug("dubbo consumer invoke end thread:{}, elapsed:{}ms ", Thread.currentThread().getId(), elapsed);
			if (invoker.getUrl() != null && (elapsed > timeout || (timeout == Integer.MAX_VALUE && TraceLogger.isTimeOut(elapsed, null)))) {
				logger.warn("dubbo consumer invoke time out. method: {} arguments: {} , url is {}, invoke elapsed {} ms.", invocation.getMethodName(), Arrays.toString(invocation.getArguments()), invoker.getUrl(), elapsed);
			}
		}
	}

}
