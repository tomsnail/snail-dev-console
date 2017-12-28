/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceGroup;

/**
 * 服务组DAO接口
 * @author yangsong
 * @version 2017-12-28
 */
@MyBatisDao
public interface TsServiceGroupDao extends TreeDao<TsServiceGroup> {
	
}