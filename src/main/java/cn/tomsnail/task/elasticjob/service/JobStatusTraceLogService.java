/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.tomsnail.task.elasticjob.entity.JobStatusTraceLog;
import cn.tomsnail.task.elasticjob.dao.JobStatusTraceLogDao;

/**
 * 任务执行跟踪Service
 * @author yangsong
 * @version 2018-07-03
 */
@Service
@Transactional(readOnly = true)
public class JobStatusTraceLogService extends CrudService<JobStatusTraceLogDao, JobStatusTraceLog> {

	public JobStatusTraceLog get(String id) {
		return super.get(id);
	}
	
	public List<JobStatusTraceLog> findList(JobStatusTraceLog jobStatusTraceLog) {
		return super.findList(jobStatusTraceLog);
	}
	
	public Page<JobStatusTraceLog> findPage(Page<JobStatusTraceLog> page, JobStatusTraceLog jobStatusTraceLog) {
		return super.findPage(page, jobStatusTraceLog);
	}
	
	@Transactional(readOnly = false)
	public void save(JobStatusTraceLog jobStatusTraceLog) {
		super.save(jobStatusTraceLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(JobStatusTraceLog jobStatusTraceLog) {
		super.delete(jobStatusTraceLog);
	}
	
}