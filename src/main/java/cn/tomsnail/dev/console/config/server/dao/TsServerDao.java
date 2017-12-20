/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.server.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.tomsnail.dev.console.config.server.entity.TsServer;

/**
 * 服务器配置DAO接口
 * @author yangsong
 * @version 2017-12-20
 */
@MyBatisDao
public interface TsServerDao extends TreeDao<TsServer> {
	
}