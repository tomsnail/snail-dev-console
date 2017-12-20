/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.server.web;

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
import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.config.server.service.TsServerService;

/**
 * 服务器配置Controller
 * @author yangsong
 * @version 2017-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/server/tsServer")
public class TsServerController extends BaseController {

	@Autowired
	private TsServerService tsServerService;
	
	@ModelAttribute
	public TsServer get(@RequestParam(required=false) String id) {
		TsServer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsServerService.get(id);
		}
		if (entity == null){
			entity = new TsServer();
		}
		return entity;
	}
	
	@RequiresPermissions("server:tsServer:view")
	@RequestMapping(value = {"list", ""})
	public String list(TsServer tsServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TsServer> list = tsServerService.findList(tsServer); 
		model.addAttribute("list", list);
		return "config/server/tsServerList";
	}

	@RequiresPermissions("server:tsServer:view")
	@RequestMapping(value = "form")
	public String form(TsServer tsServer, Model model) {
		if (tsServer.getParent()!=null && StringUtils.isNotBlank(tsServer.getParent().getId())){
			tsServer.setParent(tsServerService.get(tsServer.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(tsServer.getId())){
				TsServer tsServerChild = new TsServer();
				tsServerChild.setParent(new TsServer(tsServer.getParent().getId()));
				List<TsServer> list = tsServerService.findList(tsServer); 
				if (list.size() > 0){
					tsServer.setSort(list.get(list.size()-1).getSort());
					if (tsServer.getSort() != null){
						tsServer.setSort(tsServer.getSort() + 30);
					}
				}
			}
		}
		if (tsServer.getSort() == null){
			tsServer.setSort(30);
		}
		model.addAttribute("tsServer", tsServer);
		return "config/server/tsServerForm";
	}

	@RequiresPermissions("server:tsServer:edit")
	@RequestMapping(value = "save")
	public String save(TsServer tsServer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsServer)){
			return form(tsServer, model);
		}
		tsServerService.save(tsServer);
		addMessage(redirectAttributes, "保存服务器配置成功");
		return "redirect:"+Global.getAdminPath()+"/server/tsServer/?repage";
	}
	
	@RequiresPermissions("server:tsServer:edit")
	@RequestMapping(value = "delete")
	public String delete(TsServer tsServer, RedirectAttributes redirectAttributes) {
		tsServerService.delete(tsServer);
		addMessage(redirectAttributes, "删除服务器配置成功");
		return "redirect:"+Global.getAdminPath()+"/server/tsServer/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TsServer> list = tsServerService.findList(new TsServer());
		for (int i=0; i<list.size(); i++){
			TsServer e = list.get(i);
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