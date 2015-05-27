package cn.guoyukun.spring.shiro.user.web.bind.method;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.guoyukun.spring.shiro.spi.SystemAccount;
import cn.guoyukun.spring.shiro.spi.SystemAccountService;
import cn.guoyukun.spring.shiro.user.web.bind.annotation.CurrentAccount;

/**
 * 
 * @author Guo yukun
 * @version 
 */
public class CurrentAccountMethodArgumentResolver implements HandlerMethodArgumentResolver {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(CurrentAccountMethodArgumentResolver.class);
	
	@Autowired
	private SystemAccountService accountService;
	
    public CurrentAccountMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentAccount.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    	Subject subject = SecurityUtils.getSubject();
    	String id = (String)subject.getPrincipal();
    	LOG.trace("进入@CurrentAccount解析器,Principal:{}",id);
    	if(id!=null){
    		try{
    			SystemAccount account = accountService.findByIdentify(id);
    			if(LOG.isTraceEnabled()){
    				LOG.trace("@CurrentAccount解析结果，Id[{}]->[{}]",id, account);
    			}
    			return account;
    		}catch(Exception e){
    			LOG.error("@CurrentAccount解析器，查找Id为["+id+"]异常！",e);	
    			return null;
    		}
    	}
    	return null;
    }

	public SystemAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(SystemAccountService accountService) {
		this.accountService = accountService;
	}
}
