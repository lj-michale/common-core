package common.core.site.cookie;

import org.springframework.util.StringUtils;

public class CookieSpec {

	public static final int AGE_SESSION = -1;
	public static final int AGE_REMOVE = 0;
	public static final int AGE_MAX = 99999999;

	private String name;
	private String path = "/";
	private boolean httpOnly = true;
	private boolean secure = false;
	private int maxAge = -1;
	private String domain = null;
	private int version = 0;

	public CookieSpec(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public CookieSpec name(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public CookieSpec path(String path) {
		this.path = path;
		return this;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public CookieSpec httpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return this;
	}

	public boolean isSecure() {
		return secure;
	}

	public CookieSpec secure(boolean secure) {
		this.secure = secure;
		return this;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public CookieSpec maxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	public static CookieSpec build(String name) {
		return new CookieSpec(name);
	}

	public String getDomain() {
		return domain;
	}

	public CookieSpec domain(String domain) {
		if (StringUtils.hasText(domain) && domain.indexOf(".zss.cn") >= 0) {
			throw new RuntimeException("please change zss.cn to eascs.com");
		}
		this.domain = domain;
		return this;
	}

	public int getVersion() {
		return version;
	}

	public CookieSpec version(int version) {
		this.version = version;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + (httpOnly ? 1231 : 1237);
		result = prime * result + maxAge;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (secure ? 1231 : 1237);
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CookieSpec other = (CookieSpec) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (httpOnly != other.httpOnly)
			return false;
		if (maxAge != other.maxAge)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (secure != other.secure)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public void setValue(String value) {
		CookieContext.setCookieValue(this, value);
	}

	public String getValue() {
		return CookieContext.getCookieValue(this);
	}

	public void remove() {
		CookieContext.deleteCookie(this);
	}

}
