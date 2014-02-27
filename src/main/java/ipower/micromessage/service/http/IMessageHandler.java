package ipower.micromessage.service.http;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;

/**
 * 消息处理接口
 * */
public interface IMessageHandler {
	/**
	 * 消息处理。
	 * @param msg
	 * 	消息对象。
	 * @return
	 * 	处理反馈对象。
	 * */
	BaseRespMessage handler(BaseMessage msg);
}