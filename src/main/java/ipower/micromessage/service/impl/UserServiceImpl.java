package ipower.micromessage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ipower.micromessage.dao.IUserDao;
import ipower.micromessage.domain.User;
import ipower.micromessage.modal.UserInfo;
import ipower.micromessage.service.IRemoteEICPService;
import ipower.micromessage.service.IRemoteEICPService.CallbackData;
import ipower.micromessage.service.IUserService;
/**
 * 用户服务接口实现。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public class UserServiceImpl extends DataServiceImpl<User, UserInfo> implements IUserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	private IUserDao userDao;
	private IRemoteEICPService remoteEICPService;
	/**
	 * 设置远程EICP服务接口。
	 * @param remoteEICPService
	 * 	远程EICP服务接口。
	 * */
	public void setRemoteEICPService(IRemoteEICPService remoteEICPService) {
		this.remoteEICPService = remoteEICPService;
	}

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
	/**
	 * 添加用户。
	 * @param userId
	 * 	用户ID。
	 * @param userAccount
	 * 	用户账号。
	 * @param openId
	 * 	微信openId。
	 * @return 添加成功true，否则false。
	 * */
	protected boolean addUser(String userId, String userAccount, String openId) {
		if(userId == null || userId.trim().isEmpty()) return false;
		if(userAccount == null || userAccount.trim().isEmpty()) return false;
		if(openId == null || openId.trim().isEmpty()) return false;
		
		User data = this.userDao.load(User.class, openId);
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
	public String loadUserId(String openId) {
		if(openId == null || openId.trim().isEmpty())return null;
		User data = this.userDao.load(User.class, openId);
		if(data == null)return null;
		return data.getId();
	}

	@Override
	public VerifyCallback verification(String openId, String account, String password) {
		VerifyCallback callback = new VerifyCallback();
		try{
			JSONObject body = new JSONObject();
			body.put("userid", account);
			body.put("password", password);
			
			CallbackData result = this.remoteEICPService.remotePost("UserAuthentication", body);
			if(result.getCode() == 0){
				body = JSON.parseObject(result.getBody());
				Integer code = body.getInteger("resultcode");
				String msg = body.getString("resultmsg");
				callback.setSuccess(code == 0);
				callback.setMessage(msg);
				if(callback.isSuccess()){
					callback.setUserId(result.getUserId());
					boolean isAdd = this.addUser(callback.getUserId(), account, openId);
					logger.info(isAdd ? "用户验证成功！" :"插入用户本地缓存时发生未知错误!" );
					return callback;
				}
				logger.info("验证用户失败[" + code + "]:"+ msg);
				return callback;
			}
			logger.info("验证用户反馈：[" + result.getCode() + "]:" + result.getError());
			callback.setSuccess(false);
			callback.setMessage(result.getError());
			return callback;
			
		}catch(Exception e){
			logger.error("校验用户时发生异常：" + e.getMessage(), e);
			callback.setSuccess(false);
			callback.setMessage(e.getMessage());
		}
		return callback;
	}

	@Override
	public boolean remove(String openId) {
		if(openId == null || openId.trim().isEmpty()) return false;
		User user = this.userDao.load(User.class, openId);
		if(user != null){
			this.userDao.delete(user);
			return true;
		}
		return false;
	}
}