package ipower.micromessage.msg;

import java.io.Serializable;

/**
 * 图文信息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title,description,picUrl,url;
	/**
	 * 获取图文消息标题。
	 * @return 图文消息标题。
	 * */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置图文消息标题。
	 * @param title
	 * 	图文消息标题。
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取图文信息描述。
	 * @return 图文信息描述。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置图文信息描述。
	 * @param description
	 * 	图文信息描述。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取图片链接。
	 * @return 图片链接。
	 * */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置图片链接。
	 * @param picUrl
	 * 	图片链接。
	 * */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取图文消息跳转链接。
	 * @return 图文消息跳转链接。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置图文消息跳转链接。
	 * @param url
	 * 	图文消息跳转链接。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
}
