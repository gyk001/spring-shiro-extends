package cn.guoyukun.spring.shiro.pam;



import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypedRealmAuthenticator extends ModularRealmAuthenticator{
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(TypedRealmAuthenticator.class);
	
	@Override
	protected AuthenticationInfo doMultiRealmAuthentication(
			Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = getAuthenticationStrategy();

        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        if (LOG.isTraceEnabled()) {
            LOG.trace("Iterating through {} realms for PAM authentication", realms.size());
        }

        for (Realm realm : realms) {

            aggregate = strategy.beforeAttempt(realm, token, aggregate);

            if (realm.supports(token)) {

                LOG.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);

                AuthenticationInfo info = null;
                Throwable t = null;
                //假定所有类型的token都只有一个realm能认证，认证失败直接抛出异常
                //否则无法获取准确的异常信息
                //try {
                    info = realm.getAuthenticationInfo(token);
//                } catch (Throwable throwable) {
//                    t = throwable;
//                    if (LOG.isDebugEnabled()) {
//                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
//                        LOG.debug(msg, t);
//                    }
//                    throw throwable;
//                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);

            } else {
                LOG.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);

        return aggregate;
	}

	
}
