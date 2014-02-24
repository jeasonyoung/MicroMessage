package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Voice;

/**
 * 响应回复语音消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VoiceRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Voice voice;
	/**
	 * 获取语音。
	 * @return 语音。
	 * */
	public Voice getVoice() {
		return voice;
	}
	/**
	 * 设置语音。
	 * @param voice
	 * 语音。
	 * */
	public void setVoice(Voice voice) {
		this.voice = voice;
	}
}
