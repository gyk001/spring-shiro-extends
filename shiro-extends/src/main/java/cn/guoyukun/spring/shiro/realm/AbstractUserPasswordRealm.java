/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.guoyukun.spring.shiro.realm;


import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.guoyukun.spring.shiro.spi.SystemAccount;

public abstract class AbstractUserPasswordRealm extends AuthorizingRealm{

	/**
	 * 只能处理用户名密码认证
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * 获取权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		AuthorizationInfo info = new SimpleAuthorizationInfo();
		// TODO
		return info;
	}

	/**
	 * 获取认证身份
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		// 唯一标识
		String identify = upt.getUsername().trim();
		try{
			// 检索用户信息
	        SystemAccount account = getAccountByLoginIdentify(identify);
	        // 查找不到用户抛出异常
	        if(account==null){
	        	throw new UnknownAccountException("用户["+identify+"]不存在！");	
	        }
	        if(account.isLocked()){
	        	throw new LockedAccountException("用户["+identify+"]已锁定，请联系管理员！");	
	        }
	        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(identify, account.getCredentials(), this.getName());
	        if(! getCredentialsMatcher().doCredentialsMatch(token, sai)){
	        	throw new IncorrectCredentialsException("密码错误");
	        }
	        return sai;
		}catch(AuthenticationException ae){
			throw ae;
		}catch(Exception e){
			throw new AuthenticationException("验证["+identify+"]用户失败，系统未知错误！",e); 
		}
	}
	
	/**
	 * 通过登陆标识返回账号信息
	 * @param identify
	 * @return 返回检索到系统账号信息，没有用户的话返回null
	 * @throws Exception
	 */
	protected abstract SystemAccount getAccountByLoginIdentify(Serializable identify) throws Exception  ;
}


     
