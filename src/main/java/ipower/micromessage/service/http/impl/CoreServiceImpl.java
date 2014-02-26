package ipower.micromessage.service.http.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.resp.RespMesssageHelper;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.msg.utils.MsgUtil;
import ipower.micromessage.service.http.ICoreService;
import ipower.micromessage.service.http.IReceiveHandlerService;

/**
 * 微信核心业务实现。
 * @author yangyong.
 * @since 2014-02-24.
 * http://blog.csdn.net/lyq8479/article/details/9841371
 * */
public class CoreServiceImpl implements ICoreService {
	private static Logger logger = Logger.getLogger(CoreServiceImpl.class);
	private static final String REQ_MSG_FromUserName = "FromUserName",
								REQ_MSG_ToUserName = "ToUserName",
								REQ_MSG_MsgType = "MsgType";
	private String token;
	private IReceiveHandlerService receiveHandler;
	/**
	 * 设置微信令牌。
	 * @param token
	 * 	微信令牌。
	 * */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 设置接收消息处理服务。
	 * @param receiveHandler
	 * 	接收消息处理。
	 * */
	public void setReceiveHandler(IReceiveHandlerService receiveHandler) {
		this.receiveHandler = receiveHandler;
	}

	@Override
	public String token() {
		return this.token;
	}

	@Override
	public String processRequest(HttpServletRequest req) {
		TextRespMessage callback = null;
		try {
			Map<String, String> params = MsgUtil.parseXml(req);
			//消息类型。
			String msgType = params.get(REQ_MSG_MsgType);
			logger.info("消息类型：" + msgType);
			
			//接收消息处理。
			String out = this.receiveHandler.handler(msgType, params);
			if(out != null){
				return out;
			}
			callback = new TextRespMessage();
			//发送方openid。
			callback.setToUserName(params.get(REQ_MSG_FromUserName));
			//公众账号。
			callback.setFromUserName(params.get(REQ_MSG_ToUserName));
			callback.setContent("[" + new Date().toString() + "]正在开发测试中... 您的消息类型是：" + msgType);
		} catch (Exception e) {
			logger.error("核心业务发生异常：",e);
			callback = new TextRespMessage();
			callback.setContent("服务器发生异常,正在处理中,请稍后...");
		}finally{
			if(callback != null){
				return RespMesssageHelper.respMessageToXml(callback);
			}
		}
		return null;
	}
}