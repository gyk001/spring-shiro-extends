package cn.guoyukun.spring.shiro.realm;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 万能钥匙，密码输入masterkey就可以登录
 * @author <a href="mailto:gyk001@gmail.com">Guo Yukun</a>
 * @version 2014年10月31日
 *
 */
public class MasterKeyCredentialsMatcher extends SimpleCredentialsMatcher{

	private String masterKey ;
	
	@Override
	protected boolean equals(Object tokenCredentials, Object accountCredentials) {
		String tokenStr = null;
		if(tokenCredentials instanceof char[]){
			tokenStr = new String((char[])tokenCredentials);
		}else if(tokenCredentials instanceof CharSequence){
			tokenStr = ((CharSequence)tokenCredentials).toString();
		}else{
			throw new CredentialsException("不支持的密码类型："+tokenCredentials.getClass().getName());
		}
		if( masterKey.equals(tokenStr)){
			return true;
		}
		return super.equals(tokenStr, accountCredentials);
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public MasterKeyCredentialsMatcher(String masterKey) {
		super();
		this.masterKey = masterKey;
	}

	public MasterKeyCredentialsMatcher() {
		super();
	}
	
}
