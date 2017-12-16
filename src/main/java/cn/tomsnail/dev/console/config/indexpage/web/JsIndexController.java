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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.dev.console.config.indexpage.entity.JsIndex;
import cn.tomsnail.dev.console.config.indexpage.service.JsIndexService;

/**
 * 首页配置Controller
 * @author yangsong
 * @version 2017-12-14
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
		Page<JsIndex> page = jsIndexService.findPage(new Page<JsIndex>(request, response), jsIndex); 
		model.addAttribute("page", page);
		return "config/indexpage/jsIndexList";
	}

	@RequiresPermissions("indexpage:jsIndex:view")
	@RequestMapping(value = "form")
	public String form(JsIndex jsIndex, Model model) {
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
	
	@ResponseBody
	@RequestMapping(value = "jsonData",method={RequestMethod.GET})
	public List<JsIndex> jsonData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		return jsIndexService.findList(new JsIndex());
	}

}