package common.core.site.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.web.context.RequestContext;

public class DefaultServletViewController extends BaseViewController {
	private final Logger logger = LoggerFactory.getLogger(DefaultServletViewController.class);

	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String all() {
		String url = RequestContext.getRequestURI();
		logger.debug("DefaultServletView:{}", url);
		return url;
	}
}
