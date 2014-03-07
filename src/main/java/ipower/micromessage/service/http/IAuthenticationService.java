package ipower.micromessage.service.http;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.msg.resp.BaseRespMessage;

/**
 * 鉴权服务接口。
 * @author yangyong.
 * @since 2014-03-07.
 * */
public interface IAuthenticationService {
	/**
	 * 鉴权。
	 * @param context
	 * 	消息上下文。
	 * @return
	 * 	回复消息。
	 * */
	BaseRespMessage authen(MicroContext context);
}