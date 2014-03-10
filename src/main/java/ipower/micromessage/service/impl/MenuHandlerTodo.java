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
 * 待办待阅处理。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerTodo extends MenuBaseHandler {

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
		CallbackData callback = this.remoteEICPService.remotePost("Todo", post);
		if(callback == null || (body = callback.getBody()) == null){
			return this.handlerMessage(current, context, "没有待办待阅事项！");
		}
		return this.handlerMessage(current, context,body.getString("title"));
	}
}