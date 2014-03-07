package ipower.micromessage.service.http;

import ipower.micromessage.msg.MicroContext;

/**
 * 消息上下文服务接口。
 * @author yangyong.
 * @since 2014-03-06.
 * */
public interface IMessgeContextService {
	/**
	 * 获取消息上下文。
	 * @param openId
	 * 	微信openId.
	 * @return
	 * 	上下文对象。
	 * */
	MicroContext get(String openId);
	/**
	 * 更新上下文。
	 * @param context
	 * 	上下文。
	 * */
	void Update(MicroContext context);
}