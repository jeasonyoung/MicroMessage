package ipower.micromessage.service.http.impl;

import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import ipower.cache.CacheEntity;
import ipower.cache.CacheTimeHandler;
import ipower.micromessage.msg.AccessToken;
import ipower.micromessage.msg.utils.HttpUtil;
import ipower.micromessage.service.http.IAccessTokenService;
import ipower.utils.MD5Util;

/**
 *微信公众号的全局唯一票据服务实现类。
 *@author yangyong.
 *@since 2014-02-27.
 * */
public class AccessTokenServiceImpl implements IAccessTokenService {
	private static Logger logger = Logger.getLogger(AccessTokenServiceImpl.class);
	private String url,appId,secret;
	private X509TrustManager x509TrustManager;
	/**
	 * 设置SSL证书管理。
	 * @param x509TrustManager
	 * 	SSL证书管理。
	 * */
	public void setX509TrustManager(X509TrustManager x509TrustManager) {
		this.x509TrustManager = x509TrustManager;
	}
	/**
	 * 设置请求地址。
	 * @param url
	 * 	请求地址。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 设置第三方用户唯一凭证。
	 * @param appId
	 * 	第三方用户唯一凭证。
	 * */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 设置第三方用户唯一凭证密钥。
	 * @param secret
	 * 	第三方用户唯一凭证密钥。
	 * */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public AccessToken getAccessToken() {
		logger.info("开始获取微信公众号的全局唯一票据：");
		String key = MD5Util.MD5(this.appId + "_" + this.secret);
		CacheEntity<?> tokenEntity = CacheTimeHandler.getCache(key);
		AccessToken token = null;
		if(tokenEntity != null){
			token = (AccessToken)tokenEntity.getEntity();
			logger.info("得到缓存的票据:" + (token != null));
			if(token != null){
				logger.info(token.getToken());
				return token;
			}
		}
		String url = String.format(this.url, this.appId, this.secret);
		logger.info("请求url:" + url);
		JSONObject jsonObject = HttpUtil.httpsRequest(this.x509TrustManager, url, "GET", null);
		if(jsonObject == null){
			logger.error("没有得到微信服务器反馈！");
			return null;
		}
		try{
			String access_token = jsonObject.getString("access_token");
			int expires_in = jsonObject.getIntValue("expires_in");
			if(access_token == null || access_token.trim().isEmpty()){
				throw new Exception();
			}
			token = new AccessToken();
			token.setToken(access_token);
			token.setExpiresIn(expires_in);
			
			tokenEntity = new CacheEntity<AccessToken>(key,token,token.getExpiresIn());
			CacheTimeHandler.addCache(key, tokenEntity);
		}catch(Exception e){
			logger.error("获取全局唯一票据异常：errcode:" + jsonObject.getString("errcode") + ",errmsg:" + jsonObject.getString("errmsg"));
		}
		return token;
	}

}