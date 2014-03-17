package ipower.micromessage.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 远程EICP数据服务接口。
 * @author yangyong.
 * @since 2014-03-10.
 * */
public interface IRemoteEICPService {
	/**
	 * 设置EICP数据访问Url。
	 * @param url
	 * 	数据访问URL。
	 * */
	void setUrl(String url);
	/**
	 * 远程数据提交。
	 * @param ident
	 * 	head标识。
	 * @param post
	 * 	提交主体数据。
	 * @return
	 * 	反馈数据。
	 * */
	CallbackData remotePost(String ident, JSONObject post);
	/**
	 * 反馈数据。
	 * */
	public class CallbackData{
		private Integer code;
		private String userId,body,error;
		/**
		 * 获取反馈代码(0成功，-1失败)。
		 * @return 反馈代码。
		 * */
		public Integer getCode() {
			return code;
		}
		/**
		 * 设置反馈代码(0成功，-1失败)。
		 * @param code
		 * 	反馈代码(0成功，-1失败)。
		 * */
		public void setCode(Integer code) {
			this.code = code;
		}
		/**
		 * 获取错误信息。
		 * @return 错误信息。
		 * */
		public String getError() {
			return error;
		}
		/**
		 * 设置错误信息。
		 * @param error
		 * 	错误信息。
		 * */
		public void setError(String error) {
			this.error = error;
		}
		/**
		 * 获取业务系统用户代码。
		 * @return 业务系统用户代码。
		 * */
		public String getUserId() {
			return userId;
		}
		/**
		 * 设置业务系统用户代码。
		 * @param userId
		 * 	业务系统用户代码。
		 * */
		public void setUserId(String userId) {
			this.userId = userId;
		}
		/**
		 * 获取反馈主体数据。
		 * @return 主体数据。
		 * */
		public String getBody() {
			return body;
		}
		/**
		 * 设置反馈主体数据。
		 * @param body
		 * 	反馈主体数据。
		 * */
		public void setBody(String body) {
			this.body = body;
		}
	}
}