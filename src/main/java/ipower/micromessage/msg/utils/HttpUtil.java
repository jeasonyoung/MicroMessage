package ipower.micromessage.msg.utils;
import java.io.IOException;
import java.net.ConnectException;

import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
/**
 * HTTP访问工具类。
 * @author yangyong.
 * @since 2014-02-25.
 * */
public final class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	/**
	 * 发起https请求并获取结果。
	 * @param mgr
	 * 	SSL证书。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 *  @return 反馈结果(通过JSONObject.get(key)的方式获取json对象的属性值)。
	 * */
	public static JSONObject httpsRequest(X509TrustManager mgr, String requestUrl, String requestMethod, String data){
		JSONObject jsonObject = null;
		try {
			logger.info("url:\r\n"+ requestUrl);
			logger.info("method:\r\n" + requestMethod);
			logger.info("data:\r\n"+ data);
			
			String result = ipower.utils.HttpUtil.sendRequest(mgr, requestUrl, requestMethod, data);
			 
			logger.info("callback:\r\n" + result);
			 
			jsonObject = JSONObject.parseObject(result);
		}catch(ConnectException e){
			logger.error("连接服务器["+ requestUrl +"]异常：", e);
		} catch (Exception e) {
			logger.error("https请求异常：",e);
		}
		return jsonObject;
	}
	/**
	 * 发起http请求并获取结果。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 *  @return 反馈结果(通过JSONObject.get(key)的方式获取json对象的属性值)。
	 * @throws IOException 
	 * */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String data) throws IOException{
		JSONObject jsonObject = null;
		try {
			logger.info("url:\r\n"+ requestUrl);
			logger.info("method:\r\n" + requestMethod);
			logger.info("data:\r\n"+ data);
			
			String result = ipower.utils.HttpUtil.sendRequest(requestUrl, requestMethod, data);
			 
			logger.info("callback:\r\n" + result);
			 if(result == null || result.trim().isEmpty()) return null;
			jsonObject = JSONObject.parseObject(result);
		}catch(ConnectException e){
			logger.error("连接服务器["+ requestUrl +"]异常：", e);
		}
		return jsonObject;
	}
}