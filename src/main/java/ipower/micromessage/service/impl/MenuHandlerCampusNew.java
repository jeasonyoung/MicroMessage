package ipower.micromessage.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.ArticleRespMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;
import ipower.micromessage.service.MenuBaseHandler;

/**
 * 学校通知。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public class MenuHandlerCampusNew extends MenuBaseHandler {
	public final static int MAX_Article_SIZE = 9;
	private String articleMaxPic,articleMinPic;
	
	public void setArticleMaxPic(String articleMaxPic) {
		this.articleMaxPic = articleMaxPic;
	}

	public void setArticleMinPic(String articleMinPic) {
		this.articleMinPic = articleMinPic;
	}

	@Override
	protected BaseRespMessage handler(TextReqMessage current, MicroContext context) {
		return this.createContent(current, context);
	}

	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		return this.createContent(current, context);
	}
	
	private BaseRespMessage createContent(BaseMessage current, MicroContext context){
		JSONObject post = new JSONObject();
		post.put("usersysid", context.getUserId());
		CallbackData callback = this.remoteEICPService.remotePost("CampusNew", post);
		if(callback == null || callback.getBody() == null || callback.getBody().trim().isEmpty()){
			return this.handlerMessage(current, context, "没有学校通知！");
		}
		
		JSONArray bodys = JSON.parseArray(callback.getBody());
		int size = 0;
		if(bodys == null || (size = bodys.size()) == 0)
			return this.handlerMessage(current, context, "没有学校通知！");
		
		ArticleRespMessage resp = new ArticleRespMessage(current);
		List<Article> articles = new ArrayList<Article>();
		Article title = new Article();
		//title.setTitle(" ");
		title.setPicUrl(this.articleMaxPic);
		articles.add(title);
		
		for(int i = 0; i < size; i++){
			if(i > MAX_Article_SIZE - 1) break;
			JSONObject obj = bodys.getJSONObject(i);
			if(obj != null){
				Article article = new Article();
				article.setTitle(obj.getString("title"));
				article.setPicUrl(this.articleMinPic);
				article.setUrl(obj.getString("link"));
				articles.add(article);
			}
		}
		resp.setArticleCount(articles.size());
		resp.setArticles(articles);
		return resp;
	}
}