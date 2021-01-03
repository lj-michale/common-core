package common.core.app.cache;

import java.util.Date;

public interface CacheGroup {
	String getName();

	Date getLastRefreshed();

	int getRevision();
}
