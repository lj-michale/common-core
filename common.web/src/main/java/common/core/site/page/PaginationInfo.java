package common.core.site.page;

import java.text.Format;
import java.util.List;

import common.core.app.dao.PageResult;
import common.core.site.view.button.Button;

public class PaginationInfo {

	/**
	 * id请放在对象数组中的第1个，但不对界面输出，如需输出请放在相关的输出列，如果要求第0个输出，
	 * 需设置firstRenderColumnIndex的值
	 */
	private PageResult<Object[]> pageResult;

	/**
	 * url最后为分页索引参数，如http://www.eascs.com/list?filter=1&pageIndex=
	 */
	private String url;
	private String rendTo;
	private String[] titles;
	private String[] titleDescriptions;
	private Button[] moreButtons;
	private Button[] titleButtons;
	private SelectDataOption[] selectDataOptions;
	// 是否生成分页导航信息
	private boolean buildPagingNavigation = true;
	private int firstRenderColumnIndex = 1;
	private boolean buildRowNumber = false;
	private boolean buildSelectBox = false;
	private String buildHiddenIdsName = null;
	private String textForEmpty = "暂无数据";
	private Format[] formats;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageResult<Object[]> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<Object[]> pageResult) {
		this.pageResult = pageResult;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public Button[] getMoreButtons() {
		return moreButtons;
	}

	public void setMoreButtons(Button[] moreButtons) {
		this.moreButtons = moreButtons;
	}

	public Button[] getTitleButtons() {
		return titleButtons;
	}

	public void setTitleButtons(Button[] titleButtons) {
		this.titleButtons = titleButtons;
	}

	public String getRendTo() {
		return rendTo;
	}

	public void setRendTo(String rendTo) {
		this.rendTo = rendTo;
	}

	public PaginationInfo(PageResult<Object[]> pageResult, String[] titles, String url, String rendTo) {
		super();
		this.rendTo = rendTo;
		this.url = url;
		this.pageResult = pageResult;
		this.titles = titles;
	}

	public PaginationInfo(PageResult<Object[]> pageResult, String[] titles, String url) {
		super();
		this.url = url;
		this.pageResult = pageResult;
		this.titles = titles;
	}

	public PaginationInfo(PageResult<Object[]> pageResult, String[] titles) {
		super();
		this.pageResult = pageResult;
		this.titles = titles;
	}

	public PaginationInfo(List<Object[]> listData, String[] titles) {
		super();
		this.pageResult = new PageResult<Object[]>(listData.size(), 0, listData.size());
		this.pageResult.setData(listData);
		this.titles = titles;
		this.setBuildPagingNavigation(false);
	}

	public PaginationInfo(String[] titles) {
		super();
		this.pageResult = new PageResult<Object[]>(0, 0, 0);
		this.titles = titles;
	}

	public boolean isBuildPagingNavigation() {
		return buildPagingNavigation;
	}

	public void setBuildPagingNavigation(boolean buildPagingNavigation) {
		this.buildPagingNavigation = buildPagingNavigation;
	}

	public int getFirstRenderColumnIndex() {
		return firstRenderColumnIndex;
	}

	public void setFirstRenderColumnIndex(int firstRenderColumnIndex) {
		this.firstRenderColumnIndex = firstRenderColumnIndex;
	}

	public boolean isBuildRowNumber() {
		return buildRowNumber;
	}

	public void setBuildRowNumber(boolean buildRowNumber) {
		this.buildRowNumber = buildRowNumber;
	}

	public boolean isBuildSelectBox() {
		return buildSelectBox;
	}

	public void setBuildSelectBox(boolean buildSelectBox) {
		this.buildSelectBox = buildSelectBox;
	}

	public SelectDataOption[] getSelectDataOptions() {
		return selectDataOptions;
	}

	public void setSelectDataOptions(SelectDataOption[] selectDataOptions) {
		this.selectDataOptions = selectDataOptions;
	}

	public String getTextForEmpty() {
		return textForEmpty;
	}

	public void setTextForEmpty(String textForEmpty) {
		this.textForEmpty = textForEmpty;
	}

	public Format[] getFormats() {
		return formats;
	}

	public void setFormats(Format[] formats) {
		this.formats = formats;
	}

	public String[] getTitleDescriptions() {
		return titleDescriptions;
	}

	public void setTitleDescriptions(String[] titleDescriptions) {
		this.titleDescriptions = titleDescriptions;
	}

	public String getBuildHiddenIdsName() {
		return buildHiddenIdsName;
	}

	public void setBuildHiddenIdsName(String buildHiddenIdsName) {
		this.buildHiddenIdsName = buildHiddenIdsName;
	}

}
