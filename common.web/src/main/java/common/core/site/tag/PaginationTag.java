package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.site.page.PaginationHelper;
import common.core.site.page.PaginationInfo;
import common.core.site.view.ViewContext;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PaginationTag extends TagSupport {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		Writer out = env.getOut();
		out.write(buildPagination(env, params));
	}

	protected String buildPagination(Environment env, @SuppressWarnings("rawtypes") Map params) throws IOException {
		String paginationInfoName = getRequiredStringParam(params, "paginationInfo");
		PaginationInfo paginationInfo = (PaginationInfo) ViewContext.get(paginationInfoName);
		AssertErrorUtils.assertTrue(null != paginationInfo, "can't load paginationInfo[{}] from ViewContext", "paginationInfo");
		return PaginationHelper.build(paginationInfo);
	}

}
