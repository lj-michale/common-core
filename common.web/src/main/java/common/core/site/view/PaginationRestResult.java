package common.core.site.view;

import common.core.site.page.PaginationHelper;
import common.core.site.page.PaginationInfo;

public class PaginationRestResult extends BaseRestResult<String> {

	private static final long serialVersionUID = 9059358065500476062L;

	public PaginationRestResult(String data) {
		super(data);
	}
	public static PaginationRestResult buildPaginationRestResult(PaginationInfo paginationInfo) {
		PaginationRestResult paginationRestResult = new PaginationRestResult(PaginationHelper.build(paginationInfo));
		paginationRestResult.setAction(BaseRestResult.ACTION_HTML);
		return paginationRestResult;
	}

}
