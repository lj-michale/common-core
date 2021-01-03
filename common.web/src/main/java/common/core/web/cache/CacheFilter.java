package common.core.web.cache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.IoUtil;

public class CacheFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(CacheFilter.class);
	private CacheFilePolicyBuilder cacheFilePolicyBuilder;

	public CacheFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		CacheFilePolicy cacheFilePolicy = this.cacheFilePolicyBuilder.getCapturerFileItem(httpServletRequest, httpServletResponse);
		if (null == cacheFilePolicy) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		if (cacheFilePolicy.isCanCapture()) {
			logger.debug("build cache data");
			CacheResponseWrapper crw = new CacheResponseWrapper(httpServletResponse);
			//ResponseContext.initContext(crw);
			chain.doFilter(request, crw);
			IoUtil.writeToFile(crw.getBytes(), cacheFilePolicy.getFile());
		}
		logger.debug("read from cache data");
		this.cacheFilePolicyBuilder.writeFromCapturerFilePolicy(httpServletResponse, cacheFilePolicy);

	}

	@Override
	public void init(FilterConfig filterConfig) {
		cacheFilePolicyBuilder = new DefaultCacheFilePolicyBuilder();
	}

	@Override
	public void destroy() {
	}

}
