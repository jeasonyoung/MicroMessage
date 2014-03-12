package ipower.micromessage.service.impl;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.MenuBaseHandler;
import ipower.micromessage.service.http.IAuthenticationService;
import ipower.micromessage.service.http.IMessgeContextService;

/**
 * 注销账号。
 * @author yangyong.
 * @since 2014-03-12.
 * */
public class MenuHandlerCancelAccount extends MenuBaseHandler {
	private IAuthenticationService authenticationService;
	private IMessgeContextService contextService;
	private String logoutTooltip;
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
	/**
	 * 设置注销账号提示信息。
	 * @param logoutTooltip
	 * 	注销账号提示信息。
	 * */
	public void setLogoutTooltip(String logoutTooltip) {
		this.logoutTooltip = logoutTooltip;
	}
	@Override
	protected BaseRespMessage handler(TextReqMessage current, MicroContext context) {
		return this.handlerMessage(current, context);
	}

	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		boolean result = false;
		if(this.authenticationService.remove(context)){
			this.contextService.remove(context);
			result = true;
		}
		return this.handlerMessage(current, context, String.format(this.logoutTooltip, (result ? "成功" : "失败")));
	}
}