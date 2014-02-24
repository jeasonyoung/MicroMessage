package ipower.micromessage.msg.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class MsgUtil {
	private static Logger logger = Logger.getLogger(MsgUtil.class);
	/**
	 * 解析微信发来的请求(xml)
	 * @param request
	 * 	请求对象。
	 * @return 解析结果。
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception{
		//将解析结果存储在HashMap中。
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = null;
		try{
			//从request中取得输入流。
			inputStream = request.getInputStream();
			//读取输入流。
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			//得到xml根元素。
			Element root = document.getRootElement();
			if(logger.isDebugEnabled()){
				logger.debug("接收的xml请求：");
				logger.debug(root.asXML());
			}
			//得到根元素的所有子节点。
			List<Element> elements = root.elements();
			if(elements != null && elements.size() > 0){
				logger.info("请求参数：");
				//遍历所有子节点。
				for(Element e : elements){
					map.put(e.getName(), e.getText());
					logger.info(e.getName() + " = " + e.getText());
				}
			}
		}catch(IOException | DocumentException e){
			logger.error("解析微信服务器请求发生异常：", e);
			throw e;			
		}finally{
			//释放资源。
			if(inputStream != null){
				inputStream.close();
				inputStream = null;
			}
		}
		return map;
	}
	/**
	 * 扩展xstream,使其支持CDATA块。
	 **/
	protected static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				//对所有的xml节点的转换都增加CDATA标签。
				boolean cdata = true;
				@SuppressWarnings("rawtypes")
				public void startNode(String name,Class clazz){
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text){
					if(cdata){
						writer.write("<![CDATA["); 
                        writer.write(text); 
                        writer.write("]]>");
					}else {
						writer.write(text);
					}
				}
			};
		}
	});
}