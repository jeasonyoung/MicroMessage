package ipower.micromessage.service;

import ipower.micromessage.dao.IUserDao;
import ipower.micromessage.modal.UserInfo;
import ipower.micromessage.service.http.IUserAuthentication;

/**
 * 用户服务接口。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public interface IUserService extends IDataService<UserInfo>,IUserAuthentication  {
	/**
	 * 设置用户数据访问接口。
	 * @param userDao
	 * 	用户数据访问接口。
	 * */
	void setUserDao(IUserDao userDao);
}