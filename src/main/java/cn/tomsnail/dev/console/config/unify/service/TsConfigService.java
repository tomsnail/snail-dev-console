/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.unify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.tomsnail.dev.console.config.server.dao.TsServerDao;
import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.config.unify.entity.TsConfig;
import cn.tomsnail.dev.console.config.unify.dao.TsConfigDao;
import cn.tomsnail.dev.console.util.RedisConfig;
import cn.tomsnail.dev.console.util.RedisUtil;

/**
 * 统一配置Service
 * @author yangsong
 * @version 2017-12-17
 */
@Service
@Transactional(readOnly = true)
public class TsConfigService extends CrudService<TsConfigDao, TsConfig> {
	
	@Autowired
	private TsServerDao tsServerDao;

	public TsConfig get(String id) {
		return super.get(id);
	}
	
	public List<TsConfig> findList(TsConfig tsConfig) {
		return super.findList(tsConfig);
	}
	
	public Page<TsConfig> findPage(Page<TsConfig> page, TsConfig tsConfig) {
		return super.findPage(page, tsConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(TsConfig tsConfig) {
		super.save(tsConfig);
	}
	
	@Transactional(readOnly = false)
	public void saveAndSync(TsConfig tsConfig) {
		save(tsConfig);
		sync(tsConfig);
	}
	
	public void syncAll(){
		List<TsConfig> tsConfigs = this.findList(new TsConfig());
		if(tsConfigs!=null&&tsConfigs.size()>0){
			for(TsConfig tsConfig:tsConfigs){
				try {
					sync(tsConfig);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void sync(TsConfig tsConfig){
		if(StringUtils.isBlank(tsConfig.getServerId())){
			return;
		}
		TsServer tsServer = tsServerDao.get(tsConfig.getServerId());
		if(tsServer!=null&&tsConfig.getSyncSystem().equalsIgnoreCase("redis")){
			RedisConfig redisConfig = new RedisConfig();
			redisConfig.setUrl(tsServer.getServerIp());
			redisConfig.setPort(Integer.valueOf(tsServer.getServerPort()));
			RedisUtil.set(tsConfig.getKey(), tsConfig.getValue(), redisConfig);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TsConfig tsConfig) {
		super.delete(tsConfig);
	}
	
}