package ipower.micromessage.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.MenuArticleHandler;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;
/**
 * 我的校园。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerMySchool extends MenuArticleHandler {

	@Override
	protected BaseRespMessage createConent(BaseMessage current, MicroContext context, List<Article> articles) {
		JSONObject post = new JSONObject();
		post.put("usersysid", context.getUserId());
		CallbackData callback = this.remoteEICPService.remotePost("MySchool", post);
		if(callback.getCode() != 0){
			return this.handlerMessage(current, context, callback.getError());
		}
		
		JSONObject body = JSON.parseObject(callback.getBody());
		Article article = new Article();
		article.setTitle(body.getString("title"));
		article.setUrl(body.getString("link"));
		articles.add(article);
		
		return this.createRespMessage(current, articles);
	}

}