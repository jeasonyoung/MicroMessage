package ipower.micromessage.service.http.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ipower.micromessage.msg.MicroContext;
import ipower.micromessage.service.http.IMessgeContextService;

/**
 * 消息上下文服务内存模式实现。
 * @author yangyong.
 * @since 2014-03-07.
 * */
public class MessgeContextServiceMemoryImpl implements IMessgeContextService {
	private static Logger logger = Logger.getLogger(MessgeContextServiceMemoryImpl.class);
	private static final Map<String, MicroContext> cache = Collections.synchronizedMap(new HashMap<String, MicroContext>());
	
	@Override
	public synchronized MicroContext get(String openId) {
		if(openId == null || openId.trim().isEmpty()) return null;
		
		MicroContext context = cache.get(openId);
		logger.info("从缓存中获取上下文[" +(context != null)+ "]");
		return context;
	}

	@Override
	public synchronized void update(MicroContext context) {
		if(context == null || context.getOpenId() == null || context.getOpenId().trim().isEmpty())
			return;
		cache.put(context.getOpenId(), context);
		logger.info("更新上下文到缓存[" +context.getOpenId()+ "]");
	}

	@Override
	public synchronized void remove(MicroContext context) {
		if(context == null || context.getOpenId() == null || context.getOpenId().trim().isEmpty()) return;
		String openId = context.getOpenId();
		logger.info("准备中缓存中移除上下文："+ openId);
		if(cache.containsKey(openId)){
			cache.put(openId, null);
			cache.remove(openId);
			context.clear();
			logger.info("从缓存中移除上下文："+ openId);
		}
	}
}