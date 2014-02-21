package ipower.micromessage.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Filter代理类，配合Spring进行管理。
 * */
public class FilterProxy implements Filter {
	private static Logger logger = Logger.getLogger(FilterProxy.class);
	private Filter proxy;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String err = null;
		logger.info("初始化Filter代理类...");
		String targetFilterBean = filterConfig.getInitParameter("targetFilterBean");
		if(targetFilterBean == null || targetFilterBean.trim().isEmpty()){
			logger.error(err = "未配置参数：targetFilterBean");
			throw new ServletException(err);
		}
		logger.info("装载[Filter:"+targetFilterBean+"]");
		this.proxy = this.loadFilterBean(filterConfig,targetFilterBean);
		if(this.proxy == null){
			logger.error(err = "装载[Filter:"+targetFilterBean+"]失败，未在Spring配置中找到对象！");
			throw new ServletException(err);
		}
		this.proxy.init(filterConfig);
		logger.info("成功装载[Filter:"+targetFilterBean+"]对象:["+this.proxy.getClass().getName()+"]");
		logger.info("初始化Filter代理类完成。");
	}
	/**
	 * 加载目标Filter对象。
	 * @param targetFilterBean
	 * 	在Spring中配置的Bean对象。
	 * */
	protected synchronized Filter loadFilterBean(FilterConfig config,String targetFilterBean){
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return (Filter)wac.getBean(targetFilterBean);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		this.proxy.doFilter(request, response, chain);
	}

	@Override
	public void destroy() {
		
	}

}