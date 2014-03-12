package ipower.micromessage.service.impl;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IMessageHandler;

/**
 * 关注事件处理。
 * @author yangyong.
 * @since 2014-03-12.
 * */
public class EventHandlerSubscribe implements IMessageHandler {

	@Override
	public BaseRespMessage handler(MicroContext context) {
		TextRespMessage resp = new TextRespMessage(context.getCurrentReqMessage());
		resp.setContent("欢迎关注！");
		return resp;
	}
}