package cn.guoyukun.spring.shiro.spi;

import java.io.Serializable;


public interface SystemAccountService {
	/**
	 * 根据用户的唯一标识查询出账号信息
	 * @param identify
	 * @return
	 */
	public SystemAccount findByIdentify(Serializable identify);
}
