package ipower.micromessage.service.impl;

import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import ipower.configuration.ModuleDefine;
import ipower.configuration.ModuleSystem;
import ipower.micromessage.menu.Button;
import ipower.micromessage.menu.CommonButton;
import ipower.micromessage.menu.ComplexButton;
import ipower.micromessage.menu.Menu;
import ipower.micromessage.menu.UrlButton;
import ipower.micromessage.msg.AccessToken;
import ipower.micromessage.msg.utils.HttpUtil;
import ipower.micromessage.service.IMenuService;
import ipower.micromessage.service.IMicroMenuService;
import ipower.micromessage.service.http.IAccessTokenService;

/**
 * 微信菜单服务实现。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class MicroMenuServiceImpl implements IMicroMenuService {
	private static Logger logger = Logger.getLogger(MicroMenuServiceImpl.class);
	private X509TrustManager x509TrustManager;
	private IAccessTokenService accessTokenService;
	private IMenuService menuService;
	private String currentSystemId,createUrl,queryUrl,deleteUrl;
	/**
	 * 设置HTTPS信任证书管理。
	 * @param x509TrustManager
	 * 	HTTPS信任证书管理。
	 * */
	public void setX509TrustManager(X509TrustManager x509TrustManager) {
		this.x509TrustManager = x509TrustManager;
	}
	/**
	 * 设置微信全局令牌服务。
	 * @param accessTokenService
	 * 	微信全局令牌服务。
	 * */
	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	/**
	 * 设置菜单服务。
	 * @param menuService
	 * 	菜单服务。
	 * */
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	/**
	 * 设置菜单系统Id。
	 * @param currentSystemId
	 * 	当前菜单系统Id。
	 * */
	public void setCurrentSystemId(String currentSystemId) {
		this.currentSystemId = currentSystemId;
	}
	/**
	 * 设置菜单创建Url。
	 * @param createUrl
	 * 	菜单创建Url。
	 * */
	public void setCreateUrl(String createUrl) {
		this.createUrl = createUrl;
	}
	/**
	 * 设置菜单查询Url。
	 * @param queryUrl
	 * 	菜单查询Url。
	 * */
	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}
	/**
	 * 设置菜单删除Url。
	 * @param deleteUrl
	 * 	菜单删除Url。
	 * */
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	/**
	 * 获取令牌。
	 * @return 令牌。
	 * */
	private AccessToken getToken(){
		AccessToken token = this.accessTokenService.getAccessToken();
		if(token == null){
			logger.error("获取全局统一令牌失败！");
		}
		return token;
	}
	/**
	 * 创建微信菜单。
	 * @return 反馈结果。
	 * */
	@Override
	public String create() {
		Menu menu = this.buildMenu(this.menuService, this.currentSystemId);
		if(menu == null){
			logger.error("创建微信菜单对象失败！");
			return "创建菜单失败！";
		}
		AccessToken token = this.getToken();
		if(token == null){
			return "获取令牌失败！";
		}
		String url = String.format(this.createUrl, token.getToken());
		if(url == null || url.trim().isEmpty()){
			logger.error("未配置菜单创建url！");
			return "未配置菜单创建url";
		}
		String data = JSONObject.toJSON(menu).toString();
		JSONObject result = HttpUtil.httpsRequest(this.x509TrustManager, url, "POST", data);
		if(result == null){
			logger.error("服务器未响应！");
			return "服务器未响应！";
		}
		return result.toJSONString();
	}
	/**
	 * 构建菜单对象。
	 * @param menuService
	 * 	菜单服务。
	 * @param currentSystemId
	 * 	当前系统ID。
	 * @return
	 * 	微信菜单。
	 * */
	private synchronized Menu buildMenu(IMenuService menuService, String currentSystemId){
		if(menuService == null || currentSystemId == null || currentSystemId.trim().isEmpty()){
			logger.error("配置参数menuService 或 currentSystemId ！");
			return null;
		}
		ModuleSystem moduleSystem = menuService.loadModuleSystem(currentSystemId);
		int size = 0;
		if(moduleSystem == null || moduleSystem.getModules() == null || (size = moduleSystem.getModules().size()) == 0){
			logger.error("未找到[currentSystemId:"+currentSystemId+"]下的菜单数据！");
			return null;
		}
		Menu menu = new Menu();
		Button[] btns = new Button[size > 3 ? 3 :size];
		for(int i = 0; i < moduleSystem.getModules().size(); i++){
			if(i >= 3)break;
			ModuleDefine define = moduleSystem.getModules().item(i);
			if(define == null)continue;
			int len = 0;
			if(define.getModules() != null && (len = define.getModules().size()) > 0){
				ComplexButton btn = new ComplexButton();
				btn.setName(define.getModuleName());
				Button[] childs = new Button[len > 5 ? 5 : len];
				for(int j = 0; j < define.getModules().size();j++){
					if(j >= 5)break;
					childs[j] = this.createButton(define.getModules().item(j));
				}
				btn.setSub_button(childs);
				btns[i] = btn;
			}else {
				btns[i] = this.createButton(define);
			}
		}
		menu.setButton(btns);
		return menu;
	}
	
	/**
	 * 创建微信菜单按钮。
	 * @param define
	 * 	菜单信息。
	 * @return
	 * 	微信按钮对象。
	 * */
	private Button createButton(ModuleDefine define){
		if(define == null) return null;
		logger.info("菜单：" + define.getModuleUri());
		String[] types = define.getModuleUri().split("\\|");
		if(types[0].equalsIgnoreCase("click")){
			logger.info("创建普通按钮");
			CommonButton btn = new CommonButton();
			btn.setName(define.getModuleName());
			btn.setKey(define.getModuleID());
			logger.info(btn.getClass().getName());
			return btn;
		}else if(types[0].equalsIgnoreCase("view")){
			logger.info("创建网页按钮");
			UrlButton btn = new UrlButton();
			btn.setName(define.getModuleName());
			btn.setUrl(types[1]);
			logger.info(btn.getClass().getName());
			return btn;
		}
		return null;
	}
	/**
	 * 查询微信菜单。
	 * @return
	 * 	反馈结果。
	 * */
	@Override
	public String query() {
		AccessToken token = this.getToken();
		if(token == null){
			return "获取令牌失败！";
		}
		String url = String.format(this.queryUrl, token.getToken());
		if(url == null || url.trim().isEmpty()){
			logger.error("未配置[queryUrl]菜单查询url！");
			return "未配置菜单查询url";
		}
		JSONObject result = HttpUtil.httpsRequest(this.x509TrustManager, url, "GET", null);
		if(result == null){
			logger.error("服务器未响应！");
			return "服务器未响应！";
		}
		return result.toJSONString();
	}
	/**
	 * 删除微信菜单。
	 * @return 
	 * 反馈结果。
	 * */
	@Override
	public String delete() {
		AccessToken token = this.getToken();
		if(token == null){
			return "获取令牌失败！";
		}
		String url = String.format(this.deleteUrl, token.getToken());
		if(url == null || url.trim().isEmpty()){
			logger.error("未配置[deleteUrl]菜单删除url！");
			return "未配置菜单删除url";
		}
		JSONObject result = HttpUtil.httpsRequest(this.x509TrustManager, url, "GET", null);
		if(result == null){
			logger.error("服务器未响应！");
			return "服务器未响应！";
		}
		return result.toJSONString();
	}
}