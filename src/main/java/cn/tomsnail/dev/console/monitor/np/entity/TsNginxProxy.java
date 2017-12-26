/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.np.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * Nginx代理Entity
 * @author yangsong
 * @version 2017-12-22
 */
public class TsNginxProxy extends TreeEntity<TsNginxProxy> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String sort;		// 排序
	private TsNginxProxy parent;		// 所属上级
	private String parentIds;		// 上级集合
	private String proxyUrl;		// 代理地址
	private String realUrl;		// 真实地址
	private String proxyType;		// 代理类型
	private String proxyMethod;
	private String isUse;		// 是否可用
	
	private String serverId;
	
	private String serverName;
	
	public TsNginxProxy() {
		super();
	}

	public TsNginxProxy(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@JsonBackReference
	public TsNginxProxy getParent() {
		return parent;
	}

	public void setParent(TsNginxProxy parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="上级集合长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=1000, message="代理地址长度必须介于 0 和 1000 之间")
	public String getProxyUrl() {
		return proxyUrl;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}
	
	@Length(min=0, max=1000, message="真实地址长度必须介于 0 和 1000 之间")
	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}
	
	@Length(min=1, max=64, message="代理类型长度必须介于 1 和 64 之间")
	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	
	@Length(min=1, max=1, message="是否可用长度必须介于 1 和 1 之间")
	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getProxyMethod() {
		return proxyMethod;
	}

	public void setProxyMethod(String proxyMethod) {
		this.proxyMethod = proxyMethod;
	}
	
	
	
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String toObjectMapStr(){
		Map<String,String> _map = new HashMap<String, String>();
		_map.put("proxyMethod", proxyMethod);
		_map.put("realUrl", realUrl);
		return JSONObject.toJSONString(_map);
	}
	
	public boolean isUse(){
		if(this.isUse == null || this.isUse.equals("0")){
			return false;
		}else{
			return true;
		}
	}
}