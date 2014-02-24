package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Article;
import java.util.List;

/**
 * 响应回复图文消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ArticleRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Integer articleCount;
	private List<Article> articles;
	/**
	 * 构造函数。
	 * */
	public ArticleRespMessage(){
		this.setMsgType("news");
	}
	/**
	 * 获取图文消息个数，限制为10条以内。
	 * @return 图文消息个数。
	 * */
	public Integer getArticleCount() {
		return articleCount;
	}
	/**
	 * 设置图文消息个数，限制为10条以内。
	 * @param articleCount。
	 * 	图文消息个数。
	 * */
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}
	/**
	 * 获取多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @return 多条图文消息信息。
	 * */
	public List<Article> getArticles() {
		return articles;
	}
	/**
	 * 设置多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @param articles
	 * 多条图文消息信息。
	 * */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
}
