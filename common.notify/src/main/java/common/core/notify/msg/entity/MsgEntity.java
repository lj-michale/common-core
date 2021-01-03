package common.core.notify.msg.entity;

import java.util.List;

/**
 * 短信发送实体类
 * 
 * @author user
 *
 */
public class MsgEntity {
	private List<String> receivers;

	private String msgContent;

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
}
