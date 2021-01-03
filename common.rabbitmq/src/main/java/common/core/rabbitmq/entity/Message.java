package common.core.rabbitmq.entity;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 216432652698421717L;

	private String msgType;

	private String msgBody;
	
	public Message() {
		super();
	}

	public Message(String msgType, String msgBody) {
		super();
		this.msgType = msgType;
		this.msgBody = msgBody;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	@Override
	public String toString() {
		return "Message [msgType=" + msgType + ", msgBody=" + msgBody + "]";
	}
	
	
	
}
