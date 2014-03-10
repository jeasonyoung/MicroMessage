package ipower.micromessage.service.impl;

import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.utils.HttpUtil;
import ipower.micromessage.service.IRemoteEICPService;

/**
 * 远程EICP数据服务接口实现。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class RemoteEICPServiceImpl implements IRemoteEICPService {
	private String url;
	/**
	 * 设置EICP数据交互URL。
	 * @param url
	 * 	EICP数据交互URL。
	 * */
	@Override
	public void setUrl(String url) {
		 this.url = url;
	}
	
	@Override
	public synchronized CallbackData remotePost(String ident, JSONObject post) {
		CallbackData callback = new CallbackData();
		
		JSONObject head = new JSONObject();
		head.put("HEAD", ident);
		head.put("BODY", post);
		
		JSONObject result = HttpUtil.httpRequest(this.url,"POST",head.toJSONString());
		
		if(result != null){
			callback.setUserId(result.getString("HEAD"));
			callback.setBody(result.getJSONObject("BODY"));
		}
		
		return callback;
	}

}