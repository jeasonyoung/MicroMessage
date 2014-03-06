package ipower.micromessage.service;

import ipower.micromessage.dao.IUserDao;
import ipower.micromessage.domain.User;
import ipower.micromessage.modal.UserInfo;

/**
 * 用户服务接口。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public interface IUserService extends IDataService<UserInfo>  {
	/**
	 * 设置用户数据访问接口。
	 * @param userDao
	 * 	用户数据访问接口。
	 * */
	void setUserDao(IUserDao userDao);
	/**
	 * 添加用户。
	 * @param userId
	 * 	用户Id。
	 * @param userAccount
	 * 	用户账号。
	 * @param openId
	 * 	微信ID。
	 * @return
	 * 	添加成功true，否则false.
	 * */
	boolean AddUser(String userId,String userAccount,String openId);
	/**
	 * 加载用户。
	 * @param userId
	 * 	用户Id。
	 * @return
	 * 	用户。
	 * */
	User loadByUserId(String userId);
	/**
	 * 加载用户。
	 * @param openId
	 * 	微信Id。
	 * @return
	 * 	用户。
	 * */
	User loadByOpenId(String openId);
}