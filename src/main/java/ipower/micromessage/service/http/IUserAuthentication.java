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
	 * 	验证反馈信息。
	 * */
	VerifyCallback verification(String openId, String account,String password);
	/**
	 * 验证反馈信息。
	 * @author yangyong.
	 * @since 2014-03-10.
	 * */
	public class VerifyCallback{
		private boolean success;
		private String userId,message;
		/**
		 * 构造函数。
		 * */
		public VerifyCallback(){
			this.success = false;
		}
		/**
		 * 获取验证结果。
		 * @return 验证结果true，false。
		 * */
		public boolean isSuccess() {
			return success;
		}
		/**
		 * 设置验证结果。
		 * @param success
		 * 	验证结果。
		 * */
		public void setSuccess(boolean success) {
			this.success = success;
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
		 * 获取反馈信息。
		 * @return 反馈信息。
		 * */
		public String getMessage() {
			return message;
		}
		/**
		 * 设置反馈信息。
		 * @param message
		 * 	反馈信息。
		 * */
		public void setMessage(String message) {
			this.message = message;
		}
	}
}