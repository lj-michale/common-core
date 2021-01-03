package common.core.web.api;

public class BaseApiResponse {
	@ApiOptionDoc(demoValue = "success", description = "响应代码")
	private String code = "success";
	@ApiOptionDoc(demoValue = "操作成功", description = "响应信息")
	private String message = "操作成功";
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BaseApiResponse() {
		super();
	}

	public BaseApiResponse(String code) {
		super();
		this.code = code;
	}

	public BaseApiResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
