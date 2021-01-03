package common.core.site.tag.select;

import java.util.List;

public interface SelectDataService {
	public List<Object[]> getDataSouce(String dataSouceType, String dataSouceFrom);
}
