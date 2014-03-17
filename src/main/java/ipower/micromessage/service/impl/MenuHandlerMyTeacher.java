package ipower.micromessage.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.ArticleRespMessage;
import ipower.micromessage.service.MenuArticleHandler;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;

/**
 * 我的老师。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerMyTeacher extends MenuArticleHandler {
	
	@Override
	protected ArticleRespMessage createConent(BaseMessage current,MicroContext context, List<Article> articles) {
		JSONObject post = new JSONObject();
		post.put("usersysid", context.getUserId());
		CallbackData callback = this.remoteEICPService.remotePost("MyTeacher", post);
		if(callback.getCode() != 0){
			articles.add(new Article(callback.getError()));
			return this.createRespMessage(current, articles);
		}
		JSONArray bodys = JSON.parseArray(callback.getBody());
		int size = 0;
		if(bodys == null || (size = bodys.size()) == 0){
			articles.add(new Article("没有获取到您的老师！"));
			return this.createRespMessage(current, articles);
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