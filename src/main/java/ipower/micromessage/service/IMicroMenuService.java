package ipower.micromessage.service;
/**
 * 微信菜单服务。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public interface IMicroMenuService {
	/**
	 * 创建菜单。
	 * @return 创建结果。
	 * */
	String create();
	/**
	 * 查询菜单。
	 * @return 查询结果。
	 * */
	String query();
	/**
	 * 删除菜单。
	 * @return 删除结果。
	 * */
	String delete();
}