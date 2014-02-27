package ipower.micromessage.service.http.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.events.EventMessageHelper;
import ipower.micromessage.msg.events.LocationEventMessage;
import ipower.micromessage.msg.events.ScanEventMessage;
import ipower.micromessage.msg.events.SubscribeEventMessage;
import ipower.micromessage.msg.req.ImageReqMessage;
import ipower.micromessage.msg.req.LinkReqMessage;
import ipower.micromessage.msg.req.LocationReqMessage;
import ipower.micromessage.msg.req.ReqMessageHelper;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.req.VideoReqMessage;
import ipower.micromessage.msg.req.VoiceReqMessage;
import ipower.micromessage.service.http.IReceiveHandlerService;

/**
 * 接收请求消息处理服务实现类。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public class ReceiveHandlerServiceImpl implements IReceiveHandlerService {
	private static Logger logger = Logger.getLogger(ReceiveHandlerServiceImpl.class);
	
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
		  	case ReqMessageHelper.REQ_MESSAGE_TYPE_EVENT:{
		  		logger.info("解析为事件消息.");
		  		return this.eventHandler(body);
		  	}
			//文本消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_TEXT:{
				TextReqMessage reqMsg = ReqMessageHelper.parseTextReqMessage(body);
				logger.info("解析为文本消息:" + reqMsg.getClass().getName());
				///TODO:
			}
			break;
			//图片消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_IMAGE:{
				ImageReqMessage reqMsg = ReqMessageHelper.parseImageReqMessage(body);
				logger.info("解析为图片消息:" + reqMsg.getClass().getName());
				///TODO:
			}
			break;
			//语音消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_VOICE:{
				VoiceReqMessage reqMsg = ReqMessageHelper.parseVoiceReqMessage(body);
				logger.info("解析为语音消息:" + reqMsg.getClass().getName());
			}
			break;
			//视频消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_VIDEO:{
				VideoReqMessage reqMsg = ReqMessageHelper.parseVideoReqMessage(body);
				logger.info("解析为视频消息:" + reqMsg.getClass().getName());
				///TODO:
			}
			break;
			//地理位置消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_LOCATION:{
				LocationReqMessage reqMsg = ReqMessageHelper.parseLocationReqMessage(body);
				logger.info("解析为地理位置消息:" + reqMsg.getClass().getName());
				///TODO:
			}
			break;
			//链接消息
			case ReqMessageHelper.REQ_MESSAGE_TYPE_LINK:{
				LinkReqMessage reqMsg = ReqMessageHelper.parseLinkReqMessage(body);
				logger.info("解析为链接消息:" + reqMsg.getClass().getName());
				///TODO:
			}
			break;
			//不能识别的消息
			default:{
				logger.error("消息类型：[" + msgType + "]未能被识别解析！");
				return null;
			}
		}
		return null;
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
			//首次关注事件
			case EventMessageHelper.EVENT_MESSAGE_TYPE_SUBSCRIBE:{
				SubscribeEventMessage reqEvent = EventMessageHelper.parseSubscribeEventMessage(body);
				logger.info("解析为首次关注事件:" + reqEvent.getClass().getName());
				///TODO:
			}
			break;
			//取消关注事件
			case EventMessageHelper.EVENT_MESSAGE_TYPE_UNSUBSCRIBE:{
				EventMessage reqEvent = EventMessageHelper.parseEventMessage(body);
				logger.info("解析为取消关注事件:" + reqEvent.getClass().getName());
				///TODO:
			}
			break;
			//扫描事件
			case EventMessageHelper.EVENT_MESSAGE_TYPE_SCAN:{
				ScanEventMessage reqEvent = EventMessageHelper.parseScanEventMessage(body);
				logger.info("解析为扫描事件:" + reqEvent.getClass().getName());
				///TODO:
			}
			break;
			//上报地理位置事件
			case EventMessageHelper.EVENT_MESSAGE_TYPE_LOCATION:{
				LocationEventMessage reqEvent = EventMessageHelper.parseLocationEventMessage(body);
				logger.info("解析为上报地理位置事件:" + reqEvent.getClass().getName());
				///TODO:
			}
			break;
			//自定义菜单点击事件
			case EventMessageHelper.EVENT_MESSAGE_TYPE_CLICK:{
				ClickEventMessage reqEvent = EventMessageHelper.parseClickEventMessage(body);
				logger.info("解析为自定义菜单点击事件:" + reqEvent.getClass().getName());
				///TODO:
			}
			break;
			//不能识别的消息
			default:{
				logger.error("事件消息类型：[" + event + "]未能被识别解析！");
				return null;
			}
		}
		return null;
	}
}