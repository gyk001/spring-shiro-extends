package cn.guoyukun.spring.shiro.spi;

import java.io.Serializable;

/**
 * 账号
 * @author Guo
 *
 */
public interface SystemAccount {
	/**
	 * 账号的唯一标识，可以是用户名，邮箱等登陆时提供的身份标识
	 * @return
	 */
	Serializable getIdentify();
	
	/**
	 * 返回用户密码
	 * @return
	 */
	String getCredentials();
	/**
	 * 显示名称
	 * @return
	 */
	String getDisplayName();
	
	/**
	 * 是否已经锁定
	 * @return
	 */
	boolean isLocked();
	
}
