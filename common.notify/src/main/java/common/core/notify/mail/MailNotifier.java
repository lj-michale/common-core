package common.core.notify.mail;

import common.core.notify.constant.ModelResult;

public interface MailNotifier {
	ModelResult<MailNotifyResponse> notify(MailNotifyRequest mailNotifyRequest);
}
