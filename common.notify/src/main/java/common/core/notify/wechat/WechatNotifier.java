package common.core.notify.wechat;

import common.core.notify.constant.ModelResult;

/**
 * @ClassName: WechatNotifier
 * @Description: 微信发送消息接口
 * @author 张庆奇
 * @date 2016年12月15日 上午10:47:19
 * @version V1.0
 */
public interface WechatNotifier {
	/**
	 * @Title: notify
	 * @Description: 企业号发送关注人消息接口
	 * @author 张庆奇
	 * @param wechatNotifyRequest
	 * @return
	 * @return ModelResult<WechatNotifyResponse>
	 * @throws
	 * @date 2016年12月15日 上午9:42:24
	 */
	ModelResult<WechatNotifyResponse>  notify(WechatNotifyRequest wechatNotifyRequest);
}
