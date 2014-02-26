package ipower.micromessage.service.http;

import java.util.Map;

/**
 * 接收消息处理服务。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public interface IReceiveHandlerService {
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