package common.core.web.api.view;

import common.core.app.dao.PageResult;
import common.core.web.api.ApiPageResult;

public class ApiPageResultBuilder {

	public static <T> ApiPageResult<T> build(PageResult<T> pageResult) {
		ApiPageResult<T> apiPageResult = new ApiPageResult<>();
		apiPageResult.setData(pageResult.getData());
		apiPageResult.setDbRowCount(pageResult.getDbRowCount());
		apiPageResult.setPageIndex(pageResult.getPageIndex());
		apiPageResult.setPageSize(pageResult.getPageSize());
		apiPageResult.setPageTotal(pageResult.getPageTotal());
		apiPageResult.setReturnDataSize(pageResult.getReturnDataSize());
		apiPageResult.setSumData(pageResult.getSumData());
		return apiPageResult;
	}

}
