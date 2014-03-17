package ipower.micromessage.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.MenuArticleHandler;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;
/**
 * 我的课表。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerMyLesson extends MenuArticleHandler {

	@Override
	protected BaseRespMessage createConent(BaseMessage current, MicroContext context, List<Article> articles) {
		JSONObject post = new JSONObject();
		post.put("usersysid", context.getUserId());
		CallbackData callback = this.remoteEICPService.remotePost("MyLesson", post);
		if(callback.getCode() != 0){
			return this.handlerMessage(current, context, callback.getError());
		}
		JSONArray bodys = JSON.parseArray(callback.getBody());
		int size = 0;
		if(bodys == null || (size = bodys.size()) == 0){
			return this.handlerMessage(current, context, "没有获取您的课表！");
		}
		for(int i = 0; i < size; i++){
			if(i > MAX_Article_SIZE - 1) break;
			JSONObject obj = bodys.getJSONObject(i);
			if(obj != null){
				Article article = new Article();
				article.setTitle(obj.getString("title"));
				article.setUrl(obj.getString("link"));
				articles.add(article);
			}
		}
		return this.createRespMessage(current, articles);
	}	
}