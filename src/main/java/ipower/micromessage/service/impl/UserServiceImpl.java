package ipower.micromessage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import ipower.micromessage.dao.IUserDao;
import ipower.micromessage.domain.User;
import ipower.micromessage.modal.UserInfo;
import ipower.micromessage.service.IUserService;

/**
 * 用户服务接口实现。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public class UserServiceImpl extends DataServiceImpl<User, UserInfo> implements IUserService {
	private IUserDao userDao;
	@Override
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	protected List<User> find(UserInfo info) {
		String hql = "from User u where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		return this.userDao.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	protected UserInfo changeModel(User data) {
		if(data == null) return null;
		UserInfo info = new UserInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(UserInfo info) {
		String hql = "select count(*) from User u where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.userDao.count(hql, parameters);
	}

	@Override
	protected String addWhere(UserInfo info, String hql, Map<String, Object> parameters) {
		if(info.getAccount() != null && !info.getAccount().trim().isEmpty()){
			hql += " and (u.account like :account or u.id like :account) ";
			parameters.put("account", "%" + info.getAccount() + "%");
		}
		if(info.getOpenId() != null && !info.getOpenId().trim().isEmpty()){
			hql += " and (u.openId like :openId) ";
			parameters.put("openId", "%" + info.getOpenId() + "%");
		}
		return hql;
	}

	@Override
	public UserInfo update(UserInfo info) {
		if(info != null){
			boolean isAdded = false;
			User data = (info.getId() == null || info.getId().trim().isEmpty()) ? null : this.userDao.load(User.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new User();
			}
			BeanUtils.copyProperties(info, data);
			if(isAdded)this.userDao.save(data);
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		 if(ids == null || ids.length == 0) return;
		 for(int i = 0; i < ids.length; i++){
			 if(ids[i] == null || ids[i].trim().isEmpty())
				 continue;
			 User data = this.userDao.load(User.class, ids[i]);
			 if(data != null)
				 this.userDao.delete(data);
		 }
	}

	@Override
	public boolean AddUser(String userId, String userAccount, String openId) {
		if(userId == null || userId.trim().isEmpty()) return false;
		if(userAccount == null || userAccount.trim().isEmpty()) return false;
		if(openId == null || openId.trim().isEmpty()) return false;
		
		User data = this.loadByUserId(userId);
		if(data == null){
			data = this.loadByOpenId(openId);
		}
		boolean isAdded = false;
		if(isAdded = (data == null)){
			data = new User();
			data.setCreateTime(new Date());
		}
		data.setId(userId);
		data.setAccount(userAccount);
		data.setOpenId(openId);

		if(isAdded)this.userDao.save(data);
		
		return true;
	}

	@Override
	public User loadByUserId(String userId) {
		if(userId == null)return null;
		return this.userDao.load(User.class, userId);
	}

	@Override
	public User loadByOpenId(String openId) {
		if(openId == null || openId.trim().isEmpty()) return null;
		final String hql = "from User u where u.openId = :openId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("openId", openId);
		List<User> list = this.userDao.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}