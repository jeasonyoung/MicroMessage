package ipower.micromessage.service.http.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.req.BaseReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IMessageHandler;

/**
 * 默认消息处理实现。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class DefaultMessageHandlerImpl implements IMessageHandler {
	private static Logger logger = Logger.getLogger(DefaultMessageHandlerImpl.class);
	
	@Override
	public BaseRespMessage handler(MicroContext context) {
		logger.info("开始默认消息处理...");
		BaseReqMessage req = (BaseReqMessage)context.getCurrentReqMessage();
		if(req == null){
			logger.error("转换为请求消息时失败！");
		}
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		builder.append("尊敬的微信用户:"+ req.getFromUserName() + ",\r\n")
			   .append("您刚才发送了["+req.getMsgType()+"]类型消息,\r\n")
			   .append("当前时间："+ sf.format(new Date()))
			   .append("感谢您的光顾，我们正在努力的开发中，敬请期待...\r\n");
		
		if(req != null){
			builder.append("消息ID：" + req.getMsgId());
		}
		TextRespMessage respMessage  = new TextRespMessage(req);
		respMessage.setContent(builder.toString()); 
		return respMessage;
	}
}