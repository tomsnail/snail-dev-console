/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 服务节点Entity
 * @author yangsong
 * @version 2017-12-28
 */
public class TsServiceNode extends DataEntity<TsServiceNode> {
	
	private static final long serialVersionUID = 1L;
	private String serviceId;		// 服务组
	private String module;		// 模块
	private String addressUrl;		// 地址
	private String nodeStatus;		// 节点状态
	private String weight;		// 权重
	private String retryCount;		// 重试次数
	private String failType;		// 失败策略
	private String lbType;		// 负载策略
	private String timeout;		// 超时时间
	private String version;		// 版本
	
	public TsServiceNode() {
		super();
	}

	public TsServiceNode(String id){
		super(id);
	}

	@Length(min=0, max=64, message="服务组长度必须介于 0 和 64 之间")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	@Length(min=0, max=500, message="模块长度必须介于 0 和 500 之间")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	@Length(min=0, max=1000, message="地址长度必须介于 0 和 1000 之间")
	public String getAddressUrl() {
		return addressUrl;
	}

	public void setAddressUrl(String addressUrl) {
		this.addressUrl = addressUrl;
	}
	
	@Length(min=0, max=2, message="节点状态长度必须介于 0 和 2 之间")
	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	
	@Length(min=0, max=10, message="权重长度必须介于 0 和 10 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=255, message="重试次数长度必须介于 0 和 255 之间")
	public String getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(String retryCount) {
		this.retryCount = retryCount;
	}
	
	@Length(min=0, max=255, message="失败策略长度必须介于 0 和 255 之间")
	public String getFailType() {
		return failType;
	}

	public void setFailType(String failType) {
		this.failType = failType;
	}
	
	@Length(min=0, max=255, message="负载策略长度必须介于 0 和 255 之间")
	public String getLbType() {
		return lbType;
	}

	public void setLbType(String lbType) {
		this.lbType = lbType;
	}
	
	@Length(min=0, max=255, message="超时时间长度必须介于 0 和 255 之间")
	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	@Length(min=0, max=255, message="版本长度必须介于 0 和 255 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}