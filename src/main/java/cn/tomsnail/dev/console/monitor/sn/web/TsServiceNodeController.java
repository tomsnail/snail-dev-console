/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.monitor.sn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceNode;
import cn.tomsnail.dev.console.monitor.sn.service.TsServiceNodeService;

/**
 * 服务节点Controller
 * @author yangsong
 * @version 2017-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sn/tsServiceNode")
public class TsServiceNodeController extends BaseController {

	@Autowired
	private TsServiceNodeService tsServiceNodeService;
	
	@ModelAttribute
	public TsServiceNode get(@RequestParam(required=false) String id) {
		TsServiceNode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsServiceNodeService.get(id);
		}
		if (entity == null){
			entity = new TsServiceNode();
		}
		return entity;
	}
	
	@RequiresPermissions("sn:tsServiceNode:view")
	@RequestMapping(value = {"list", ""})
	public String list(TsServiceNode tsServiceNode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TsServiceNode> page = tsServiceNodeService.findPage(new Page<TsServiceNode>(request, response), tsServiceNode); 
		model.addAttribute("page", page);
		return "monitor/sn/tsServiceNodeList";
	}

	@RequiresPermissions("sn:tsServiceNode:view")
	@RequestMapping(value = "form")
	public String form(TsServiceNode tsServiceNode, Model model) {
		model.addAttribute("tsServiceNode", tsServiceNode);
		return "monitor/sn/tsServiceNodeForm";
	}

	@RequiresPermissions("sn:tsServiceNode:edit")
	@RequestMapping(value = "save")
	public String save(TsServiceNode tsServiceNode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsServiceNode)){
			return form(tsServiceNode, model);
		}
		tsServiceNodeService.save(tsServiceNode);
		addMessage(redirectAttributes, "保存服务节点成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceNode/?repage";
	}
	
	@RequiresPermissions("sn:tsServiceNode:edit")
	@RequestMapping(value = "delete")
	public String delete(TsServiceNode tsServiceNode, RedirectAttributes redirectAttributes) {
		tsServiceNodeService.delete(tsServiceNode);
		addMessage(redirectAttributes, "删除服务节点成功");
		return "redirect:"+Global.getAdminPath()+"/sn/tsServiceNode/?repage";
	}

}