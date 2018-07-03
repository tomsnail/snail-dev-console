/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 作业列表Entity
 * @author yangsong
 * @version 2018-07-03
 */
public class JobInfo extends DataEntity<JobInfo> {
	
	private static final long serialVersionUID = 1L;
	private String jobName;		// 作业名称
	private String jobType;		// 作业类型
	private String jobClass;		// 执行类
	private String jobCrontab;		// 执行周期
	private String shardingTotalCount;		// 作业分片数
	private String jobParameter;		// 作业参数
	private String maxTimeDiffSeconds;		// 时间同步秒
	private String monitorPort;		// 监控端口
	private String reconcileIntervalMinutes;		// 重试周期
	private String monitorExecution;		// 监控
	private String failover;		// 失败切换
	private String misfire;		// 失败不启动
	private String shardingItemParams;		// 分片参数
	private String jobShardingStrategyClass;		// 分片策略类
	private String jobExceptionHandler;		// 异常处理类
	private String executorServiceHandler;		// 执行服务类
	private String jobDesc;		// 描述
	private String jobState;		// 状态
	private String beginJobName;		// 开始 作业名称
	private String endJobName;		// 结束 作业名称
	
	private String zookeeper;
	private String namespace;
	
	public JobInfo() {
		super();
	}

	public JobInfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="作业名称长度必须介于 0 和 255 之间")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Length(min=0, max=100, message="作业类型长度必须介于 0 和 100 之间")
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	@Length(min=0, max=500, message="执行类长度必须介于 0 和 500 之间")
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	
	@Length(min=0, max=100, message="执行周期长度必须介于 0 和 100 之间")
	public String getJobCrontab() {
		return jobCrontab;
	}

	public void setJobCrontab(String jobCrontab) {
		this.jobCrontab = jobCrontab;
	}
	
	@Length(min=0, max=10, message="作业分片数长度必须介于 0 和 10 之间")
	public String getShardingTotalCount() {
		return shardingTotalCount;
	}

	public void setShardingTotalCount(String shardingTotalCount) {
		this.shardingTotalCount = shardingTotalCount;
	}
	
	@Length(min=0, max=500, message="作业参数长度必须介于 0 和 500 之间")
	public String getJobParameter() {
		return jobParameter;
	}

	public void setJobParameter(String jobParameter) {
		this.jobParameter = jobParameter;
	}
	
	@Length(min=0, max=10, message="时间同步秒长度必须介于 0 和 10 之间")
	public String getMaxTimeDiffSeconds() {
		return maxTimeDiffSeconds;
	}

	public void setMaxTimeDiffSeconds(String maxTimeDiffSeconds) {
		this.maxTimeDiffSeconds = maxTimeDiffSeconds;
	}
	
	@Length(min=0, max=10, message="监控端口长度必须介于 0 和 10 之间")
	public String getMonitorPort() {
		return monitorPort;
	}

	public void setMonitorPort(String monitorPort) {
		this.monitorPort = monitorPort;
	}
	
	@Length(min=0, max=10, message="重试周期长度必须介于 0 和 10 之间")
	public String getReconcileIntervalMinutes() {
		return reconcileIntervalMinutes;
	}

	public void setReconcileIntervalMinutes(String reconcileIntervalMinutes) {
		this.reconcileIntervalMinutes = reconcileIntervalMinutes;
	}
	
	@Length(min=0, max=1, message="监控长度必须介于 0 和 1 之间")
	public String getMonitorExecution() {
		return monitorExecution;
	}

	public void setMonitorExecution(String monitorExecution) {
		this.monitorExecution = monitorExecution;
	}
	
	@Length(min=0, max=1, message="失败切换长度必须介于 0 和 1 之间")
	public String getFailover() {
		return failover;
	}

	public void setFailover(String failover) {
		this.failover = failover;
	}
	
	@Length(min=0, max=1, message="失败不启动长度必须介于 0 和 1 之间")
	public String getMisfire() {
		return misfire;
	}

	public void setMisfire(String misfire) {
		this.misfire = misfire;
	}
	
	@Length(min=0, max=500, message="分片参数长度必须介于 0 和 500 之间")
	public String getShardingItemParams() {
		return shardingItemParams;
	}

	public void setShardingItemParams(String shardingItemParams) {
		this.shardingItemParams = shardingItemParams;
	}
	
	@Length(min=0, max=500, message="分片策略类长度必须介于 0 和 500 之间")
	public String getJobShardingStrategyClass() {
		return jobShardingStrategyClass;
	}

	public void setJobShardingStrategyClass(String jobShardingStrategyClass) {
		this.jobShardingStrategyClass = jobShardingStrategyClass;
	}
	
	@Length(min=0, max=500, message="异常处理类长度必须介于 0 和 500 之间")
	public String getJobExceptionHandler() {
		return jobExceptionHandler;
	}

	public void setJobExceptionHandler(String jobExceptionHandler) {
		this.jobExceptionHandler = jobExceptionHandler;
	}
	
	@Length(min=0, max=500, message="执行服务类长度必须介于 0 和 500 之间")
	public String getExecutorServiceHandler() {
		return executorServiceHandler;
	}

	public void setExecutorServiceHandler(String executorServiceHandler) {
		this.executorServiceHandler = executorServiceHandler;
	}
	
	@Length(min=0, max=1000, message="描述长度必须介于 0 和 1000 之间")
	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	
	@Length(min=0, max=100, message="状态长度必须介于 0 和 100 之间")
	public String getJobState() {
		return jobState;
	}

	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	
	public String getBeginJobName() {
		return beginJobName;
	}

	public void setBeginJobName(String beginJobName) {
		this.beginJobName = beginJobName;
	}
	
	public String getEndJobName() {
		return endJobName;
	}

	public void setEndJobName(String endJobName) {
		this.endJobName = endJobName;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(String zookeeper) {
		this.zookeeper = zookeeper;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
		
}