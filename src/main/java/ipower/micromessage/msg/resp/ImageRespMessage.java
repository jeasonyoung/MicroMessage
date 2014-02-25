package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Image;

/**
 * 响应回复图片消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ImageRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Image Image;
	/**
	 * 构造函数。
	 * */
	public ImageRespMessage(){
		this.setMsgType("image");
	}
	/**
	 * 获取图片信息。
	 * @return 图片信息。
	 * */
	public Image getImage() {
		return Image;
	}
	/**
	 * 设置图片信息。
	 * @param image
	 * 	图片信息。
	 * */
	public void setImage(Image image) {
		this.Image = image;
	}
}