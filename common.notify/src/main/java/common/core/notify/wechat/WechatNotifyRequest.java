package common.core.notify.wechat;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName: WechatNotifyRequest
 * @Description: 微信发送消息需要传递的参数
 * @author 张庆奇
 * @date 2016年12月15日 上午10:49:12
 * @version V1.0
 */
public class WechatNotifyRequest implements Serializable {
	/** 属性名称 */
	private static final long serialVersionUID = 1599571444552151444L;
	/**
	 * 请求流水号（唯一）
	 */
	@NotEmpty
	private String requestId;
	//公司企业号ID
	@NotEmpty
	private String corpId;
	//管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	@NotEmpty
	public  String secret;
	// text|image|voice|video|file|news
	@NotEmpty
	private String msgType;
	//企业应用的id，整型。可在应用的设置页面查看
	private int agentid;
	// 成员ID列表（消息接收者:通讯录上的账号，比如SZ8183，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all， 则向关注该企业应用的全部成员发送
	private String touser;
	// 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	private String toparty;
	// 标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
	private String totag;
	// msgType=text时 ,文本消息内容
	private String content;
	// msgType=image|voice|video时 ,对应消息信息ID（--------）
	private String mediaId;
	// msgType=news|video时，消息标题
	private String title;
	// msgType=news|video时，消息描述
	private String description;
	// msgType=news时，消息链接
	private String url;
	// msgType=news时，图片路径
	private String picurl;
	// 表示是否是保密消息，0表示否，1表示是，默认0
	private String safe;
	

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}
	
	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

}
