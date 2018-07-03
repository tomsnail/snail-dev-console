/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.tomsnail.task.elasticjob.entity.JobExecutionLog;
import cn.tomsnail.task.elasticjob.dao.JobExecutionLogDao;

/**
 * 任务执行日志Service
 * @author yangsong
 * @version 2018-07-03
 */
@Service
@Transactional(readOnly = true)
public class JobExecutionLogService extends CrudService<JobExecutionLogDao, JobExecutionLog> {

	public JobExecutionLog get(String id) {
		return super.get(id);
	}
	
	public List<JobExecutionLog> findList(JobExecutionLog jobExecutionLog) {
		return super.findList(jobExecutionLog);
	}
	
	public Page<JobExecutionLog> findPage(Page<JobExecutionLog> page, JobExecutionLog jobExecutionLog) {
		return super.findPage(page, jobExecutionLog);
	}
	
	@Transactional(readOnly = false)
	public void save(JobExecutionLog jobExecutionLog) {
		super.save(jobExecutionLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(JobExecutionLog jobExecutionLog) {
		super.delete(jobExecutionLog);
	}
	
}