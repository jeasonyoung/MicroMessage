package ipower.micromessage.service.http.impl;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.msg.resp.TextRespMessage;
import ipower.micromessage.service.http.IAuthenticationService;
import ipower.micromessage.service.http.IUserAuthentication;
import ipower.micromessage.service.http.IUserAuthentication.VerifyCallback;

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
		if (userId != null && !userId.trim().isEmpty()) {
			context.setUserId(userId);
			return null;
		}
		//未关联出用户，需提示身份验证。
		BaseMessage req = context.getCurrentReqMessage();
		TextRespMessage resp = new TextRespMessage(req);
		//第一次回复或者请求信息非文本。
		if(!req.getMsgType().equalsIgnoreCase(MicroContext.REQ_MESSAGE_TYPE_TEXT) || 
				context.getRespMessageList() == null || context.getRespMessageList().size() == 0){
			resp.setContent(this.loginWelcome);
			return resp;
		}
		//文本。
		TextReqMessage reqMessage = (TextReqMessage)req;
		String content = reqMessage.getContent();
		if(content == null || content.trim().isEmpty()){
			resp.setContent(this.loginWelcome);
			return resp;
		}
		int index = content.indexOf(",");
		if(index < 1){
			index = content.indexOf("，");
		}
		if(index < 1){
			resp.setContent("您输入的格式不正确！\r\n\r\n" + this.loginWelcome);
			return resp;
		}
		String userName = content.substring(0, index),
			   password = content.substring(index + 1);
		VerifyCallback callback = this.userAuthentication.verification(reqMessage.getFromUserName(), userName, password);
		if(callback.isSuccess()){
			context.setUserId(callback.getUserId());
		}
		if(callback.getMessage() == null || callback.getMessage().trim().isEmpty()){
			resp.setContent("验证用户[" + userName + "]：" + (callback.isSuccess() ? "成功" : "失败"));
		}else{
			resp.setContent(callback.getMessage());
		}
		return resp;
	}
	/**
	 * 移除鉴权持久。
	 * @param context
	 * 	消息上下文。
	 * */
	@Override
	public boolean remove(MicroContext context) {
		if(context != null){
			return this.userAuthentication.remove(context.getUserId());
		}
		return false;
	}

}