package ipower.micromessage.msg;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息基类。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public abstract class BaseMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String toUserName,fromUserName,msgType;
	private Long createTime;
	/**
	 * 构造函数。
	 * */
	public BaseMessage(){
		this.setCreateTime(new Date().getTime());
	}
	/**
	 * 获取目标方帐号。
	 * @return 目标帐号。
	 * */
	public String getToUserName() {
		return toUserName;
	}
	/**
	 * 设置目标方帐号。
	 * @param toUserName
	 * 	目标方帐号。
	 * */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	/**
	 * 获取发送方帐号。
	 * @return 发送方帐号。
	 * */
	public String getFromUserName() {
		return fromUserName;
	}
	/**
	 * 设置发送方帐号。
	 * @param fromUserName
	 *  发送方帐号。
	 * */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	/**
	 * 获取消息类型(text/image/voice/video/location/link)。
	 * @return 消息类型(text/image/voice/video/location/link)。
	 * */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * 设置消息类型。
	 * @param msgType
	 * 消息类型(text/image/voice/video/location/link)。
	 * */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * 获取消息创建时间。
	 * @return 消息创建时间。
	 * */
	public Long getCreateTime() {
		return createTime;
	}
	/**
	 * 设置消息创建时间。
	 * @param createTime
	 * 	消息创建时间。
	 * */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}