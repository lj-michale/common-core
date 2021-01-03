package common.core.notify.sms;

import common.core.notify.constant.ModelResult;

public interface SmsNotifier {
	ModelResult<SmsNotifyResponse>  notify(SmsNotifyRequest smsNotifyRequest);
}
