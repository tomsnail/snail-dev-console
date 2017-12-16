/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.indexpage.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 首页配置Entity
 * @author yangsong
 * @version 2017-12-14
 */
public class JsIndex extends DataEntity<JsIndex> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String icon;		// 图片链接
	private String url;		// 地址链接
	private String sort;		// 排序
	private String groupName;		// 说明
	private String orderInt;		// 分组
	
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
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="说明长度必须介于 0 和 255 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOrderInt() {
		return orderInt;
	}

	public void setOrderInt(String orderInt) {
		this.orderInt = orderInt;
	}
	
	
	
}