package ipower.micromessage.service;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IMessageHandler;
/**
 * 菜单处理处理抽象类。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public abstract class MenuBaseHandler implements IMessageHandler {
	/**
	 * 远程EICP数据访问服务。
	 * */
	protected IRemoteEICPService remoteEICPService;
	/**
	 * 设置远程EICP服务接口。
	 * @param remoteEICPService
	 * 	远程EICP服务接口。
	 * */
	public void setRemoteEICPService(IRemoteEICPService remoteEICPService) {
		this.remoteEICPService = remoteEICPService;
	}
	
	@Override
	public BaseRespMessage handler(MicroContext context) {
		BaseMessage message = context.getCurrentReqMessage();
		if(message == null) return null;
		if(message.getMsgType().equalsIgnoreCase(MicroContext.REQ_MESSAGE_TYPE_TEXT)){
			return this.handler((TextReqMessage)message, context);
		}
		if(message.getMsgType().equalsIgnoreCase(MicroContext.REQ_MESSAGE_TYPE_EVENT)){
			EventMessage eventMessage = (EventMessage)message;
			if(eventMessage.getEvent().equalsIgnoreCase(MicroContext.EVENT_MESSAGE_TYPE_CLICK)){
				return this.menuClick((ClickEventMessage)eventMessage, context);
			}
		}
		return this.handlerMessage(message, context);
	}
	/**
	 * 处理文本请求消息。
	 * @param current
	 * 	当前请求消息。
	 * @param context
	 * 	上下文。
	 * @return 处理反馈。
	 * */
	protected abstract BaseRespMessage handler(TextReqMessage current, MicroContext context);
	/**
	 * 处理菜单点击请求消息。
	 * @param current
	 * 	当前请求消息。
	 * @param context
	 * 	上下文。
	 * @return  处理反馈。
	 * */
	protected abstract BaseRespMessage menuClick(ClickEventMessage current,MicroContext context);
	/**
	 * 一般事件处理。
	 * @param current
	 * 当前事件请求消息。
	 * @param context
	 * 	上下文。
	 * @return 处理反馈。
	 * */
	protected BaseRespMessage handlerEvent(EventMessage current,MicroContext context){
		TextRespMessage respMessage = new TextRespMessage(current);
		//发布空消息。
		respMessage.setContent("");
		return respMessage;
	}
	/**
	 * 一般消息处理。
	 * @param current
	 * 	当前请求消息。
	 * @param context
	 * 	上下文。
	 * @return 处理反馈。 
	 * */
	protected BaseRespMessage handlerMessage(BaseMessage current,MicroContext context){
		TextRespMessage respMessage = new TextRespMessage(current);
		//发布空消息。
		respMessage.setContent("");
		return respMessage;
	}
}