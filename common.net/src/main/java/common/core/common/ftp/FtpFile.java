package common.core.common.ftp;

import java.io.Serializable;
import java.util.Date;

public class FtpFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /** A constant indicating an FTPFile is a file. ***/
    public static final String FILE_TYPE = "file";
    /** A constant indicating an FTPFile is a directory. ***/
    public static final String DIRECTORY_TYPE = "directory";
    /** A constant indicating an FTPFile is a symbolic link. ***/
    public static final String SYMBOLIC_LINK_TYPE = "symbolicLink";
    /** A constant indicating an FTPFile is of unknown type. ***/
    public static final String UNKNOWN_TYPE = "unknown";
	
	private long size;
	private String name;
	private String path;
	private Date lastModificationTime;
	private String type;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getLastModificationTime() {
		return lastModificationTime;
	}

	public void setLastModificationTime(Date lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
