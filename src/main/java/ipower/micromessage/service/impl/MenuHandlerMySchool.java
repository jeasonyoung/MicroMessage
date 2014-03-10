package ipower.micromessage.service.impl;

import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.MenuBaseHandler;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;

/**
 * 我的校园。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerMySchool extends MenuBaseHandler {

	@Override
	protected BaseRespMessage handler(TextReqMessage current, MicroContext context) {
		return this.createContent(current, context);
	}

	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		return this.createContent(current, context);
	}
	
	private BaseRespMessage createContent(BaseMessage current,MicroContext context){
		JSONObject post = new JSONObject(),body = null;
		post.put("usersysid", context.getUserId());
		CallbackData callback = this.remoteEICPService.remotePost("MySchool", post);
		if(callback == null || (body = callback.getBody()) == null){
			return this.handlerMessage(current, context, "未设置我的校园！");
		}
		return this.handlerMessage(current, context,"<a href=\"" + body.getString("link") + "\">" + body.getString("title") + "</a>");
	}

}