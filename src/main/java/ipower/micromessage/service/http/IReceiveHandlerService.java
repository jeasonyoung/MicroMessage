package ipower.micromessage.service.http;

import java.util.Map;

/**
 * 接收消息处理服务。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public interface IReceiveHandlerService {
	/**
	 * 设置消息上下文服务接口。
	 * @param contextService
	 * 	消息上下文服务接口。
	 * */
	void setContextService(IMessgeContextService contextService);
	/**
	 * 设置鉴权服务接口。
	 * @param authenticationService
	 * 	鉴权服务接口。
	 * */
	void setAuthenticationService(IAuthenticationService authenticationService);
	/**
	 * 消息处理。
	 * @param msgType 
	 * 	消息类型。
	 * @param body
	 * 	消息数据字典。
	 * @return
	 * 	 处理结果	
	 * */
	String handler(String msgType, Map<String, String> body);
}