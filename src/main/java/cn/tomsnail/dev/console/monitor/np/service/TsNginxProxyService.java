/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.np.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.dev.console.monitor.np.entity.TsNginxProxy;
import cn.tomsnail.dev.console.monitor.np.dao.TsNginxProxyDao;

/**
 * Nginx代理Service
 * @author yangsong
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class TsNginxProxyService extends TreeService<TsNginxProxyDao, TsNginxProxy> {

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
	
}