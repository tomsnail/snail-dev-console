/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.np.web;

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
import cn.tomsnail.dev.console.monitor.np.entity.TsNginxProxy;
import cn.tomsnail.dev.console.monitor.np.service.TsNginxProxyService;

/**
 * Nginx代理Controller
 * @author yangsong
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/np/tsNginxProxy")
public class TsNginxProxyController extends BaseController {

	@Autowired
	private TsNginxProxyService tsNginxProxyService;
	
	@ModelAttribute
	public TsNginxProxy get(@RequestParam(required=false) String id) {
		TsNginxProxy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsNginxProxyService.get(id);
		}
		if (entity == null){
			entity = new TsNginxProxy();
		}
		return entity;
	}
	
	@RequiresPermissions("np:tsNginxProxy:view")
	@RequestMapping(value = {"list", ""})
	public String list(TsNginxProxy tsNginxProxy, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TsNginxProxy> list = tsNginxProxyService.findList(tsNginxProxy); 
		model.addAttribute("list", list);
		return "monitor/np/tsNginxProxyList";
	}

	@RequiresPermissions("np:tsNginxProxy:view")
	@RequestMapping(value = "form")
	public String form(TsNginxProxy tsNginxProxy, Model model) {
		if (tsNginxProxy.getParent()!=null && StringUtils.isNotBlank(tsNginxProxy.getParent().getId())){
			tsNginxProxy.setParent(tsNginxProxyService.get(tsNginxProxy.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(tsNginxProxy.getId())){
				TsNginxProxy tsNginxProxyChild = new TsNginxProxy();
				tsNginxProxyChild.setParent(new TsNginxProxy(tsNginxProxy.getParent().getId()));
				List<TsNginxProxy> list = tsNginxProxyService.findList(tsNginxProxy); 
				if (list.size() > 0){
					tsNginxProxy.setSort(list.get(list.size()-1).getSort());
					if (tsNginxProxy.getSort() != null){
						tsNginxProxy.setSort(tsNginxProxy.getSort() + 30);
					}
				}
			}
		}
		if (tsNginxProxy.getSort() == null){
			tsNginxProxy.setSort(30);
		}
		model.addAttribute("tsNginxProxy", tsNginxProxy);
		return "monitor/np/tsNginxProxyForm";
	}

	@RequiresPermissions("np:tsNginxProxy:edit")
	@RequestMapping(value = "save")
	public String save(TsNginxProxy tsNginxProxy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsNginxProxy)){
			return form(tsNginxProxy, model);
		}
		tsNginxProxyService.save(tsNginxProxy);
		addMessage(redirectAttributes, "保存Nginx代理成功");
		return "redirect:"+Global.getAdminPath()+"/np/tsNginxProxy/?repage";
	}
	
	@RequiresPermissions("np:tsNginxProxy:edit")
	@RequestMapping(value = "delete")
	public String delete(TsNginxProxy tsNginxProxy, RedirectAttributes redirectAttributes) {
		tsNginxProxyService.delete(tsNginxProxy);
		addMessage(redirectAttributes, "删除Nginx代理成功");
		return "redirect:"+Global.getAdminPath()+"/np/tsNginxProxy/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TsNginxProxy> list = tsNginxProxyService.findList(new TsNginxProxy());
		for (int i=0; i<list.size(); i++){
			TsNginxProxy e = list.get(i);
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