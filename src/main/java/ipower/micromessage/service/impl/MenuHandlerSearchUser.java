package ipower.micromessage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
		JSONObject post = new JSONObject();
		post.put("usersysid", context.getUserId());
		post.put("name",name);
		CallbackData callback = this.remoteEICPService.remotePost("SearchUser", post);
		if(callback == null || callback.getBody() == null || callback.getBody().trim().isEmpty()){
			return this.handlerMessage(current, context, "没有获取到通讯录！");
		}
		JSONArray bodys = JSON.parseArray(callback.getBody());
		int size = 0;
		if(bodys == null || (size = bodys.size()) == 0){
			return this.handlerMessage(current, context, "没有找到[" + name + "]的信息！");
		}
		StringBuilder data = new StringBuilder();
		for(int i = 0; i < size; i++){
			JSONObject obj = bodys.getJSONObject(i);
			if(obj != null){
				if(i > 0) data.append("\r\n\r\n");
				 data.append(obj.getString("title"));				 
			}
		}
		return this.handlerMessage(current, context, data.toString());
	}

	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		return this.handlerMessage(current, context,this.toolTip);
	}	 
}