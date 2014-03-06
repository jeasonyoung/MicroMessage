package ipower.micromessage.service.http.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.events.EventMessageHelper;
import ipower.micromessage.msg.events.LocationEventMessage;
import ipower.micromessage.msg.events.ScanEventMessage;
import ipower.micromessage.msg.events.SubscribeEventMessage;
import ipower.micromessage.msg.req.BaseReqMessage;
import ipower.micromessage.msg.req.ImageReqMessage;
import ipower.micromessage.msg.req.LinkReqMessage;
import ipower.micromessage.msg.req.LocationReqMessage;
import ipower.micromessage.msg.req.ReqMessageHelper;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.req.VideoReqMessage;
import ipower.micromessage.msg.req.VoiceReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.RespMesssageHelper;
import ipower.micromessage.service.http.IMessageHandler;
import ipower.micromessage.service.http.IReceiveHandlerService;

/**
 * 接收请求消息处理服务实现类。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public class ReceiveHandlerServiceImpl implements IReceiveHandlerService {
	private static Logger logger = Logger.getLogger(ReceiveHandlerServiceImpl.class);
	private Map<String, IMessageHandler> handlers,eventHandlers;
	/**
	 * 设置消息处理集合。
	 * @param handlers
	 * 	消息处理。
	 * */
	public void setHandlers(Map<String, IMessageHandler> handlers) {
		this.handlers = handlers;
	}
	/**
	 * 设置事件消息处理集合。
	 * @param eventHandlers
	 * 	事件消息处理集合。
	 * */
	public void setEventHandlers(Map<String, IMessageHandler> eventHandlers) {
		this.eventHandlers = eventHandlers;
	}

	@Override
	public String handler(String msgType, Map<String, String> body) {
		logger.info("开始消息解析...");
		if(msgType == null){
			logger.error("参数异常：msgType 值为null !");
			return null;
		}
		//消息解析
		switch(msgType){
			//事件消息
		  	case MicroContext.REQ_MESSAGE_TYPE_EVENT:{
		  		logger.info("解析为事件消息.");
		  		return this.eventHandler(body);
		  	}
			//文本消息
			case MicroContext.REQ_MESSAGE_TYPE_TEXT:{
				TextReqMessage reqMsg = ReqMessageHelper.parseTextReqMessage(body);
				logger.info("解析为文本消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//图片消息
			case MicroContext.REQ_MESSAGE_TYPE_IMAGE:{
				ImageReqMessage reqMsg = ReqMessageHelper.parseImageReqMessage(body);
				logger.info("解析为图片消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//语音消息
			case MicroContext.REQ_MESSAGE_TYPE_VOICE:{
				VoiceReqMessage reqMsg = ReqMessageHelper.parseVoiceReqMessage(body);
				logger.info("解析为语音消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//视频消息
			case MicroContext.REQ_MESSAGE_TYPE_VIDEO:{
				VideoReqMessage reqMsg = ReqMessageHelper.parseVideoReqMessage(body);
				logger.info("解析为视频消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//地理位置消息
			case MicroContext.REQ_MESSAGE_TYPE_LOCATION:{
				LocationReqMessage reqMsg = ReqMessageHelper.parseLocationReqMessage(body);
				logger.info("解析为地理位置消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//链接消息
			case MicroContext.REQ_MESSAGE_TYPE_LINK:{
				LinkReqMessage reqMsg = ReqMessageHelper.parseLinkReqMessage(body);
				logger.info("解析为链接消息:" + reqMsg.getClass().getName());
				return this.handlersFactory(msgType, reqMsg);
			}
			//不能识别的消息
			default:{
				logger.error("消息类型：[" + msgType + "]未能被识别解析！");
				return null;
			}
		}
	}
	/**
	 * 事件消息处理。
	 * @param body
	 * 	消息数据。
	 * @return
	 * 	处理结果。
	 * */
	protected synchronized String eventHandler(Map<String, String> body){
		logger.info("开始事件消息解析...");
		String event = body.get("Event");
		if(event == null){
			logger.error("参数异常：event 值为null !");
			return null;
		}
		//事件解析
		switch(event){
			//关注事件
			case MicroContext.EVENT_MESSAGE_TYPE_SUBSCRIBE:{
				SubscribeEventMessage reqEvent = EventMessageHelper.parseSubscribeEventMessage(body);
				logger.info("解析为关注事件:" + reqEvent.getClass().getName());
				return this.eventHandlersFactory(event, reqEvent);
			}
			//取消关注事件
			case MicroContext.EVENT_MESSAGE_TYPE_UNSUBSCRIBE:{
				EventMessage reqEvent = EventMessageHelper.parseEventMessage(body);
				logger.info("解析为取消关注事件:" + reqEvent.getClass().getName());
				return this.eventHandlersFactory(event, reqEvent);
			}
			//扫描事件
			case MicroContext.EVENT_MESSAGE_TYPE_SCAN:{
				ScanEventMessage reqEvent = EventMessageHelper.parseScanEventMessage(body);
				logger.info("解析为扫描事件:" + reqEvent.getClass().getName());
				return this.eventHandlersFactory(event, reqEvent);
			}
			//上报地理位置事件
			case MicroContext.EVENT_MESSAGE_TYPE_LOCATION:{
				LocationEventMessage reqEvent = EventMessageHelper.parseLocationEventMessage(body);
				logger.info("解析为上报地理位置事件:" + reqEvent.getClass().getName());
				return this.eventHandlersFactory(event, reqEvent);
			}
			//自定义菜单点击事件
			case MicroContext.EVENT_MESSAGE_TYPE_CLICK:{
				ClickEventMessage reqEvent = EventMessageHelper.parseClickEventMessage(body);
				logger.info("解析为自定义菜单点击事件:" + reqEvent.getClass().getName());
				return this.eventHandlersFactory(event, reqEvent);
			}
			//不能识别的消息
			default:{
				logger.error("事件消息类型：[" + event + "]未能被识别解析！");
				return null;
			}
		}
	}
	/**
	 * 消息处理工厂。
	 * @param map
	 * 	处理集合。
	 * @param msg
	 * 	消息对象。
	 * @return
	 * 	处理结果。
	 * */
	private String handlersFactory(Map<String, IMessageHandler> map,String type, BaseMessage msg){
		IMessageHandler msgHandler = map.get(type);
		if(msgHandler == null){
			logger.error("未配置消息处理：type:"+ type);
			return null;
		}
		BaseRespMessage resp = msgHandler.handler(msg);
		if(resp == null){
			logger.info("反馈为null[type:"+ type + ","+ msgHandler.getClass().getName() +"]");
			return null;
		}
		return RespMesssageHelper.respMessageToXml(resp);
	}
	/**
	 * 消息处理工厂。
	 * @param msg
	 * 	消息对象。
	 * @return
	 * 	处理结果。
	 * */
	protected String handlersFactory(String type, BaseReqMessage msg){
		if(this.handlers == null){
			logger.error("未配置[handlers]消息处理集合！");
			return null;
		}
		return this.handlersFactory(this.handlers, type, msg);
	}
	/**
	 * 事件消息处理工厂。
	 * @param msg
	 * 	消息对象。
	 * @return
	 * 	处理结果。
	 * */
	protected String eventHandlersFactory(String event,EventMessage msg){
		if(this.eventHandlers == null){
			logger.error("未配置[eventHandlers]事件消息处理集合！");
			return null;
		}
		return this.handlersFactory(this.eventHandlers, event, msg);
	}
}