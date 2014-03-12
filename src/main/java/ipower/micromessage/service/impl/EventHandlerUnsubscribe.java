package ipower.micromessage.service.impl;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IAuthenticationService;
import ipower.micromessage.service.http.IMessageHandler;
import ipower.micromessage.service.http.IMessgeContextService;

/**
 * 取消关注事件处理。
 * @author yangyong.
 * @since 2014-03-12.
 * */
public class EventHandlerUnsubscribe implements IMessageHandler {
	private static Logger logger = Logger.getLogger(EventHandlerUnsubscribe.class);
	private IAuthenticationService authenticationService;
	private IMessgeContextService contextService;
	
	/**
	 * 设置鉴权服务接口。
	 * @param authenticationService
	 * 	鉴权服务。
	 * */
	public void setAuthenticationService(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	/**
	 * 设置上下文服务接口。
	 * @param messgeContextService
	 * 上下文服务。
	 * */
	public void setContextService(IMessgeContextService contextService) {
		this.contextService = contextService;
	}
	
	@Override
	public BaseRespMessage handler(MicroContext context) {
		logger.info("取消关注事件处理...");
		EventMessage event = (EventMessage)context.getCurrentReqMessage();
		if(event == null){
			logger.error("转换为事件请求消息时失败！");
			return null;
		}
		if(this.authenticationService.remove(context)){
			this.contextService.remove(context); 
		}
		TextRespMessage resp = new TextRespMessage(event);
		resp.setContent("取消关注成功，谢谢光顾！");
		logger.info("取消关注事件处理完毕！");
		return resp;
	}

}