package common.core.web.cache;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CacheFilePolicyBuilder {
	public CacheFilePolicy getCapturerFileItem(HttpServletRequest request, HttpServletResponse response);

	public void writeFromCapturerFilePolicy(HttpServletResponse httpServletResponse, CacheFilePolicy cacheFilePolicy) throws IOException;
}
