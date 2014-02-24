package ipower.micromessage.msg;

import java.io.Serializable;

/**
 * 语音信息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class Voice implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mediaId;
	/**
	 * 获取通过上传多媒体文件，得到的id。
	 * @return 通过上传多媒体文件，得到的id。
	 * */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置通过上传多媒体文件，得到的id。
	 * @param mediaId
	 * 	通过上传多媒体文件，得到的id。
	 * */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}