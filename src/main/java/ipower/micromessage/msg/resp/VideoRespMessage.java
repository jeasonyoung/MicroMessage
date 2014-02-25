package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Video;

/**
 * 响应回复视频消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VideoRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Video Video;
	/**
	 * 构造函数。
	 * */
	public VideoRespMessage(){
		this.setMsgType("video");
	}
	/**
	 * 获取视频信息。
	 * @return 视频信息。
	 * */
	public Video getVideo() {
		return Video;
	}
	/**
	 * 设置视频信息。
	 * @param video
	 * 	视频信息。
	 * */
	public void setVideo(Video video) {
		this.Video = video;
	}
}