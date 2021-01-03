package common.core.site.page;

public class SelectDataOption {
	private String dataSouceType;
	private String dataSouceFrom;

	public String getDataSouceType() {
		return dataSouceType;
	}

	public void setDataSouceType(String dataSouceType) {
		this.dataSouceType = dataSouceType;
	}

	public String getDataSouceFrom() {
		return dataSouceFrom;
	}

	public void setDataSouceFrom(String dataSouceFrom) {
		this.dataSouceFrom = dataSouceFrom;
	}

	public SelectDataOption(String dataSouceType, String dataSouceFrom) {
		super();
		this.dataSouceType = dataSouceType;
		this.dataSouceFrom = dataSouceFrom;
	}

	public SelectDataOption(String dataSouceType) {
		super();
		this.dataSouceType = dataSouceType;
	}

	@Override
	public String toString() {
		return "SelectDataOption [dataSouceType=" + dataSouceType + ", dataSouceFrom=" + dataSouceFrom + "]";
	}

}
