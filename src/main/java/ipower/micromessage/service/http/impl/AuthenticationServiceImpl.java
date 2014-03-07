package ipower.micromessage.service.http.impl;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IAuthenticationService;
import ipower.micromessage.service.http.IUserAuthentication;

/**
 * 鉴权服务实现类。
 * @author yangyong.
 * @since 2014-03-07.
 * */
public class AuthenticationServiceImpl implements IAuthenticationService {
	private IUserAuthentication userAuthentication;
	private String loginWelcome;
	/**
	 * 设置登录欢迎信息。
	 * @param loginWelcome
	 * 	登录欢迎信息。
	 * */
	public void setLoginWelcome(String loginWelcome) {
		this.loginWelcome = loginWelcome;
	}
	/**
	 * 设置用户鉴权接口。
	 * @param userAuthentication
	 * 	用户鉴权接口。
	 * */
	public void setUserAuthentication(IUserAuthentication userAuthentication){
		this.userAuthentication = userAuthentication;
	}
	/**
	 * 鉴权。
	 * @param context
	 * 	消息上下文。
	 * @return 
	 * 	回复为null,则鉴权成功，否则回复登录提示。
	 * */
	@Override
	public BaseRespMessage authen(MicroContext context) {
		String userId = this.userAuthentication.loadUserId(context.getOpenId());
		//未关联出用户，需提示身份验证。
		if(userId == null || userId.trim().isEmpty()){
			BaseMessage req = context.getCurrentReqMessage();
			TextRespMessage resp =  null;
			//第一次回复或者请求信息非文本。
			if(context.getRespMessageList() == null || context.getRespMessageList().size() == 0 || 
					!req.getMsgType().equalsIgnoreCase(MicroContext.REQ_MESSAGE_TYPE_TEXT)){
				resp = new TextRespMessage(req);
				resp.setContent(this.loginWelcome);
				return resp;
			}
			//文本。
			TextReqMessage reqMessage = (TextReqMessage)req;
			String content = reqMessage.getContent();
			if(content == null || content.trim().isEmpty()){
				resp = new TextRespMessage(req);
				resp.setContent(this.loginWelcome);
				return resp;
			}
			int index = content.indexOf(",");
			if(index < 1){
				index = content.indexOf("，");
			}
			if(index < 1){
				resp = new TextRespMessage(req);
				resp.setContent("您输入的格式不正确！\r\n\r\n" + this.loginWelcome);
				return resp;
			}
			///TODO:验证账号密码。
			
		}
		context.setUserId(userId);
		return null;
	}

}