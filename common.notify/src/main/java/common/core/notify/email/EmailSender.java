package common.core.notify.email;

import common.core.notify.email.entity.EmailContentEntity;

/**
 * 邮件发送
 * 
 * @author user
 *
 */
public interface EmailSender {
	boolean send(EmailContentEntity entity);
}
