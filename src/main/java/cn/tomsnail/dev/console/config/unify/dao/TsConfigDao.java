/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.unify.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.tomsnail.dev.console.config.unify.entity.TsConfig;

/**
 * 统一配置DAO接口
 * @author yangsong
 * @version 2017-12-17
 */
@MyBatisDao
public interface TsConfigDao extends CrudDao<TsConfig> {
	
}