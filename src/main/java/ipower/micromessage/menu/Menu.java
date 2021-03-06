package ipower.micromessage.menu;

import java.io.Serializable;

/**
 * 菜单。
 * @author yangyong.
 * @since 2012-02-25.
 * */
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	private Button[] button;
	/**
	 * 获取菜单。
	 * @return 菜单。
	 * */
	public Button[] getButton() {
		return button;
	}
	/**
	 * 设置菜单。
	 * @param button
	 * 菜单。
	 * */
	public void setButton(Button[] button) {
		this.button = button;
	}
}