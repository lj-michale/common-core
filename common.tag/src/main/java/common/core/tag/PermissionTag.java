package common.core.tag;

import java.io.IOException;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.StringUtil;
import common.core.web.sso.User;
import common.core.web.sso.UserHolder;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class PermissionTag implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String permissionName = PermissionTag.getParam(params, "code", null);
		if (StringUtil.isEmpty(permissionName)) {
			permissionName = PermissionTag.getParam(params, "permissionName", null);
		}
		AssertErrorUtils.assertTrue(StringUtil.hasText(permissionName), "please config code for PermissionTag");
		boolean flag = false;
		User user = UserHolder.getUser();
		if (user != null) {
			if (user.isSuperAdministrator()) {
				flag = true;
			} else {
				Boolean permission = user.getPermissionCodeMap().get(permissionName);
				if (null != permission && permission == true) {
					flag = true;
				}
			}

		}

		if (flag) {
			body.render(env.getOut());
		}

	}

	static String getRequiredParam(@SuppressWarnings("rawtypes") Map params, String key) throws TemplateException {
		Object value = params.get(key);
		if (value == null || StringUtil.isEmpty(value.toString())) {
			throw new TemplateModelException("not found required parameter:" + key + " for directive");
		}
		return value.toString();
	}

	static String getParam(@SuppressWarnings("rawtypes") Map params, String key, String defaultValue) throws TemplateException {
		Object value = params.get(key);
		return value == null ? defaultValue : value.toString();
	}
}
