package cn.guoyukun.spring.shiro.session.mgt;

import java.util.List;

public interface UserOnlineService {

	    /**
	     * 上线
	     *
	     * @param userOnline
	     */
	    public void online(OnlineSession session) ;

	    /**
	     * 下线
	     *
	     * @param sid
	     */
	    public void offline(String sid);

	    /**
	     * 批量下线
	     *
	     * @param needOfflineIdList
	     */
	    public void batchOffline(List<String> needOfflineIdList) ;



	}
