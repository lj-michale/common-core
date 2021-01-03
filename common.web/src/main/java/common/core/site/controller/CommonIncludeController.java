package common.core.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.core.site.scheme.HttpScheme;
import common.core.site.scheme.Scheme;
import common.core.web.context.RequestContext;
import common.core.web.context.Schemes;
import common.core.web.url.UrlHelper;

@Controller
public class CommonIncludeController {
	private static final String URL = "/top-refresh";
	private static final String HTML = "<html><head></head><body><script>" + "document.domain = \"%s\"; if (top.location) " + "{top.location.reload();} " + "else " + "{window.locaction.href = \"%s\";} </script></body></html>";

	@RequestMapping(value = URL, produces = "text/html")
	@HttpScheme(Scheme.HTTP)
	@ResponseBody
	public String topRefresh() {
		String url = UrlHelper.build(URL, Schemes.HTTP);
		return String.format(HTML, RequestContext.getDomain(), url);
		// return SCRIPT;
	}
	@RequestMapping(value = "/crossdomain.xml", produces = "application/xml")
	@HttpScheme(Scheme.HTTP)
	@ResponseBody
	public String crossdomain() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cross-domain-policy><allow-access-from domain=\"*\"/></cross-domain-policy>";
	}

}
