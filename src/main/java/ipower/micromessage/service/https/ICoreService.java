package ipower.micromessage.service.https;

import javax.servlet.http.HttpServletRequest;
/**
 * 微信核心业务服务接口。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public interface ICoreService {
	/**
	 * 微信令牌(微信开发模式注册的)。
	 * @return 微信令牌。
	 * */
	String token();
	/**
	 * 处理微信发来的请求。
	 * @param req
	 * 	请求参数集合。
	 * @return 反馈结果。
	 * */
	String processRequest(HttpServletRequest req);
}