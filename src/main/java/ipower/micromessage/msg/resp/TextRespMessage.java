package ipower.micromessage.msg.resp;
/**
 * 响应回复文本消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class TextRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private String Content;
	/**
	 * 构造函数。
	 * */
	public TextRespMessage(){
		this.setMsgType("text");
	}
	/**
	 * 获取消息内容。
	 * @return 消息内容。
	 * */
	public String getContent() {
		return Content;
	}
	/**
	 * 设置消息内容。
	 * @param content
	 * 	消息内容。
	 * */
	public void setContent(String content) {
		this.Content = content;
	}
}