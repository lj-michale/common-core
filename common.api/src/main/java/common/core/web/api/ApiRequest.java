package common.core.web.api;

import javax.validation.constraints.NotNull;

public class ApiRequest {

	/**
	 * 请求系统ID
	 */
	@NotNull(message = "请求系统ID不能为空")
	private String appId;

	/**
	 * 请求方法
	 */
	@NotNull(message = "请求方法不能为空")
	private String method;
	
	/**
	 * 客户端相关信息
	 */
	private String userAgent;

	/**
	 * 接口版本
	 */
	private String version;

	/**
	 * 数据格式
	 */
	private String format;

	/**
	 * 字符集
	 */
	private String charset;

	/**
	 * 签名类型
	 */
	private String signType;

	/**
	 * 加密类型
	 */
	private String encryptionType;

	/**
	 * 请求签名串，将请求参数名和参数值用“=”连接，并将非空值和非sign的所有参数值项目按字母排序以“&”连接的字符串后，按指定签名类型签名的字符串值
	 */
	private String sign;

	/**
	 * 请求时间戳
	 */
	@NotNull(message = "请求时间戳不能为空")
	private Long timestamp;

	/**
	 * 请求唯一ID,每次请求生成唯一ID，一个ID只能用一次
	 */
	@NotNull(message = "请求唯一ID不能为空")
	private String uuid;
	
	
	/**
	 * app的设备唯一码
	 */
	private String udid;

	/**
	 * token串
	 */
	private String token;
	
	/**
	 * 请求业务数据
	 */
	private String data;
	/**
	 * 登录IP
	 *
	 */
	private String ip;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}

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

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "ApiRequest [appId=" + appId + ", method=" + method + ", userAgent=" + userAgent + ", version=" + version + ", format=" + format + ", charset=" + charset + ", signType=" + signType + ", encryptionType=" + encryptionType + ", sign=" + sign + ", timestamp=" + timestamp + ", uuid="
				+ uuid + ", udid=" + udid + ", token=" + token + ", data=" + data + ", ip=" + ip + "]";
	}

}
