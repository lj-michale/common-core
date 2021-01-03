package common.core.site.page;

import java.text.Format;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.core.app.dao.PageResult;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.ClassUtil;
import common.core.common.util.EncodingUtil;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StringEscapeUtil;
import common.core.common.util.StringUtil;
import common.core.site.tag.select.SelectDataService;
import common.core.site.view.button.Button;
import common.core.site.view.button.ButtonHelper;

public class PaginationHelper {
	private static final String[] TEMPLATE_PAGINATION_WRA = { "<div class=\"dataBable action-pagination-table\">", "</div>" };
	private static final String[] TEMPLATE_TABLE_WARP = { "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "</table>" };
	private static final String[] TEMPLATE_TITLE_WRA = { "<thead><tr>", "</tr></thead>" };
	private static final String[] TEMPLATE_TITLE_ITEM_WRA = { "<th>", "</th>" };
	private static final String[] TEMPLATE_BODY_WRAP = { "<tbody>", "</tbody>" };
	private static final String[] TEMPLATE_BODY_ROW_WRAP_0 = { "<tr>", "</tr>" };
	private static final String[] TEMPLATE_BODY_ROW_WRAP_1 = { "<tr class=\"bg\">", "</tr>" };
	private static final String[] TEMPLATE_BODY_ROW_ITEM_WRAP = { "<td>", "</td>" };
	private static final String[] TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP = { "<div class=\"tar\">", "</div>" };
	private static final String[] TEMPLATE_PAGE_WRA = { "<div class=\"pages\"><div class\"pagination\">", "</div></div>" };
	private static final String[] TEMPLATE_PAGE_NAV_WRA = { "<div class=\"pagination\">", "</div>" };
	private static final String[] TEMPLATE_PAGE_TOTAL_WRA = { "<div class=\"searchPage\"><span class=\"page-sum\">", "</span></div>" };
	private static final String TEMPLATE_PAGE_CURRENT_CLASS = "current";
	private static final String TEMPLATE_PAGE_FIRST_CLASS = "first";
	private static final String TEMPLATE_PAGE_LAST_CLASS = "last";
	private static final String TEMPLATE_PAGE_PRE_CLASS = "prev";
	private static final String TEMPLATE_PAGE_NEX_CLASS = "next";
	private static final String TABLE_HEADER_CHECK_BOX = "<input type=\"checkbox\" class=\"table-header-check-box\"/>";
	private static final String[] TABLE_BAODY_ROW_CHECK_BOX = { "<input type=\"checkbox\"  name=\"ids\" class=\"table-body-row-check-box\" value=\"", "\"/>" };

	private static SelectDataService selectDataService;

	public static String build(PaginationInfo paginationInfo) {
		StringBuffer html = new StringBuffer();
		html.append(TEMPLATE_PAGINATION_WRA[0]);
		html.append(TEMPLATE_TABLE_WARP[0]);
		buildHeader(paginationInfo, html);
		buildBody(paginationInfo, html);
		html.append(TEMPLATE_TABLE_WARP[1]);
		buildPage(paginationInfo, html);
		buildHiddenIds(paginationInfo, html);
		html.append(TEMPLATE_PAGINATION_WRA[1]);
		return html.toString();
	}

	private static void buildHiddenIds(PaginationInfo paginationInfo, StringBuffer html) {
		if (paginationInfo.isBuildSelectBox())
			return;
		if (StringUtil.isEmpty(paginationInfo.getBuildHiddenIdsName()))
			return;
		if (null == paginationInfo.getPageResult() || null == paginationInfo.getPageResult().getData() || paginationInfo.getPageResult().getData().size() == 0)
			return;
		List<Object[]> data = paginationInfo.getPageResult().getData();
		for (Object[] objects : data) {
			html.append("<input type=\"hidden\" value=\"").append(objects[0].toString()).append("\" name=\"").append(paginationInfo.getBuildHiddenIdsName()).append("\"/>");
		}

	}

	private static void buildPage(PaginationInfo paginationInfo, StringBuffer html) {
		if (!paginationInfo.isBuildPagingNavigation())
			return;
		PageResult<Object[]> pageResult = paginationInfo.getPageResult();
		if (pageResult.getDbRowCount() > 0) {
			html.append(TEMPLATE_PAGE_WRA[0]);
			buildNav(html, paginationInfo, pageResult);
			html.append(TEMPLATE_PAGE_TOTAL_WRA[0]).append("共").append(pageResult.getPageTotal()).append("页|共").append(pageResult.getDbRowCount()).append("条").append(TEMPLATE_PAGE_TOTAL_WRA[1]);
			html.append(TEMPLATE_PAGE_WRA[1]);
		}
	}

	private static void buildNav(StringBuffer html, PaginationInfo paginationInfo, PageResult<Object[]> pageResult) {
		int pageTotal = pageResult.getPageTotal();
		int pageIndex = pageResult.getPageIndex();
		int firstPageIndex = 0;
		int lastPageIndex = pageTotal > firstPageIndex ? pageTotal - 1 : firstPageIndex;
		int prePageIndex = pageIndex > firstPageIndex ? pageIndex - 1 : firstPageIndex;
		int nextPageIndex = pageIndex < lastPageIndex ? pageIndex + 1 : lastPageIndex;
		html.append(TEMPLATE_PAGE_NAV_WRA[0]);
		if (pageIndex > firstPageIndex) {
			buildNavItem(html, paginationInfo, firstPageIndex, TEMPLATE_PAGE_FIRST_CLASS, "|&lt;&lt;");
			buildNavItem(html, paginationInfo, prePageIndex, TEMPLATE_PAGE_PRE_CLASS, "&lt;");
		}
		buildNavItem(html, paginationInfo, pageIndex, TEMPLATE_PAGE_CURRENT_CLASS, "" + (pageIndex + 1) + "");
		if (pageIndex < lastPageIndex) {
			buildNavItem(html, paginationInfo, nextPageIndex, TEMPLATE_PAGE_NEX_CLASS, "&gt;");
			buildNavItem(html, paginationInfo, lastPageIndex, TEMPLATE_PAGE_LAST_CLASS, "&gt;&gt;|");
		}
		html.append(TEMPLATE_PAGE_NAV_WRA[1]);
	}

	public static void buildNavItem(StringBuffer html, PaginationInfo paginationInfo, int pageIndex, String itemClass, String title) {
		html.append("<a").append(" class=\"").append(itemClass);
		if (StringUtil.isNotBlank(paginationInfo.getRendTo())) {
			html.append(" action-ajax-btn ");
		}
		html.append("\"");
		if (StringUtil.isNotBlank(paginationInfo.getRendTo())) {
			html.append(" data-action-html=\"");
			html.append(paginationInfo.getRendTo()).append("\"");

			ButtonHelper.appendWaitImg(html);
		}
		html.append("  href=\"").append(paginationInfo.getUrl()).append(pageIndex).append("\" ");
		html.append(">").append(title);
		html.append("</a>");
	}

	private static void buildBody(PaginationInfo paginationInfo, StringBuffer html) {
		html.append(TEMPLATE_BODY_WRAP[0]);
		if (paginationInfo.getPageResult().getDbRowCount() > 0) {
			buildDataBody(paginationInfo, html);
			buildSumRow(paginationInfo, html);
		} else {
			buildEmptyBody(paginationInfo, html);
		}

		html.append(TEMPLATE_BODY_WRAP[1]);
	}

	public static String formatData(PaginationInfo paginationInfo, int rowDataIndex, int rowIndex, int colIndex) {
		Object[] rowDatas = paginationInfo.getPageResult().getData().get(rowIndex);
		Object data = rowDatas[rowDataIndex];
		if (null == data)
			return null;
		Format format = getFormat(paginationInfo, colIndex);
		if (null != format) {
			if (format instanceof RowCellFormat) {
				data = new RowCellFormatParam(paginationInfo, rowDataIndex, rowIndex, colIndex);
			}
			return format.format(data);
		}
		return ObjectUtil.toString(data);
	}

	public static Format getFormat(PaginationInfo paginationInfo, int colIndex) {
		if (null != paginationInfo.getFormats() && paginationInfo.getFormats().length > colIndex && null != paginationInfo.getFormats()[colIndex]) {
			return paginationInfo.getFormats()[colIndex];
		}
		return null;
	}

	private static void buildSumRow(PaginationInfo paginationInfo, StringBuffer html) {
		Object[] sumData = paginationInfo.getPageResult().getSumData();
		if (null == sumData)
			return;
		int rowIndex = 0;
		if (null != paginationInfo.getPageResult())
			rowIndex = paginationInfo.getPageResult().getData().size();
		buildRowDataPrefix(paginationInfo, html, rowIndex, null);

		int titleCol = paginationInfo.getTitles().length;
		int firstRenderColumnIndex = paginationInfo.getFirstRenderColumnIndex();
		for (int rowDataIndex = firstRenderColumnIndex; rowDataIndex < titleCol + firstRenderColumnIndex; rowDataIndex++) {
			int colIndex = rowDataIndex - firstRenderColumnIndex;
			Object item = sumData[rowDataIndex];
			if (colIndex == 0) {
				item = StringUtil.format("[{}条数据合计]", paginationInfo.getPageResult().getDbRowCount());
			} else if (item == null) {
				item = "";
			} else {
				Format format = getFormat(paginationInfo, colIndex);
				if (null != format) {
					item = format.format(item);
				}
			}

			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[0]);
			html.append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[0]).append(item).append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[1]);
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[1]);
		}

		buildRowDataSuffix(paginationInfo, html, rowIndex, null);

	}

	private static void buildDataBody(PaginationInfo paginationInfo, StringBuffer html) {
		int titleCol = paginationInfo.getTitles().length;
		List<Object[]> dataList = paginationInfo.getPageResult().getData();
		int rowIndex = 0;
		int firstRenderColumnIndex = paginationInfo.getFirstRenderColumnIndex();
		Map<String, List<Object[]>> selectDataMap = null;
		if (paginationInfo.getPageResult().getPageSize() > 0 && null != dataList) {
			for (Object[] objects : dataList) {
				buildRowDataPrefix(paginationInfo, html, rowIndex, objects);

				for (int rowDataIndex = firstRenderColumnIndex; rowDataIndex < titleCol + firstRenderColumnIndex; rowDataIndex++) {
					int colIndex = rowDataIndex - firstRenderColumnIndex;
					String item = formatData(paginationInfo, rowDataIndex, rowIndex, colIndex);
					if (null != paginationInfo.getSelectDataOptions() && colIndex < paginationInfo.getSelectDataOptions().length && null != paginationInfo.getSelectDataOptions()[colIndex]) {
						SelectDataOption selectDataOption = paginationInfo.getSelectDataOptions()[colIndex];
						if (null == selectDataService)
							selectDataService = ApplicationContextUtil.getBean(SelectDataService.class);
						if (null == selectDataMap)
							selectDataMap = new HashMap<String, List<Object[]>>();
						List<Object[]> data = selectDataMap.get(selectDataOption.toString());
						if (null == data) {
							data = selectDataService.getDataSouce(selectDataOption.getDataSouceType(), selectDataOption.getDataSouceFrom());
							selectDataMap.put(selectDataOption.toString(), data);
						}
						for (Object[] selectData : data) {
							if (selectData[0].equals(item)) {
								item = StringEscapeUtil.escapeHtml4(selectData[1].toString());
								break;
							}
						}

					} else if (null != paginationInfo.getTitleButtons() && (colIndex) < paginationInfo.getTitleButtons().length && null != paginationInfo.getTitleButtons()[colIndex]) {
						Button button = paginationInfo.getTitleButtons()[colIndex];
						if (StringUtil.hasText(item) && button.showButton(objects) && (StringUtil.hasText(button.getTitle()) || null != objects[rowDataIndex])) {
							String permissionCode = button.getPermissionCode();
							button = ButtonHelper.createButton(StringUtil.hasText(button.getTitle()) ? button.getTitle() : objects[rowDataIndex].toString(), buildUrlWithObjects(button.getUrl(), objects), button.getType(), button.getShowButtonFilter());
							button.setPermissionCode(permissionCode);
							item = ButtonHelper.buildButtonHtml(button);
						}
					} else if (null == getFormat(paginationInfo, colIndex)) {
						item = null == item ? null : StringEscapeUtil.escapeHtml4(item);
					}
					item = null == item ? "" : item;

					html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[0]);
					Format format = getFormat(paginationInfo, colIndex);
					if ((null != format && (format instanceof NumberCellFormat || format instanceof NumberFormat)) || (null != objects[rowDataIndex] && ClassUtil.isNumberType(objects[rowDataIndex].getClass()))) {
						html.append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[0]).append(item).append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[1]);
					} else {
						html.append(item);
					}
					html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[1]);
				}
				buildRowDataSuffix(paginationInfo, html, rowIndex, objects);
				rowIndex++;
			}
		}
	}

	private static void buildRowDataSuffix(PaginationInfo paginationInfo, StringBuffer html, int rowIndex, Object[] objects) {
		if (null != paginationInfo.getMoreButtons()) {
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[0]);
			for (Button button : paginationInfo.getMoreButtons()) {
				if (null == button || !button.showButton(objects) || null == objects)
					continue;
				String permissionCode = button.getPermissionCode();
				button = ButtonHelper.createButton(button.getTitle(), buildUrlWithObjects(button.getUrl(), objects), button.getType(), button.getShowButtonFilter());
				button.setPermissionCode(permissionCode);
				html.append(ButtonHelper.buildButtonHtml(button));
			}
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[1]);
		}
		if (rowIndex % 2 == 0)
			html.append(TEMPLATE_BODY_ROW_WRAP_0[1]);
		else
			html.append(TEMPLATE_BODY_ROW_WRAP_1[1]);
	}

	private static void buildRowDataPrefix(PaginationInfo paginationInfo, StringBuffer html, int rowIndex, Object[] objects) {
		if (rowIndex % 2 == 0)
			html.append(TEMPLATE_BODY_ROW_WRAP_0[0]);
		else
			html.append(TEMPLATE_BODY_ROW_WRAP_1[0]);

		if (paginationInfo.isBuildSelectBox()) {
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[0]);
			if (null != objects)
				html.append(TABLE_BAODY_ROW_CHECK_BOX[0]).append(objects[0].toString()).append(TABLE_BAODY_ROW_CHECK_BOX[1]);
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[1]);
		}

		if (paginationInfo.isBuildRowNumber()) {
			html.append(TEMPLATE_BODY_ROW_ITEM_WRAP[0]).append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[0]).append(rowIndex + 1).append(TEMPLATE_BODY_ROW_ITEM_NUMBER_WRAP[1]).append(TEMPLATE_BODY_ROW_ITEM_WRAP[1]);
		}
	}

	private static String buildUrlWithObjects(String url, Object[] objects) {
		int start = url.indexOf("{");
		if (start < 0)
			return url + objects[0];

		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(url.substring(0, start));
		int end = url.indexOf("}");
		int index = Integer.valueOf(url.substring(start + 1, end));
		urlBuffer.append(EncodingUtil.urlEncode(objects[index].toString()));
		urlBuffer.append(url.substring(end + 1));
		// urlBuffer.append(EncodingUtil.urlEncode(objects[0].toString()));
		String result = urlBuffer.toString();
		if (result.indexOf("{") >= 0)
			return buildUrlWithObjects(result, objects);
		return result;
	}

	public static void buildEmptyBody(PaginationInfo paginationInfo, StringBuffer html) {
		int col = paginationInfo.getTitles().length;
		if (paginationInfo.isBuildSelectBox())
			col++;
		if (paginationInfo.isBuildRowNumber())
			col++;
		if (null != paginationInfo.getMoreButtons())
			col++;
		html.append("<tr><td colspan=\"").append(col).append("\"><div class=\"mtb20 c666 tac\">").append(paginationInfo.getTextForEmpty()).append("</div></td></tr>");
	}

	private static void buildHeader(PaginationInfo paginationInfo, StringBuffer html) {
		html.append(TEMPLATE_TITLE_WRA[0]);
		if (paginationInfo.isBuildSelectBox()) {
			html.append(TEMPLATE_TITLE_ITEM_WRA[0]).append(TABLE_HEADER_CHECK_BOX).append(TEMPLATE_TITLE_ITEM_WRA[1]);
		}
		if (paginationInfo.isBuildRowNumber()) {
			html.append(TEMPLATE_TITLE_ITEM_WRA[0]).append(StringEscapeUtil.escapeHtml4("#")).append(TEMPLATE_TITLE_ITEM_WRA[1]);
		}
		String[] titles = paginationInfo.getTitles();
		String[] titleDescriptions = paginationInfo.getTitleDescriptions();
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			String titleDescription = null != titleDescriptions && titleDescriptions.length > i ? titleDescriptions[i] : null;
			html.append(TEMPLATE_TITLE_ITEM_WRA[0]);
			html.append("<span ");
			if (StringUtil.hasText(titleDescription)) {
				html.append(" title=\"").append(StringEscapeUtil.escapeHtml4(titleDescription)).append("\"");
			}
			html.append(" >");
			html.append(StringEscapeUtil.escapeHtml4(title));
			if (StringUtil.hasText(titleDescription)) {
				html.append("<span class=\"wenhaoIco ml5\"/>");
			}
			html.append("</span>");
			html.append(TEMPLATE_TITLE_ITEM_WRA[1]);
		}
		if (null != paginationInfo.getMoreButtons()) {
			html.append(TEMPLATE_TITLE_ITEM_WRA[0]).append("操作").append(TEMPLATE_TITLE_ITEM_WRA[1]);
		}
		html.append(TEMPLATE_TITLE_WRA[1]);
	}

}
