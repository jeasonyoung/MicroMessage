package ipower.micromessage.service.http;
/**
 * 用户认证接口。
 * @author yangyong.
 * @since 2014-03-07.
 * */
public interface IUserAuthentication {
	/**
	 * 加载用户ID。
	 * @param openId
	 * 	微信openId。
	 * @return 用户ID。
	 * */
	String loadUserId(String openId);
	/**
	 * 验证用户。
	 * @param openId
	 * 	微信openId.
	 * @param account
	 * 	账号。
	 * @param password
	 * 	密码。
	 * @return
	 * 	如果校验成功返回userId,否则返回null;
	 * */
	String verification(String openId, String account,String password);
}