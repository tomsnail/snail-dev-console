/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 服务组Entity
 * @author yangsong
 * @version 2017-12-28
 */
public class TsServiceGroup extends TreeEntity<TsServiceGroup> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 服务名
	private String groupName;		// 组名
	private String nameKey;		// KEY
	private TsServiceGroup parent;		// 所属上级
	private String parentIds;		// parent_ids
	private String sort;		// 排序
	private String useNodeNumber = "0";
	
	public TsServiceGroup() {
		super();
	}

	public TsServiceGroup(String id){
		super(id);
	}

	@Length(min=0, max=255, message="服务名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="组名长度必须介于 0 和 255 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Length(min=0, max=255, message="KEY长度必须介于 0 和 255 之间")
	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}
	
	@JsonBackReference
	public TsServiceGroup getParent() {
		return parent;
	}

	public void setParent(TsServiceGroup parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getUseNodeNumber() {
		return useNodeNumber;
	}

	public void setUseNodeNumber(String useNodeNumber) {
		this.useNodeNumber = useNodeNumber;
	}
	
	
}