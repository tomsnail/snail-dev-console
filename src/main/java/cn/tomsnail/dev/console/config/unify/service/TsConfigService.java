/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.unify.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.tomsnail.dev.console.config.unify.entity.TsConfig;
import cn.tomsnail.dev.console.config.unify.dao.TsConfigDao;

/**
 * 统一配置Service
 * @author yangsong
 * @version 2017-12-17
 */
@Service
@Transactional(readOnly = true)
public class TsConfigService extends CrudService<TsConfigDao, TsConfig> {

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
	public void delete(TsConfig tsConfig) {
		super.delete(tsConfig);
	}
	
}