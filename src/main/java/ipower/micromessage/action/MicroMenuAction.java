package ipower.micromessage.action;

import java.io.IOException;

import ipower.micromessage.service.IMicroMenuService;

public class MicroMenuAction extends BaseAction {
	private IMicroMenuService microMenuService;
	/**
	 * 设置微信菜单服务。
	 * @param microMenuService
	 * 	微信菜单服务。
	 * */
	public void setMicroMenuService(IMicroMenuService microMenuService) {
		this.microMenuService = microMenuService;
	}
	/**
	 * 创建菜单。
	 * @return 返回结果。
	 * @throws IOException 
	 * */
	public void create() throws IOException{
		String result = this.microMenuService.create();
		this.writeJson(result);
	}
	/**
	 * 查询菜单。
	 * @return 返回结果。
	 * @throws IOException 
	 * */
	public void query() throws IOException{
		String result = this.microMenuService.query();
		this.writeJson(result);
	}
	/**
	 * 删除菜单。
	 * @return 返回结果。
	 * @throws IOException 
	 * */
	public void delete() throws IOException{
		String result = this.microMenuService.delete();
		this.writeJson(result);
	}
}