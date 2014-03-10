package ipower.micromessage.service.http.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.IMenuClickEventHandlers;
import ipower.micromessage.service.http.IMessageHandler;

/**
 * 默认事件消息处理实现。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class DefaultEventMessageHandlerImpl implements IMessageHandler {
	private static Logger logger = Logger.getLogger(DefaultEventMessageHandlerImpl.class);
	private IMenuClickEventHandlers menuHandlers;
	/**
	 * 设置菜单处理集合。
	 * @param menuHandlers
	 * 	菜单处理集合。
	 * */
	public void setMenuHandlers(IMenuClickEventHandlers menuHandlers) {
		this.menuHandlers = menuHandlers;
	}
	
	@Override
	public BaseRespMessage handler(MicroContext context) {
		//菜单处理。
		if(this.menuHandlers != null && context.getLastMenuKey() != null && !context.getLastMenuKey().trim().isEmpty()){
			logger.info("事件跳转到给菜单[" + context.getLastMenuKey() + "]处理...");
			return this.menuHandlers.handler(context);
		}
		logger.info("开始默认事件消息处理...");
		EventMessage event = (EventMessage)context.getCurrentReqMessage();
		if(event == null){
			logger.error("转换为事件请求消息时失败！");
		}
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		builder.append("尊敬的微信用户:"+ event.getFromUserName() + ",\r\n")
			   .append("您刚才发送了["+event.getMsgType()+"]类型事件消息,\r\n")
			   .append("当前时间："+ sf.format(new Date()))
			   .append("感谢您的光顾，我们正在努力的开发中，敬请期待...\r\n");
		
		if(event != null){
			builder.append("事件类型：" + event.getEvent());
		}
		TextRespMessage respMessage  = new TextRespMessage(event);
		respMessage.setContent(builder.toString()); 
		return respMessage;
	}
}