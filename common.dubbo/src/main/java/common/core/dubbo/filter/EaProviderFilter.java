package common.core.dubbo.filter;

import java.util.Arrays;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import common.core.app.log.LogConstants;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.log.TraceLogger;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;

@Activate(group = Constants.PROVIDER, order = -10000 + 10)
public class EaProviderFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(EaProviderFilter.class);

	public EaProviderFilter() {
		super();
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		long start = System.currentTimeMillis();
		try {
			TraceLogger.get().initialize();
			String requestId = invocation.getAttachment(EaConsumerFilter.DUBBO_REQUEST_ID);
			if (StringUtil.isBlank(requestId)) {
				requestId = UuidUtil.generateUuid();
			}
			TraceLogger.putContext(LogConstants.MDC_REQUEST_ID, requestId);
			TraceLogger.putContext(LogConstants.MDC_ACTION, invoker.getInterface().getSimpleName());
			TraceLogger.putContext(LogConstants.MDC_ACTION_METHOD, invocation.getMethodName());
			logger.debug("dubbo provider invoke thread:{} receiveDubboRequestId:{}  method:{} arguments:{} , url:{} ", Thread.currentThread().getId(), requestId, invocation.getMethodName(), Arrays.toString(invocation.getArguments()), invoker.getUrl());
			Result result = invoker.invoke(invocation);
			return result;
		} finally {
			long elapsed = System.currentTimeMillis() - start;
			long timeout = invoker.getUrl().getMethodParameter(invocation.getMethodName(), "timeout", Integer.MAX_VALUE);
			logger.debug("dubbo provider invoke end thread:{}, elapsed:{}ms ", Thread.currentThread().getId(), elapsed);
			if (invoker.getUrl() != null && (elapsed > timeout || (timeout == Integer.MAX_VALUE && TraceLogger.isTimeOut(elapsed, null)))) {
				logger.warn("dubbo provider invoke time out. method: {} arguments: {} , url is {}, invoke elapsed {} ms.", invocation.getMethodName(), Arrays.toString(invocation.getArguments()), invoker.getUrl(), elapsed);
			}
			TraceLogger.get().cleanup(false);
		}

	}

}
