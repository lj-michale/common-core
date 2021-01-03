package common.core.app.cache;

public class CacheSpec {
	private String name;
	private long liveSeconds;
	private int maxEntriesLocalHeap = 0;
	private boolean updateWithVersion = true;

	public boolean isUpdateWithVersion() {
		return updateWithVersion;
	}

	public void setUpdateWithVersion(boolean updateWithVersion) {
		this.updateWithVersion = updateWithVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLiveSeconds() {
		return liveSeconds;
	}

	public void setLiveSeconds(long liveSeconds) {
		this.liveSeconds = liveSeconds;
	}

	public int getMaxEntriesLocalHeap() {
		return maxEntriesLocalHeap;
	}

	public void setMaxEntriesLocalHeap(int maxEntriesLocalHeap) {
		this.maxEntriesLocalHeap = maxEntriesLocalHeap;
	}

	public CacheSpec(String name, long liveSeconds, int maxEntriesLocalHeap) {
		super();
		this.name = name;
		this.liveSeconds = liveSeconds;
		this.maxEntriesLocalHeap = maxEntriesLocalHeap;
	}

	public CacheSpec(String name, long liveSeconds, boolean updateWithVersion) {
		super();
		this.name = name;
		this.liveSeconds = liveSeconds;
		this.updateWithVersion = updateWithVersion;
	}

	public CacheSpec(String name, long liveSeconds) {
		super();
		this.name = name;
		this.liveSeconds = liveSeconds;
	}

}
