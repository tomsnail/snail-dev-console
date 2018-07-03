/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 任务执行跟踪Entity
 * @author yangsong
 * @version 2018-07-03
 */
public class JobStatusTraceLog extends DataEntity<JobStatusTraceLog> {
	
	private static final long serialVersionUID = 1L;
	private String jobName;		// 作业名称
	private String originalTaskId;		// 原始任务ID
	private String taskId;		// 任务ID
	private String slaveId;		// SLAVE
	private String source;		// 源
	private String executionType;		// 执行类型
	private String shardingItem;		// 分片序号
	private String state;		// 状态
	private String message;		// 执行信息
	private Date creationTime;		// 完成时间
	
	public JobStatusTraceLog() {
		super();
	}

	public JobStatusTraceLog(String id){
		super(id);
	}

	@Length(min=1, max=100, message="作业名称长度必须介于 1 和 100 之间")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Length(min=1, max=255, message="原始任务ID长度必须介于 1 和 255 之间")
	public String getOriginalTaskId() {
		return originalTaskId;
	}

	public void setOriginalTaskId(String originalTaskId) {
		this.originalTaskId = originalTaskId;
	}
	
	@Length(min=1, max=255, message="任务ID长度必须介于 1 和 255 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=1, max=50, message="SLAVE长度必须介于 1 和 50 之间")
	public String getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(String slaveId) {
		this.slaveId = slaveId;
	}
	
	@Length(min=1, max=50, message="源长度必须介于 1 和 50 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=1, max=20, message="执行类型长度必须介于 1 和 20 之间")
	public String getExecutionType() {
		return executionType;
	}

	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	
	@Length(min=1, max=100, message="分片序号长度必须介于 1 和 100 之间")
	public String getShardingItem() {
		return shardingItem;
	}

	public void setShardingItem(String shardingItem) {
		this.shardingItem = shardingItem;
	}
	
	@Length(min=1, max=20, message="状态长度必须介于 1 和 20 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=4000, message="执行信息长度必须介于 0 和 4000 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
}