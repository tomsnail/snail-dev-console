/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.indexpage.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 首页配置Entity
 * @author yangsong
 * @version 2017-12-20
 */
public class JsIndex extends TreeEntity<JsIndex> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String icon;		// 图片链接
	private String url;		// 地址链接
	private String sort;		// 排序
	private String groupName;		// 组名
	private String orderInt;		// 排序
	private JsIndex parent;		// parent_id
	private String parentIds;		// 所属上级
	
	public JsIndex() {
		super();
	}

	public JsIndex(String id){
		super(id);
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="图片链接长度必须介于 0 和 255 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Length(min=0, max=255, message="地址链接长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	@Length(min=0, max=255, message="组名长度必须介于 0 和 255 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getOrderInt() {
		return orderInt;
	}

	public void setOrderInt(String orderInt) {
		this.orderInt = orderInt;
	}
	
	@JsonBackReference
	public JsIndex getParent() {
		return parent;
	}

	public void setParent(JsIndex parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="所属上级长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}