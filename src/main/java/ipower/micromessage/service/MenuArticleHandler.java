package ipower.micromessage.service;

import java.util.ArrayList;
import java.util.List;
import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;
import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.req.TextReqMessage;
import ipower.micromessage.msg.resp.ArticleRespMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;

/**
 * 菜单回复图文消息处理。
 * @author yangyong.
 * @since 2014-03-17.
 * */
public abstract class MenuArticleHandler extends MenuBaseHandler {
	private String picUrl;
	/**
	 * 图文消息的最大数据条数。
	 * */
	public final static int MAX_Article_SIZE = 9;
	/**
	 * 设置图片。
	 * */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 接收到微信文本请求处理。
	 * @param current
	 * 	文本请求。
	 * @param context
	 * 	上下文。
	 * @return
	 * 	回复消息。
	 * */
	@Override
	protected BaseRespMessage handler(TextReqMessage current, MicroContext context) {
		return this.createRespConent(current,context);
	}
	/**
	 * 接收到微信菜单事件处理。
	 * @param current
	 * 	菜单事件消息。
	 * @param context
	 * 	上下文。
	 * @return
	 * 	回复消息。
	 * */
	@Override
	protected BaseRespMessage menuClick(ClickEventMessage current, MicroContext context) {
		return this.createRespConent(current,context);
	}
	/**
	 * 创建图文回复内容。
	 * @param current
	 * 	当前请求。
	 * @param context
	 * 	上下文
	 * @return
	 * 	图文回复对象。
	 * */
	protected ArticleRespMessage createRespConent(BaseMessage current,MicroContext context){
		List<Article> articles = new ArrayList<Article>();
		if(this.picUrl != null && !this.picUrl.trim().isEmpty()){
			Article pic = this.createTopArticle(this.picUrl);
			if(pic != null)articles.add(pic);
		}
		return this.createConent(current,context, articles);
	}
	/**
	 * 创建顶部大图。
	 * @param picUrl
	 * 	图片地址。
	 * @return
	 * 	图文对象。
	 * */
	protected Article createTopArticle(String picUrl){
		Article pic = new Article(); 
		pic.setPicUrl(picUrl);
		pic.setTitle("请点击查看……");
		return pic;
	}
	/**
	 * 创建回复内容。
	 * @param current
	 * 	当前请求。
	 * @param context
	 *  上下文。
	 * @param articles
	 * 	图文集合。
	 * @return
	 * 	图文回复对象。
	 * */
	protected abstract ArticleRespMessage createConent(BaseMessage current,MicroContext context,List<Article> articles);
	/**
	 * 创建图文回复消息对象。
	 * @param current
	 * 	请求消息。
	 * @param articles
	 * 	图文消息集合。
	 * @return
	 * 	图文回复消息对象。
	 * */
	protected ArticleRespMessage createRespMessage(BaseMessage current,List<Article> articles){
		ArticleRespMessage resp = new ArticleRespMessage(current);
		if(articles != null && articles.size() > 0){
			resp.setArticleCount(articles.size());
			resp.setArticles(articles);
		}
		return resp;
	} 
}