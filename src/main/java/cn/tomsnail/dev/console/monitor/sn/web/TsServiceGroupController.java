/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zkjd.ehua.system.conf.entity.VisitAddress;
import com.zkjd.ehua.system.conf.service.VisitAddressService;

import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceGroup;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceNode;
import cn.tomsnail.dev.console.monitor.sn.service.TsServiceGroupService;
import cn.tomsnail.dev.console.monitor.sn.service.TsServiceNodeService;

/**
 * 服务组Controller
 * @author yangsong
 * @version 2017-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sn/tsServiceGroup")
public class TsServiceGroupController extends BaseController {

	@Autowired
	private TsServiceGroupService tsServiceGroupService;
	
	@Autowired
	private TsServiceNodeService tsServiceNodeService;
	
	
	@Autowired
	private VisitAddressService visitAddressService;
	
	
	@ModelAttribute
	public TsServiceGroup get(@RequestParam(required=false) String id) {
		TsServiceGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsServiceGroupService.get(id);
		}
		if (entity == null){
			entity = new TsServiceGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("sn:tsServiceGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(TsServiceGroup tsServiceGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TsServiceGroup> list = tsServiceGroupService.findList(tsServiceGroup); 
		if(list!=null){
			for(TsServiceGroup serviceGroup:list){
				TsServiceNode tsServiceNode = new TsServiceNode();
				tsServiceNode.setServiceId(serviceGroup.getId());
				tsServiceNode.setNodeStatus("0");
				List<TsServiceNode> nodes = tsServiceNodeService.findList(tsServiceNode);
				if(nodes!=null&&nodes.size()>0){
					serviceGroup.setUseNodeNumber(nodes.size()+"");
				}
			}
		}
		model.addAttribute("list", list);
		return "monitor/sn/tsServiceGroupList";
	}

	@RequiresPermissions("sn:tsServiceGroup:view")
	@RequestMapping(value = "form")
	public String form(TsServiceGroup tsServiceGroup, Model model) {
		if (tsServiceGroup.getParent()!=null && StringUtils.isNotBlank(tsServiceGroup.getParent().getId())){
			tsServiceGroup.setParent(tsServiceGroupService.get(tsServiceGroup.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(tsServiceGroup.getId())){
				TsServiceGroup tsServiceGroupChild = new TsServiceGroup();
				tsServiceGroupChild.setParent(new TsServiceGroup(tsServiceGroup.getParent().getId()));
				List<TsServiceGroup> list = tsServiceGroupService.findList(tsServiceGroup); 
				if (list.size() > 0){
					tsServiceGroup.setSort(list.get(list.size()-1).getSort());
					if (tsServiceGroup.getSort() != null){
						tsServiceGroup.setSort(tsServiceGroup.getSort() + 30);
					}
				}
			}
		}
		if (tsServiceGroup.getSort() == null){
			tsServiceGroup.setSort(30);
		}
		TsServiceNode tsServiceNode = new TsServiceNode();
		tsServiceNode.setServiceId(tsServiceGroup.getId());
		List<TsServiceNode> nodes = tsServiceNodeService.findList(tsServiceNode);
		model.addAttribute("nodeList", nodes);
		model.addAttribute("tsServiceGroup", tsServiceGroup);//nodeList
		return "monitor/sn/tsServiceGroupForm";
	}

	@RequiresPermissions("sn:tsServiceGroup:edit")
	@RequestMapping(value = "save")
	public String save(TsServiceGroup tsServiceGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsServiceGroup)){
			return form(tsServiceGroup, model);
		}
		tsServiceGroupService.save(tsServiceGroup);
		addMessage(redirectAttributes, "保存服务组成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
	}
	
	@RequiresPermissions("sn:tsServiceGroup:edit")
	@RequestMapping(value = "syncAll")
	public String syncAll(TsServiceGroup tsServiceGroup, Model model, RedirectAttributes redirectAttributes) {
		tsServiceGroupService.syncAll();
		addMessage(redirectAttributes, "立即更新成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
	}
	
	@RequiresPermissions("sn:tsServiceGroup:edit")
	@RequestMapping(value = "addUS")
	public String addUS(TsServiceGroup tsServiceGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsServiceGroup)){
			return form(tsServiceGroup, model);
		}
		
		TsServiceNode tsServiceNode = new TsServiceNode();
		tsServiceNode.setServiceId(tsServiceGroup.getId());
		tsServiceNode.setNodeStatus("0");
		List<TsServiceNode> nodes = tsServiceNodeService.findList(tsServiceNode);
		
		if(nodes==null||nodes.size()<=0){
			addMessage(redirectAttributes, "添加失败");
			return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
		}
		String t = nodes.get(0).getAddressUrl().replace("http://", "");
		String _t = t.split("/")[0];
		t = nodes.get(0).getAddressUrl().replace("http://"+_t+"/", "");
		
		StringBuffer urlSB = new StringBuffer();
		for(TsServiceNode node:nodes){
			urlSB.append(node.getAddressUrl().replace(t, "")).append(";");
		}
		urlSB.append("0");
		String _url = urlSB.toString().replace(";0", "");
		
		VisitAddress visitAddress = new VisitAddress();
		visitAddress.setVisitAddr(t);
		List<VisitAddress> addresses = visitAddressService.findList(visitAddress);
		
		if(addresses!=null&&addresses.size()==1){
			visitAddress = addresses.get(0);
			visitAddress.setRealAddress(_url);
			visitAddress.setTestAddress(_url);
			visitAddress.setComAddress(_url);
			visitAddressService.update(visitAddress);
		}else if(addresses!=null&&addresses.size()>1){
			addMessage(redirectAttributes, "添加失败");
			return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
		}else{
			visitAddress = new VisitAddress();
			visitAddress.setAccessLimitType("103300");
			visitAddress.setAccessLimitValue("1");
			visitAddress.setAddressType("202102");
			visitAddress.setAuthor("");
			visitAddress.setComAddress(_url);
			visitAddress.setDegradeContext("{'command':'nullResp','sequenceID':'','fingerprint':'','body':{},'status':'901','msg':'系统繁忙,请稍后再试','code':'','msgCode':''}");
			visitAddress.setGrpgName("");
			visitAddress.setHttpType("POST");
			visitAddress.setIsAddUser("1");
			visitAddress.setIsAddVer("0");
			visitAddress.setIsAuth("1");
			visitAddress.setIsDefVer("1");
			visitAddress.setIsDegrade("0");
			visitAddress.setIsInner("0");
			visitAddress.setIsLogger("0");
			visitAddress.setIsRelease("0");
			visitAddress.setIsSign("1");
			visitAddress.setLogLevel("1");
			visitAddress.setRealAddress(_url);
			visitAddress.setTestAddress(_url);
			visitAddress.setVerInfo("v0.1");
			visitAddress.setVisitAddr(t);
			visitAddressService.save(visitAddress);
		}
		
		
		
		
		
		addMessage(redirectAttributes, "添加成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
	}
	
	@RequiresPermissions("sn:tsServiceGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(TsServiceGroup tsServiceGroup, RedirectAttributes redirectAttributes) {
		tsServiceGroupService.delete(tsServiceGroup);
		addMessage(redirectAttributes, "删除服务组成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceGroup/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TsServiceGroup> list = tsServiceGroupService.findList(new TsServiceGroup());
		for (int i=0; i<list.size(); i++){
			TsServiceGroup e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}