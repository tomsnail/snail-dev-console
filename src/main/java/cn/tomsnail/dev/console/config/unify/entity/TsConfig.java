/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.unify.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 统一配置Entity
 * @author yangsong
 * @version 2017-12-17
 */
public class TsConfig extends DataEntity<TsConfig> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String label;		// 标签
	private String key;		// 关键字
	private String value;		// 值
	private String type;		// 类型
	private TsConfig parent;		// parent_id
	private String parentIds;		// parent_ids
	private String service;		// 服务
	private String module;		// 模块
	private String project;		// 工程
	private String system;		// 系统
	private String syncMethod;		// 同步方法
	private String syncSystem;		// 同步系统
	private String syncDate;		// 同步时间
	
	public TsConfig() {
		super();
	}

	public TsConfig(String id){
		super(id);
	}

	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="标签长度必须介于 0 和 255 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=0, max=255, message="关键字长度必须介于 0 和 255 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Length(min=0, max=2000, message="值长度必须介于 0 和 2000 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=255, message="类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonBackReference
	public TsConfig getParent() {
		return parent;
	}

	public void setParent(TsConfig parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=1000, message="parent_ids长度必须介于 0 和 1000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=255, message="服务长度必须介于 0 和 255 之间")
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
	@Length(min=0, max=255, message="模块长度必须介于 0 和 255 之间")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	@Length(min=0, max=255, message="工程长度必须介于 0 和 255 之间")
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	@Length(min=0, max=255, message="系统长度必须介于 0 和 255 之间")
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	@Length(min=0, max=255, message="同步方法长度必须介于 0 和 255 之间")
	public String getSyncMethod() {
		return syncMethod;
	}

	public void setSyncMethod(String syncMethod) {
		this.syncMethod = syncMethod;
	}
	
	@Length(min=0, max=255, message="同步系统长度必须介于 0 和 255 之间")
	public String getSyncSystem() {
		return syncSystem;
	}

	public void setSyncSystem(String syncSystem) {
		this.syncSystem = syncSystem;
	}
	
	@Length(min=0, max=255, message="同步时间长度必须介于 0 和 255 之间")
	public String getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}
	
}