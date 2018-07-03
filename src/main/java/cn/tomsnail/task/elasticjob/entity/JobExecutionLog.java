/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 任务执行日志Entity
 * @author yangsong
 * @version 2018-07-03
 */
public class JobExecutionLog extends DataEntity<JobExecutionLog> {
	
	private static final long serialVersionUID = 1L;
	private String jobName;		// 作业名称
	private String taskId;		// 任务ID
	private String hostname;		// 主机
	private String ip;		// IP地址
	private String shardingItem;		// 分片序列号
	private String executionSource;		// 执行源
	private String executionMessage;		// 执行信息
	private String failureCause;		// 失败原因
	private String isSuccess;		// 是否成功
	private Date startTime;		// 开始时间
	private Date completeTime;		// 完成时间
	
	public JobExecutionLog() {
		super();
	}

	public JobExecutionLog(String id){
		super(id);
	}

	@Length(min=1, max=100, message="作业名称长度必须介于 1 和 100 之间")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Length(min=1, max=255, message="任务ID长度必须介于 1 和 255 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=1, max=255, message="主机长度必须介于 1 和 255 之间")
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	@Length(min=1, max=50, message="IP地址长度必须介于 1 和 50 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=1, max=11, message="分片序列号长度必须介于 1 和 11 之间")
	public String getShardingItem() {
		return shardingItem;
	}

	public void setShardingItem(String shardingItem) {
		this.shardingItem = shardingItem;
	}
	
	@Length(min=1, max=20, message="执行源长度必须介于 1 和 20 之间")
	public String getExecutionSource() {
		return executionSource;
	}

	public void setExecutionSource(String executionSource) {
		this.executionSource = executionSource;
	}
	
	@Length(min=1, max=4000, message="执行信息长度必须介于 1 和 4000 之间")
	public String getExecutionMessage() {
		return executionMessage;
	}

	public void setExecutionMessage(String executionMessage) {
		this.executionMessage = executionMessage;
	}
	
	@Length(min=0, max=4000, message="失败原因长度必须介于 0 和 4000 之间")
	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	
	@Length(min=1, max=11, message="是否成功长度必须介于 1 和 11 之间")
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
}