package ipower.micromessage.msg;

import ipower.micromessage.msg.events.ClickEventMessage;
import ipower.micromessage.msg.events.EventMessage;
import ipower.micromessage.msg.resp.BaseRespMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 消息上下文。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public class MicroContext implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 事件消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	/**
	 * 文本消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 图片消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	/**
	 * 语音消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 视频消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	/**
	 * 地理位置信息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	/**
	 * 链接消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	/**
	 * 关注事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_SUBSCRIBE = "subscribe";
	/**
	 * 取消关注事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 扫描事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_SCAN = "SCAN";
	/**
	 * 上报地理位置事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_LOCATION = "LOCATION";
	/**
	 * 自定义菜单事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_CLICK = "CLICK";
	/**
	 * 列表中最多存储消息条数。
	 * */
	protected static final int LIST_MAX_COUNT = 5;
	private String userId,openId,lastMenuKey;
	private Date lastActiveTime;
	private List<BaseMessage> reqMessageList;
	private List<BaseRespMessage> respMessageList;
	/**
	 * 构造函数。
	 * */
	public MicroContext(){
		this.reqMessageList = new ArrayList<BaseMessage>();
		this.respMessageList = new ArrayList<BaseRespMessage>();
		this.setLastActiveTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param userId
	 * 	用户ID。
	 * @param openId
	 * 	微信openId。
	 * */
	public MicroContext(String userId, String openId){
		this();
		this.setUserId(userId);
		this.setOpenId(openId);
	}
	/**
	 * 构造函数。
	 * @param userId
	 * 	用户ID。
	 * @param openId
	 * 	微信openId。
	 * @param req
	 * 	请求消息。
	 * */
	public MicroContext(String userId, String openId, BaseMessage req){
		this(userId,openId);
		this.addReqMessage(req);
	}
	/**
	 * 获取最新菜单ID。
	 * @return 最新菜单ID。
	 * */
	public String getLastMenuKey() {
		return lastMenuKey;
	} 
	/**
	 * 添加请求消息。
	 * @param req
	 * 	请求消息。
	 * */
	public synchronized void addReqMessage(BaseMessage req){
		if(req != null){
			this.reqMessageList.add(req);
			if(this.reqMessageList.size() > LIST_MAX_COUNT){
				this.reqMessageList.remove(0);
			}
			this.setLastActiveTime(new Date());
			if(this.userId == null || this.userId.trim().isEmpty()){
				this.lastMenuKey = null;
				return;
			}
			//判断消息类型是否为事件。
			if(req.getMsgType().equalsIgnoreCase(REQ_MESSAGE_TYPE_EVENT)){
				EventMessage eventMessage = (EventMessage)req;
				if(eventMessage != null && eventMessage.getEvent().equalsIgnoreCase(EVENT_MESSAGE_TYPE_CLICK)){
					//菜单事件。
					ClickEventMessage clickEvent = (ClickEventMessage)eventMessage;
					if(clickEvent != null) this.lastMenuKey = clickEvent.getEventKey();
					return;
				}
			}
		}
	}
	/**
	 * 清空上下文。
	 * */
	public synchronized void clear(){
		this.lastMenuKey = null;
		this.setUserId(null);
		this.reqMessageList.clear();
		this.respMessageList.clear();
	}
	/**
	 * 添加响应消息。
	 * @param resp
	 * 	响应消息。
	 * */
	public synchronized void addRespMessage(BaseRespMessage resp){
		if(resp != null){
			this.respMessageList.add(resp);
			if(respMessageList.size() > LIST_MAX_COUNT){
				this.respMessageList.remove(0);
			}
		}
	}
	/**
	 * 获取当前请求消息。
	 * @return当前请求消息。
	 * */
	public BaseMessage getCurrentReqMessage(){
		if(this.reqMessageList == null || this.reqMessageList.size() == 0)
			return null;
		return this.reqMessageList.get(this.reqMessageList.size() - 1);
	}
	/**
	 * 获取上一次请求消息。
	 * @return 上一次请求消息。
	 * */
	public BaseMessage getPrevReqMessage(){
		if(this.reqMessageList == null || this.reqMessageList.size() < 2) 
			return null;
		return this.reqMessageList.get(this.reqMessageList.size() - 2);
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 * */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId
	 * 	用户ID。
	 * */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取微信OpenId。
	 * @return 微信OpenId。
	 * */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置微信OpenId。
	 * @param openId
	 * 	微信OpenId。
	 * */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取最后一次活动时间。
	 * @return 最后一次活动时间。
	 * */
	public Date getLastActiveTime() {
		return lastActiveTime;
	}
	/**
	 * 设置最后一次活动时间。
	 * @param lastActiveTime
	 * 	最后一次活动时间。
	 * */
	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	/**
	 * 获取请求消息列表。
	 * @return 请求消息列表。
	 * */
	public List<BaseMessage> getReqMessageList() {
		return reqMessageList;
	}
	/**
	 * 获取响应消息列表。
	 * @return 响应消息列表。
	 * */
	public List<BaseRespMessage> getRespMessageList() {
		return respMessageList;
	}
}