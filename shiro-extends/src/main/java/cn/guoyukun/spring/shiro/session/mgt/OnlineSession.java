/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.guoyukun.spring.shiro.session.mgt;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-20 下午2:29
 * <p>Version: 1.0
 */
public class OnlineSession extends SimpleSession {
	private static final long serialVersionUID = -2846948210812028816L;

	/**
	 * 用户ID
	 */
	private String uid;

    /**
     * 用户浏览器类型
     */
    private String userAgent;
    /**
     * 引导页面
     */
    private String referer;
    /**
     * 客户端IP地址
     */
    private String clientIp;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	
}
