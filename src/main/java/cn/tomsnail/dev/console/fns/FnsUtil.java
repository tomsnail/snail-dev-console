package cn.tomsnail.dev.console.fns;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;

import cn.tomsnail.dev.console.config.server.dao.TsServerDao;
import cn.tomsnail.dev.console.config.server.entity.TsServer;

public class FnsUtil {
	
	
	private static TsServerDao tsServerDao = SpringContextHolder.getBean(TsServerDao.class);
	
	
	public static String getServerName(String serverId){
		TsServer tsServer = tsServerDao.get(serverId);
		if(tsServer==null){
			return "";
		}else{
			return tsServer.getName();
		}
	}

}
