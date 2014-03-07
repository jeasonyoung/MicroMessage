package ipower.micromessage.service.http;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.BaseRespMessage;
/**
 * 消息处理接口
 * */
public interface IMessageHandler {
	/**
	 * 消息处理。
	 * @param context
	 * 	消息上下文对象。
	 * @return
	 * 	处理反馈对象。
	 * */
	BaseRespMessage handler(MicroContext context);
}