/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.indexpage.web;

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
import cn.tomsnail.dev.console.config.indexpage.entity.JsIndex;
import cn.tomsnail.dev.console.config.indexpage.service.JsIndexService;

/**
 * 首页配置Controller
 * @author yangsong
 * @version 2017-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/indexpage/jsIndex")
public class JsIndexController extends BaseController {

	@Autowired
	private JsIndexService jsIndexService;
	
	@ModelAttribute
	public JsIndex get(@RequestParam(required=false) String id) {
		JsIndex entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jsIndexService.get(id);
		}
		if (entity == null){
			entity = new JsIndex();
		}
		return entity;
	}
	
	@RequiresPermissions("indexpage:jsIndex:view")
	@RequestMapping(value = {"list", ""})
	public String list(JsIndex jsIndex, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<JsIndex> list = jsIndexService.findList(jsIndex); 
		model.addAttribute("list", list);
		return "config/indexpage/jsIndexList";
	}

	@RequiresPermissions("indexpage:jsIndex:view")
	@RequestMapping(value = "form")
	public String form(JsIndex jsIndex, Model model) {
		if (jsIndex.getParent()!=null && StringUtils.isNotBlank(jsIndex.getParent().getId())){
			jsIndex.setParent(jsIndexService.get(jsIndex.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(jsIndex.getId())){
				JsIndex jsIndexChild = new JsIndex();
				jsIndexChild.setParent(new JsIndex(jsIndex.getParent().getId()));
				List<JsIndex> list = jsIndexService.findList(jsIndex); 
				if (list.size() > 0){
					jsIndex.setSort(list.get(list.size()-1).getSort());
					if (jsIndex.getSort() != null){
						jsIndex.setSort(jsIndex.getSort() + 30);
					}
				}
			}
		}
		if (jsIndex.getSort() == null){
			jsIndex.setSort(30);
		}
		model.addAttribute("jsIndex", jsIndex);
		return "config/indexpage/jsIndexForm";
	}

	@RequiresPermissions("indexpage:jsIndex:edit")
	@RequestMapping(value = "save")
	public String save(JsIndex jsIndex, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jsIndex)){
			return form(jsIndex, model);
		}
		jsIndexService.save(jsIndex);
		addMessage(redirectAttributes, "保存首页配置成功");
		return "redirect:"+Global.getAdminPath()+"/indexpage/jsIndex/?repage";
	}
	
	@RequiresPermissions("indexpage:jsIndex:edit")
	@RequestMapping(value = "delete")
	public String delete(JsIndex jsIndex, RedirectAttributes redirectAttributes) {
		jsIndexService.delete(jsIndex);
		addMessage(redirectAttributes, "删除首页配置成功");
		return "redirect:"+Global.getAdminPath()+"/indexpage/jsIndex/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<JsIndex> list = jsIndexService.findList(new JsIndex());
		for (int i=0; i<list.size(); i++){
			JsIndex e = list.get(i);
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