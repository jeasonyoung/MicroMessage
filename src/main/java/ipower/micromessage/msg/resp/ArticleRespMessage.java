package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.BaseMessage;

import java.util.List;

/**
 * 响应回复图文消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ArticleRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Integer ArticleCount;
	private List<Article> Articles;
	/**
	 * 构造函数。
	 * */
	public ArticleRespMessage(){
		this.setMsgType("news");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public ArticleRespMessage(BaseMessage req){
		super(req);
	}
	/**
	 * 获取图文消息个数，限制为10条以内。
	 * @return 图文消息个数。
	 * */
	public Integer getArticleCount() {
		return ArticleCount;
	}
	/**
	 * 设置图文消息个数，限制为10条以内。
	 * @param articleCount。
	 * 	图文消息个数。
	 * */
	public void setArticleCount(Integer articleCount) {
		this.ArticleCount = articleCount;
	}
	/**
	 * 获取多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @return 多条图文消息信息。
	 * */
	public List<Article> getArticles() {
		return Articles;
	}
	/**
	 * 设置多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @param articles
	 * 多条图文消息信息。
	 * */
	public void setArticles(List<Article> articles) {
		this.Articles = articles;
	}
	
}
