package ipower.micromessage.service.impl;

import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.MenuBaseHandler;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;

/**
 * 通讯录。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerSearchUser extends MenuBaseHandler {
	private String toolTip;
	/**
	 * 设置通信录提示信息。
	 * @param toolTip
	 * 	通信录提示信息。
	 * */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	@Override
	protected BaseRespMessage handler(TextReqMessage current, MicroContext context) {
		String name = current.getContent();
		if(name == null || name.trim().isEmpty()){
			return this.handlerMessage(current, context,this.toolTip); 
		}
		JSONObject post = new JSONObject(),body = null;
		post.put("usersysid", context.getUserId());
		post.put("name",name);
		CallbackData callback = this.remoteEICPService.remotePost("SearchUser", post);
		if(callback == null || (body = callback.getBody()) == null){
			return this.handlerMessage(current, context, "暂时没有通讯录！");
		}
		return this.handlerMessage(current, context, body.getString("resultmsg"));
	}

	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		return this.handlerMessage(current, context,this.toolTip);
	}
	
	 
}