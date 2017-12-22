/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.np.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.tomsnail.dev.console.monitor.np.entity.TsNginxProxy;

/**
 * Nginx代理DAO接口
 * @author yangsong
 * @version 2017-12-22
 */
@MyBatisDao
public interface TsNginxProxyDao extends TreeDao<TsNginxProxy> {
	
}