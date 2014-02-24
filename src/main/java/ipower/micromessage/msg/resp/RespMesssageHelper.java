package ipower.micromessage.msg.resp;

import ipower.micromessage.msg.Article;
import ipower.micromessage.msg.utils.MsgUtil;

/**
 * 响应回复消息处理帮助类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public final class RespMesssageHelper extends MsgUtil {
	/**
	 * 响应回复消息对象转换成xml。
	 * @param message
	 *  响应回复消息对象。
	 * @return xml。
	 * */
	public static <T extends BaseRespMessage> String respMessageToXml(T message){
		xstream.alias("xml", message.getClass());
		if(message.getClass() == ArticleRespMessage.class){
			xstream.alias("item", new Article().getClass());
		}
		return xstream.toXML(message);
	}
}