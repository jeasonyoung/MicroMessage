package ipower.micromessage.servlets;

import ipower.micromessage.msg.utils.SignUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 核心Servlet
 * 	负责接收从微信服务传递过来的数据。
 * @author yangyong.
 * @since 2014-02-20.
 * */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 381665767336444132L;
	private static Logger logger = Logger.getLogger(CoreServlet.class);
	private String token;
	/**
	 * 设置微信令牌。
	 * @param token
	 *  微信令牌.
	 * */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 确认请求来自微信服务器。
	 * @param req
	 * @param resp
	 * */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		logger.info("接收请求url：" + req.getRequestURI());
		logger.info("请求源地址：" + req.getRemoteAddr());
		logger.info("请求主机：" + req.getRemoteHost());
		
		String signature = req.getParameter("signature"),//微信加密签名
				timestamp = req.getParameter("timestamp"),//时间戳
				nonce = req.getParameter("nonce"),//随机数
				echostr = req.getParameter("echostr");//随机字符串
		PrintWriter out = resp.getWriter();
		try{
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败 
			if(SignUtil.checkSignature(signature, this.token, timestamp, nonce)){
				out.print(echostr);
				logger.info("signature=" + signature + ",timestamp=" + timestamp + ",nonce=" + nonce + ",echostr=" + echostr);
			}
		}catch(Exception e){
			logger.error("发生异常：" + e.getMessage(), e);
			out.print("发生异常：" + e.getMessage());
		}
	}
	/**
	 * 
	 * */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		
	}
}