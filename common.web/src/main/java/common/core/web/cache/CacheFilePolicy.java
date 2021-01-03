package common.core.web.cache;

import java.io.File;

public class CacheFilePolicy {

	private File file;

	private boolean canCapture;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isCanCapture() {
		return canCapture;
	}

	public void setCanCapture(boolean canCapture) {
		this.canCapture = canCapture;
	}

}
