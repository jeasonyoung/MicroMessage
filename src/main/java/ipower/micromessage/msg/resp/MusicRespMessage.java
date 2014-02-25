package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Music;

/**
 * 响应回复音乐消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class MusicRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Music Music;
	/**
	 * 构造函数。
	 * */
	public MusicRespMessage(){
		this.setMsgType("music");
	}
	/**
	 * 获取音乐信息。
	 * @return 音乐信息。
	 * */
	public Music getMusic() {
		return Music;
	}
	/**
	 * 设置音乐信息。
	 * @param music
	 * 	音乐信息。	
	 * */
	public void setMusic(Music music) {
		this.Music = music;
	}
}