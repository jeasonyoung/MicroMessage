package ipower.micromessage.msg.events;

import ipower.micromessage.msg.BaseMessage;

/**
 * 关注/取消事件消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class EventMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;
	private String event;
	/**
	 * 获取事件类型，subscribe(订阅)、unsubscribe(取消订阅)。
	 * @return 事件类型。
	 * */
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
}