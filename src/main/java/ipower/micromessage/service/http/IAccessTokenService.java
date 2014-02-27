package ipower.micromessage.service.http;

import ipower.micromessage.msg.AccessToken;

/**
 *微信公众号的全局唯一票据服务。
 *@author yangyong.
 *@since 2014-02-27.
 * */
public interface IAccessTokenService {
	/**
	 * 获取微信公众号全局唯一票据。
	 * @return 
	 * 	全局唯一票据。
	 * */
	AccessToken getAccessToken();
}