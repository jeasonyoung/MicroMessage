package ipower.micromessage.action;

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
	 * */
	public String create(){
		return this.microMenuService.create();
	}
	/**
	 * 查询菜单。
	 * @return 返回结果。
	 * */
	public String query(){
		return this.microMenuService.query();
	}
	/**
	 * 删除菜单。
	 * @return 返回结果。
	 * */
	public String delete(){
		return this.microMenuService.delete();
	}
}