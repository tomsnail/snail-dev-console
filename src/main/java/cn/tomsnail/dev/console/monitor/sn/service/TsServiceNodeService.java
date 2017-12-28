/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceNode;
import cn.tomsnail.dev.console.monitor.sn.dao.TsServiceNodeDao;

/**
 * 服务节点Service
 * @author yangsong
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class TsServiceNodeService extends CrudService<TsServiceNodeDao, TsServiceNode> {

	public TsServiceNode get(String id) {
		return super.get(id);
	}
	
	public List<TsServiceNode> findList(TsServiceNode tsServiceNode) {
		return super.findList(tsServiceNode);
	}
	
	public Page<TsServiceNode> findPage(Page<TsServiceNode> page, TsServiceNode tsServiceNode) {
		return super.findPage(page, tsServiceNode);
	}
	
	@Transactional(readOnly = false)
	public void save(TsServiceNode tsServiceNode) {
		super.save(tsServiceNode);
	}
	
	@Transactional(readOnly = false)
	public void delete(TsServiceNode tsServiceNode) {
		super.delete(tsServiceNode);
	}
	
}