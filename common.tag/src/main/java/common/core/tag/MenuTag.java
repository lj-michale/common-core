package common.core.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StringEscapeUtil;
import common.core.tag.entity.Resources;
import common.core.web.sso.MenuItem;
import common.core.web.sso.User;
import common.core.web.sso.UserHolder;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class MenuTag implements TemplateDirectiveModel {

	public static final String CURRENT_MENU_CODE = "CURRENT_MENU_CODE";
	private ActPermissionService actPermissionService;

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		User user = UserHolder.getUser();
		if (null == user)
			return;
		@SuppressWarnings("unchecked")
		boolean buildNavigation = Boolean.valueOf(params.getOrDefault("buildNavigation", "true").toString());
		TemplateModel currentMenuCodeTemplateModel = env.getVariable(MenuTag.CURRENT_MENU_CODE);
		String currentMenuCode = null == currentMenuCodeTemplateModel ? null : currentMenuCodeTemplateModel.toString();
		List<Resources> resources = null;
		if (buildNavigation) {
			if (null == actPermissionService) {
				actPermissionService = ApplicationContextUtil.getBean(ActPermissionService.class);
				AssertErrorUtils.assertNotNull(actPermissionService, "UserService is not register");
			}
			resources = actPermissionService.queryResourcesStartWithByCODE(currentMenuCode);
			if (resources != null) {
				if (resources.size() > 0) {
					currentMenuCode = resources.get(resources.size() - 1).getResourceCode();
				}
			}
		}
		// String menuBody = buildMenuBody(user, currentMenuCode,resources);
		String menuBody = buildMutilLevelMenu(user, currentMenuCode, resources);
		env.getOut().write(menuBody);
	}

	@SuppressWarnings("unused")
	private String buildMenuBody(User user, String currentMenuCode, List<Resources> resources) {
		StringBuffer menu = new StringBuffer();
		List<MenuItem> menus = user.getMenuItemTree();

		menu.append("<div class=\"yytNav\"><ul>");
		MenuItem item = null;
		for (int i = 0; null != menus && i < menus.size(); i++) {
			item = menus.get(i);
			menu.append("<li");
			if (item.getCode().equals(currentMenuCode)) {
				menu.append(" class=\"hot\"");
			}
			menu.append(">");
			menu.append("<a href=\"").append(item.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(item.getName())).append("</a>");
			if (null != item.getSubMenuItems() && item.getSubMenuItems().size() > 0) {
				menu.append("<div class=\"subNav\">");
				for (MenuItem menuItem : item.getSubMenuItems()) {
					menu.append("<a href=\"").append(menuItem.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(menuItem.getName())).append("</a>");
				}
				menu.append("</div>");
			}
			menu.append("</li>");
		}

		menu.append("</ul></div>");
		return menu.toString();
	}

	private String buildMutilLevelMenu(User user, String currentMenuCode, List<Resources> resources) {
		StringBuffer menu = new StringBuffer();
		List<MenuItem> menus = user.getMenuItemTree();

		menu.append("<ul id=\"sample-menu-1\" class=\"sf-menu\">");
		MenuItem item = null;
		for (int i = 0; null != menus && i < menus.size(); i++) {
			item = menus.get(i);
			menu.append("<li");
			if (item.getCode().equals(currentMenuCode)) {
				menu.append(" class=\"hot\"");
			}
			menu.append(">");
			menu.append("<a href=\"").append(item.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(item.getName())).append("</a>");

			// 二级菜单
			if (null != item.getSubMenuItems() && item.getSubMenuItems().size() > 0) {
				menu.append("<ul style=\"display: block;\">");
				for (MenuItem menuItem2 : item.getSubMenuItems()) {
					// 三级菜单
					// 有三级
					if (null != menuItem2.getSubMenuItems() && menuItem2.getSubMenuItems().size() > 0) {
						menu.append(" <li class=\"current hot\">");
						menu.append("<a href=\"").append(menuItem2.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(menuItem2.getName())).append("<span class=\"jtiocn\"></span></a>");
						menu.append("<ul style=\"display: block;\">");

						for (MenuItem menuItem3 : menuItem2.getSubMenuItems()) {
							menu.append("<li><a href=\"").append(menuItem3.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(menuItem3.getName())).append("</a></li>");
						}
						menu.append("</ul></li>");
					} else {
						// 没有三级菜单
						menu.append("<li><a href=\"").append(menuItem2.getUrl()).append("\">").append(StringEscapeUtil.escapeHtml4(menuItem2.getName())).append("</a></li>");
					}

				}
				menu.append("</ul>");
			}
			menu.append("</li>");
		}
		menu.append("</ul>");
		if (resources != null) {
			if (resources.size() > 1) {
				menu.append("<div class=\"dqText\" >当前位置：");
				String rString = "";
				for (int i = resources.size() - 1; i >= 0; i--) {
					rString = rString + resources.get(i).getResourceName() + "->";

				}
				rString = rString.substring(0, rString.length() - 2);
				menu.append(rString);
				menu.append("</div>");
			}
		}
		return menu.toString();
	}
}
