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

import cn.guoyukun.spring.shiro.Constants;
import cn.guoyukun.spring.shiro.spi.SystemAccount;
import cn.guoyukun.spring.shiro.spi.SystemAccountService;
import cn.guoyukun.spring.shiro.user.web.bind.annotation.CurrentUser;

/**
 * 
 * @author Guo yukun
 * @version 
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(CurrentUserMethodArgumentResolver.class);
	
	@Autowired
	private SystemAccountService accountService;
	
    public CurrentUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
    	//return webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_SESSION);
    	Subject subject = SecurityUtils.getSubject();
    	Object principal = subject.getPrincipal();
    	if(principal!=null){
    		
    	}
    	
    	Long userId = (Long) webRequest.getAttribute(Constants.CURRENT_USERID, NativeWebRequest.SCOPE_SESSION);
    	if(userId==null){
    		LOG.trace("进入@CurrentUser解析器，Session中没有UserId");
    		return null;
    	}
    	SystemAccount account = accountService.findByIdentify(userId);
    	if(account==null){
    		LOG.trace("@CurrentUser解析器，查找UserId为[{}]的用户失败！",userId);	
    		return null;
    	}
    	LOG.trace("@CurrentUser解析器，解析出User[{}]:[{}]",userId,account);
    	return account;
    }

	public SystemAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(SystemAccountService accountService) {
		this.accountService = accountService;
	}
}
