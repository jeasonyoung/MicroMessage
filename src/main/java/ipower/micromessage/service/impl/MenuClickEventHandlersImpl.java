package ipower.micromessage.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.IMenuClickEventHandlers;
import ipower.micromessage.service.http.IMessageHandler;

/**
 * 菜单点击事件处理实现。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuClickEventHandlersImpl implements IMenuClickEventHandlers {
	private static Logger logger = Logger.getLogger(MenuClickEventHandlersImpl.class);
	private Map<String, IMessageHandler> handlers;
	/**
	 * 设置菜单事件处理集合。
	 * @param handlers
	 * 	菜单事件处理集合。
	 * */
	public void setHandlers(Map<String, IMessageHandler> handlers) {
		this.handlers = handlers;
	}
	/**
	 * 按菜单编码加载处理。
	 * @param key
	 * 	菜单编码。
	 * */
	protected synchronized IMessageHandler loadHandler(String key){
		if(key == null || key.trim().isEmpty()) return null;
		if(this.handlers == null || this.handlers.size() == 0) return null;
		return this.handlers.get(key);
	}

	@Override
	public BaseRespMessage handler(MicroContext context) {
		BaseRespMessage resp = null;
		try{
			String menuKey = context.getLastMenuKey();
			BaseMessage req = context.getCurrentReqMessage();
			if(req != null && req.getMsgType().equalsIgnoreCase(MicroContext.REQ_MESSAGE_TYPE_EVENT)){
				EventMessage eventMessage = (EventMessage)req;
				if(eventMessage != null && eventMessage.getEvent().equalsIgnoreCase(MicroContext.EVENT_MESSAGE_TYPE_CLICK)){
					ClickEventMessage click = (ClickEventMessage)context.getCurrentReqMessage();
					if(click != null) menuKey = click.getEventKey();
				}
			}
			
			if(menuKey == null || menuKey.trim().isEmpty())
				return resp;
			
			IMessageHandler handler = this.loadHandler(menuKey);
			if(handler == null){
				logger.error("未配置菜单ID["+ menuKey +"]的处理对象！");
				return resp;
			}
			resp = handler.handler(context);
		}catch(Exception e){
			logger.error("菜单处理异常：" + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}