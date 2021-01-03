package common.core.web.api;

public class ApiResponse {

	/**
	 * 响应签名串，将响应参数名和参数值用“=”连接，并将非空值和非sign的所有参数值项目按字母排序以“&”连接的字符串后，按指定签名类型签名的字符串值
	 */
	private String sign;

	/**
	 * 响应时间戳
	 */
	private Long timestamp;

	/**
	 * 响应唯一ID,每次响应生成唯一ID
	 */
	private String uuid;

	/**
	 * 响应业务数据
	 */
	private String data;

	/**
	 * 响应代码
	 */
	private String code;

	/**
	 * 响应消息
	 */
	private String message;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

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

	@Override
	public String toString() {
		return "ApiResponse [sign=" + sign + ", timestamp=" + timestamp + ", uuid=" + uuid + ", data=" + data + ", code=" + code + ", message=" + message + "]";
	}


}
