package ipower.micromessage.service.http.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.service.http.IMessgeContextService;

/**
 * 消息上下文服务内存模式实现。
 * @author yangyong.
 * @since 2014-03-07.
 * */
public class MessgeContextServiceMemoryImpl implements IMessgeContextService {
	private static final Map<String, MicroContext> cache = Collections.synchronizedMap(new HashMap<String, MicroContext>());
	
	@Override
	public synchronized MicroContext get(String openId) {
		if(openId == null || openId.trim().isEmpty()) return null;
		return cache.get(openId);
	}

	@Override
	public synchronized void update(MicroContext context) {
		if(context == null || context.getOpenId() == null || context.getOpenId().trim().isEmpty())
			return;
		cache.put(context.getOpenId(), context);
	}

	@Override
	public synchronized void remove(MicroContext context) {
		if(context == null || context.getOpenId() == null || context.getOpenId().trim().isEmpty()) return;
		String openId = context.getOpenId();
		if(cache.containsKey(openId)){
			cache.remove(openId);
		}
	}
}