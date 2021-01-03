package common.core.web.api;

public class DataApiResponse<T> extends BaseApiResponse {

	@ApiOptionDoc(demoValue = "数据对象", description = "数据对象")
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DataApiResponse(T data) {
		super();
		this.data = data;
	}

	public DataApiResponse() {
		super();
	}

}
