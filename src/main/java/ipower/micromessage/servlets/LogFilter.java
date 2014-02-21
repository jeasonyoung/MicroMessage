package ipower.micromessage.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 日志记录Filter。
 * 负责记录从微信传递过来的原始数据流。
 * @author yangyong.
 * @since 2014-02-20.
 * */
public class LogFilter implements Filter {
	private FilterConfig config;
	/**
	 * 实现初始化方法。
	 * @param filterConfig
	 *  filter配置。
	 * */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}
	/**
	 * 执行过滤的核心方法。
	 * @param request
	 * @param response
	 * @param chain
	 * */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ServletContext context = this.config.getServletContext();
		long before = System.currentTimeMillis();
		context.log("开始过滤...");
		HttpServletRequest req = (HttpServletRequest)request;
		context.log("请求地址:"+ req.getServletPath());
		chain.doFilter(request, response);
		long after= System.currentTimeMillis();
		context.log("过滤结束！");
		context.log("请求被定位到" + req.getRequestURI() + "所花的时间为: " + (after - before));
	}
	/**
	 * 实现销毁方法。
	 * */
	@Override
	public void destroy() {
		this.config = null;
	}

}