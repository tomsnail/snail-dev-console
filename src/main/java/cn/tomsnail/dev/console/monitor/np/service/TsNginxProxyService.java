/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.np.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.tomsnail.dev.console.config.server.dao.TsServerDao;
import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.monitor.np.entity.TsNginxProxy;
import cn.tomsnail.dev.console.monitor.np.dao.TsNginxProxyDao;
import cn.tomsnail.dev.console.util.RedisConfig;
import cn.tomsnail.dev.console.util.RedisUtil;

/**
 * Nginx代理Service
 * @author yangsong
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class TsNginxProxyService extends TreeService<TsNginxProxyDao, TsNginxProxy> {
	
	
	@Autowired
	private TsServerDao tsServerDao;

	public TsNginxProxy get(String id) {
		return super.get(id);
	}
	
	public List<TsNginxProxy> findList(TsNginxProxy tsNginxProxy) {
		if (StringUtils.isNotBlank(tsNginxProxy.getParentIds())){
			tsNginxProxy.setParentIds(","+tsNginxProxy.getParentIds()+",");
		}
		return super.findList(tsNginxProxy);
	}
	
	@Transactional(readOnly = false)
	public void save(TsNginxProxy tsNginxProxy) {
		super.save(tsNginxProxy);
	}
	
	@Transactional(readOnly = false)
	public void delete(TsNginxProxy tsNginxProxy) {
		super.delete(tsNginxProxy);
	}
	
	
	@Transactional(readOnly = false)
	public void saveAndSync(TsNginxProxy tsNginxProxy) {
		save(tsNginxProxy);
		if(tsNginxProxy.isUse()){
			sync(tsNginxProxy);
		}
	}
	
	public void syncAll(){
		TsNginxProxy _tsNginxProxy = new TsNginxProxy();
		_tsNginxProxy.setIsUse("1");
		List<TsNginxProxy> tsNginxProxies = this.findList(_tsNginxProxy);
		if(tsNginxProxies!=null&&tsNginxProxies.size()>0){
			for(TsNginxProxy tsNginxProxy:tsNginxProxies){
				try {
					sync(tsNginxProxy);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void sync(TsNginxProxy tsNginxProxy){
		if(StringUtils.isBlank(tsNginxProxy.getServerId())){
			return;
		}
		TsServer tsServer = tsServerDao.get(tsNginxProxy.getServerId());
		RedisConfig redisConfig = new RedisConfig();
		redisConfig.setUrl(tsServer.getServerIp());
		redisConfig.setPort(Integer.valueOf(tsServer.getServerPort()));
		RedisUtil.hset("TsNginxProxy", tsNginxProxy.getProxyUrl(), tsNginxProxy.toObjectMapStr(), 0, redisConfig);
	}
	
}