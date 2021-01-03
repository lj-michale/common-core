package common.core.notify.msg;

import common.core.notify.msg.entity.MsgEntity;

/**
 * 短信发送
 * 
 * @author user
 *
 */
public interface MsgSender {
	void send(MsgEntity entity);
}
