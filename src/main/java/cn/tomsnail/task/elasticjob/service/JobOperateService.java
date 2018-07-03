package cn.tomsnail.task.elasticjob.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tomsnail.task.elasticjob.entity.JobInfo;

import com.dangdang.ddframe.job.lite.console.service.JobAPIService;
import com.dangdang.ddframe.job.lite.console.service.impl.JobAPIServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobSettingsAPI;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobSettings;
import com.google.common.base.Optional;

@Service
public class JobOperateService {

	
	
	
	public List<JobInfo> getAllJobs(String zookeeper,String namespace){
		
		RegistryCenterService.getRegistryCenterConfiguration(zookeeper, namespace);
		
		Collection<JobBriefInfo> briefInfos = JobAPIFactory.createJobStatisticsAPI(zookeeper, namespace, Optional.fromNullable("")).getAllJobsBriefInfo();;
		
		List<JobInfo> jobInfos = new ArrayList<JobInfo>();
		
		if(briefInfos!=null&&!briefInfos.isEmpty()){
			for(JobBriefInfo briefInfo:briefInfos){
				JobInfo info = new JobInfo();
				info.setJobName(briefInfo.getJobName());
				info.setJobDesc(briefInfo.getDescription());
				info.setJobCrontab(briefInfo.getCron());
				info.setShardingTotalCount(briefInfo.getShardingTotalCount()+"");
				info.setJobState(briefInfo.getStatus().toString());
				jobInfos.add(info);
			}
		}
		
		return jobInfos;
	}
	
	
	public void disable(JobInfo jobInfo){
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		jobOperateAPI.disable(Optional.of(jobInfo.getJobName()), Optional.<String>absent());
	}
	
	public void trigger(JobInfo jobInfo){
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		jobOperateAPI.trigger(Optional.of(jobInfo.getJobName()), Optional.<String>absent());
	}
	
	public void enable(JobInfo jobInfo){
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		jobOperateAPI.enable(Optional.of(jobInfo.getJobName()), Optional.<String>absent());
	}
	
	public void remove(JobInfo jobInfo){
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		jobOperateAPI.remove(Optional.of(jobInfo.getJobName()), Optional.<String>absent());
	}


	public void shutdown(JobInfo jobInfo){
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		jobOperateAPI.shutdown(Optional.of(jobInfo.getJobName()), Optional.<String>absent());
	}
	
	
	public JobSettings getSetting(JobInfo jobInfo){
		JobSettingsAPI jobSettingsAPI = JobAPIFactory.createJobSettingsAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		return jobSettingsAPI.getJobSettings(jobInfo.getJobName());
	}
	
	
	public void updateSetting(JobInfo jobInfo){
		JobSettingsAPI jobSettingsAPI = JobAPIFactory.createJobSettingsAPI(jobInfo.getZookeeper(), jobInfo.getNamespace(), Optional.fromNullable(""));
		
		JobSettings jobSettings = new JobSettings();
		jobSettings.setCron(jobInfo.getJobCrontab());
		jobSettings.setDescription(jobInfo.getJobDesc());
		jobSettings.setFailover("1".equals(jobInfo.getFailover()));
		jobSettings.setJobClass(jobInfo.getJobClass());
		jobSettings.setJobName(jobInfo.getJobName());
		jobSettings.setJobParameter(jobInfo.getJobParameter());
		jobSettings.setJobShardingStrategyClass(jobInfo.getJobShardingStrategyClass());
		jobSettings.setJobType(jobInfo.getJobType());
		jobSettings.setMaxTimeDiffSeconds(Integer.valueOf(jobInfo.getMaxTimeDiffSeconds()));
		jobSettings.setMisfire("1".equals(jobInfo.getMisfire()));
		jobSettings.setMonitorExecution("1".equals(jobInfo.getMonitorExecution()));
		jobSettings.setMonitorPort(Integer.valueOf(jobInfo.getMonitorPort()));
		jobSettings.setReconcileIntervalMinutes(Integer.valueOf(jobInfo.getReconcileIntervalMinutes()));
		jobSettings.setShardingItemParameters(jobInfo.getShardingItemParams());
		jobSettings.setShardingTotalCount(Integer.valueOf(jobInfo.getShardingTotalCount()));
		
		jobSettingsAPI.updateJobSettings(jobSettings);
	}

}
