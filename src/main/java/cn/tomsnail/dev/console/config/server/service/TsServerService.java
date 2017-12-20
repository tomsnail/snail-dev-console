/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.config.server.dao.TsServerDao;

/**
 * 服务器配置Service
 * @author yangsong
 * @version 2017-12-20
 */
@Service
@Transactional(readOnly = true)
public class TsServerService extends TreeService<TsServerDao, TsServer> {

	public TsServer get(String id) {
		return super.get(id);
	}
	
	public List<TsServer> findList(TsServer tsServer) {
		if (StringUtils.isNotBlank(tsServer.getParentIds())){
			tsServer.setParentIds(","+tsServer.getParentIds()+",");
		}
		return super.findList(tsServer);
	}
	
	@Transactional(readOnly = false)
	public void save(TsServer tsServer) {
		super.save(tsServer);
	}
	
	@Transactional(readOnly = false)
	public void delete(TsServer tsServer) {
		super.delete(tsServer);
	}
	
}