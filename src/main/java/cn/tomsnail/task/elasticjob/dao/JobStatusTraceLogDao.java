/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.tomsnail.task.elasticjob.entity.JobStatusTraceLog;

/**
 * 任务执行跟踪DAO接口
 * @author yangsong
 * @version 2018-07-03
 */
@MyBatisDao
public interface JobStatusTraceLogDao extends CrudDao<JobStatusTraceLog> {
	
}