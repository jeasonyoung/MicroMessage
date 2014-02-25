package ipower.micromessage.msg.utils;

import ipower.micromessage.msg.AccessToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
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
	public static JSONObject httpsRequest(X509TrustManager mgr, String requestUrl,String requestMethod, String data){
		JSONObject jsonObject = null;
		try {
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {mgr};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象。
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
			connection.setSSLSocketFactory(ssf);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//设置请求方式(GET/POST)
			connection.setRequestMethod(requestMethod);
			if(requestMethod.equalsIgnoreCase("GET")){
				connection.connect();
			}
			//当有数据需要提交时
			if(data != null && !data.trim().isEmpty()){
				if(logger.isDebugEnabled()){
					logger.debug("提交数据：" + data);
				}
				OutputStream outputStream = connection.getOutputStream();
				//注意编码格式，防止中文乱码
				outputStream.write(data.getBytes("UTF-8"));
				outputStream.close();
			}
			//将返回的输入流转换成字符串
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			StringBuffer buffer = new StringBuffer();
			String out = null;
			while((out = bufferedReader.readLine()) != null){
				buffer.append(out);
			}
			//释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
			if(logger.isDebugEnabled()){
				logger.debug("反馈数据：" + buffer.toString());
			}
			jsonObject = JSONObject.parseObject(buffer.toString());
		}catch(ConnectException e){
			logger.error("连接服务器["+ requestUrl +"]异常：", e);
		} catch (Exception e) {
			logger.error("https请求异常：",e);
		}
		return jsonObject;
	}
	/**
	 * 获取access_token
	 * @param mgr
	 * @param url
	 * @return
	 * */
	public static AccessToken getAccessToken(X509TrustManager mgr,String url){
		AccessToken token = null;
		if(url != null && !url.trim().isEmpty()){
			JSONObject jsonObject = httpsRequest(mgr, url, "GET", null);
			//如果请求成功
			if(jsonObject != null){
				try{
					token = new AccessToken();
					token.setToken(jsonObject.getString("access_token"));
					token.setExpiresIn(jsonObject.getInteger("expires_in"));
				}catch(Exception e){
					token = null;
					logger.error("获取token失败  errcode:" + jsonObject.getInteger("errcode") + " errmsg:" +  jsonObject.getString("errmsg"));
				}
			}
		}
		return token;
	}
}