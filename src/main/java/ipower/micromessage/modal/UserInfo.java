package ipower.micromessage.modal;

import java.util.Date;

import ipower.model.Paging;

/**
 * 用户信息。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public class UserInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,account,openId;
	private Date createTime,lastTime;
	private Integer status;
	/**
	 * 获取用户Id。
	 * @return 用户Id。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户Id。
	 * @param id
	 * 	用户Id。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户账号。
	 * @return 用户账号。
	 * */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户账号。
	 * @param account
	 * 	用户账号。
	 * */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取用户微信Id。
	 * @return 用户微信Id。
	 * */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置用户微信Id。
	 * @param openId
	 * 	用户微信Id。
	 * */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 	创建时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后活动时间。
	 * @return 最后活动时间。
	 * */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后活动时间。
	 * @param lastTime
	 * 	最后活动时间。
	 * */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取用户状态。
	 * @return 用户状态(1-关注，0-取消关注)。
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态。
	 * @param status
	 * 	用户状态(1-关注，0-取消关注)。
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
}