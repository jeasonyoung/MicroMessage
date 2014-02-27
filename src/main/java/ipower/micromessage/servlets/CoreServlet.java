package ipower.micromessage.servlets;

import ipower.micromessage.msg.utils.SignUtil;
import ipower.micromessage.service.http.ICoreService;

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
	private ICoreService coreService;
	/**
	 * 设置微信核心业务服务接口。
	 * @param coreService
	 * 微信核心业务服务接口。
	 * */
	public void setCoreService(ICoreService coreService) {
		this.coreService = coreService;
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
			String token = this.coreService.token();
			if(SignUtil.checkSignature(signature, token, timestamp, nonce)){
				out.print(echostr);
				logger.info("signature=" + signature + ",timestamp=" + timestamp + ",nonce=" + nonce + ",echostr=" + echostr);
				logger.info("echostr:" + echostr);
			}else {
				logger.info("对微信的校验不成功！");
				logger.info("令牌:" + token);
				logger.info("获取微信签名：" + signature);
				logger.info("获取微信时间戳：" + timestamp);
				logger.info("获取微信随机数：" + nonce);
				logger.info("获取微信随机字符串：" + echostr);
			}
		}catch(Exception e){
			logger.error("发生异常：" + e.getMessage(), e);
			out.print(echostr);
		}
	}
	/**
	 * 处理微信服务器发来的消息。
	 * @param req
	 * @param resp
	 * */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		long before = System.currentTimeMillis();
		try {
			//将请求、响应的编码均设置为utf-8(防止中文乱码)
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			
			//调用核心业务服务接收消息、处理消息。
			logger.info("接收请求，处理中...");
			logger.info("接收请求url：" + req.getRequestURI());
			logger.info("请求源地址：" + req.getRemoteAddr());
			logger.info("请求主机：" + req.getRemoteHost());
			
			String respMsg = this.coreService.processRequest(req);
			logger.info("响应消息：\r\n" + respMsg);
			if(respMsg == null) respMsg = "";
			
			PrintWriter out = resp.getWriter();
			out.print(respMsg);
			out.close();
			 
		} catch (Exception e) {
			logger.error("处理微信服务器发来的消息时发生异常：", e);
			e.printStackTrace();
		}finally{
			logger.info("处理完毕！[耗时：" + (System.currentTimeMillis() - before) + " ms]");
		}
	}
}