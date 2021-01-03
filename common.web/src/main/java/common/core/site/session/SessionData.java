package common.core.site.session;

import java.util.HashMap;

public class SessionData extends HashMap<String, java.io.Serializable> {
	private static final long serialVersionUID = -3199370760364609333L;
	private String id;
	private String ip;
	private long creationTime;
	private int maxInactiveInterval;
	
	public  SessionData(){
		super();
	}

	public SessionData(String id, String ip) {
		super();
		this.id = id;
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

}
