/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.ddframe.job.lite.lifecycle.domain.JobSettings;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

import cn.tomsnail.task.elasticjob.entity.JobInfo;
import cn.tomsnail.task.elasticjob.dao.JobInfoDao;

/**
 * 作业列表Service
 * @author yangsong
 * @version 2018-07-03
 */
@Service
@Transactional(readOnly = true)
public class JobInfoService extends CrudService<JobInfoDao, JobInfo> {
	
	
	@Autowired
	JobOperateService jobOperateService;

	public JobInfo get(String id) {
		
		
		
		return super.get(id);
	}
	
	public List<JobInfo> findList(JobInfo jobInfo) {
		return super.findList(jobInfo);
	}
	
	public Page<JobInfo> findPage(Page<JobInfo> page, JobInfo jobInfo) {
		return super.findPage(page, jobInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(JobInfo jobInfo) {
		super.save(jobInfo);
	}
	
	@Transactional(readOnly = false)
	public void updateAllJob(JobInfo jobInfo) {
		
		List<JobInfo> infos = super.findList(jobInfo);
		if(infos!=null&&infos.size()>0){
			for(JobInfo info:infos){
				delete(info);
			}
			infos.clear();
			infos = null;
		}
		
		infos = jobOperateService.getAllJobs(jobInfo.getZookeeper(), jobInfo.getNamespace());
		
		if(infos!=null&&infos.size()>0){
			for(JobInfo info:infos){
				
				info.setZookeeper(jobInfo.getZookeeper());
				info.setNamespace(jobInfo.getNamespace());
				
				JobSettings jobSettings = jobOperateService.getSetting(info);
				info.setFailover(getFlag(jobSettings.isFailover()));
				info.setJobClass(jobSettings.getJobClass());
				info.setJobDesc(jobSettings.getDescription());
				info.setJobParameter(jobSettings.getJobParameter());
				info.setJobShardingStrategyClass(jobSettings.getJobShardingStrategyClass());
				info.setJobType(jobSettings.getJobType());
				info.setMaxTimeDiffSeconds(jobSettings.getMaxTimeDiffSeconds()+"");
				info.setMisfire(getFlag(jobSettings.isMisfire()));
				info.setMonitorPort(jobSettings.getMonitorPort()+"");
				info.setReconcileIntervalMinutes(jobSettings.getReconcileIntervalMinutes()+"");
				info.setShardingTotalCount(jobSettings.getShardingTotalCount()+"");
				info.setMonitorExecution(getFlag(jobSettings.isMonitorExecution()));
				
				super.save(info);
			}
		}
		
		
	}
	
	private String getFlag(boolean flag){
		if(flag){
			return "1";
		}else{
			return "0";
		}
	}
	
	
	public void disable(JobInfo jobInfo){
		jobOperateService.disable(jobInfo);
	}
	
	public void trigger(JobInfo jobInfo){
		jobOperateService.trigger(jobInfo);
	}


	public void shutdown(JobInfo jobInfo){
		jobOperateService.shutdown(jobInfo);
	}
	
	public void enable(JobInfo jobInfo){
		jobOperateService.enable(jobInfo);
	}
	
	public void remove(JobInfo jobInfo){
		jobOperateService.remove(jobInfo);
	}
	
	public void updateSetting(JobInfo jobInfo){
		jobOperateService.updateSetting(jobInfo);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(JobInfo jobInfo) {
		super.delete(jobInfo);
	}
	
	
	
}