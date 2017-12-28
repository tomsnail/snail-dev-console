/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceGroup;
import cn.tomsnail.dev.console.monitor.sn.dao.TsServiceGroupDao;

/**
 * 服务组Service
 * @author yangsong
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class TsServiceGroupService extends TreeService<TsServiceGroupDao, TsServiceGroup> {
	
	@Autowired
	ServiceGroupMoniService serviceGroupMoniService;

	public TsServiceGroup get(String id) {
		return super.get(id);
	}
	
	public List<TsServiceGroup> findList(TsServiceGroup tsServiceGroup) {
		if (StringUtils.isNotBlank(tsServiceGroup.getParentIds())){
			tsServiceGroup.setParentIds(","+tsServiceGroup.getParentIds()+",");
		}
		return super.findList(tsServiceGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(TsServiceGroup tsServiceGroup) {
		super.save(tsServiceGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(TsServiceGroup tsServiceGroup) {
		super.delete(tsServiceGroup);
	}
	
	@Transactional(readOnly = false)
	public void syncAll(){
		serviceGroupMoniService.init();
	}
	
}