/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.server.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 服务器配置Entity
 * @author yangsong
 * @version 2017-12-20
 */
public class TsServer extends TreeEntity<TsServer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 服务器名称
	private TsServer parent;		// parent_id
	private String serverIp;		// IP地址
	private String parentIds;		// parent_ids
	private String serverPort;		// 端口
	private String serverUrl;		// URL
	private String serverUsername;		// 用户名
	private String serverPassword;		// 密码
	private String serverBaseDir;		// 基础目录
	private String serverType;		// 服务器类型
	
	private String isMaster;
	
	private String serviceType;
	
	public TsServer() {
		super();
	}

	public TsServer(String id){
		super(id);
	}

	@Length(min=1, max=255, message="服务器名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonBackReference
	public TsServer getParent() {
		return parent;
	}

	public void setParent(TsServer parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=255, message="IP地址长度必须介于 1 和 255 之间")
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=255, message="端口长度必须介于 1 和 255 之间")
	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	
	
	
	@Length(min=0, max=255, message="URL长度必须介于 0 和 255 之间")
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getServerUsername() {
		return serverUsername;
	}

	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}
	
	@Length(min=0, max=255, message="密码长度必须介于 0 和 255 之间")
	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}
	
	@Length(min=0, max=255, message="基础目录长度必须介于 0 和 255 之间")
	public String getServerBaseDir() {
		return serverBaseDir;
	}

	public void setServerBaseDir(String serverBaseDir) {
		this.serverBaseDir = serverBaseDir;
	}
	
	@Length(min=1, max=255, message="服务器类型长度必须介于 1 和 255 之间")
	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
}