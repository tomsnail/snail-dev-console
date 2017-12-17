/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.unify.web;

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
import cn.tomsnail.dev.console.config.unify.entity.TsConfig;
import cn.tomsnail.dev.console.config.unify.service.TsConfigService;

/**
 * 统一配置Controller
 * @author yangsong
 * @version 2017-12-17
 */
@Controller
@RequestMapping(value = "${adminPath}/unify/tsConfig")
public class TsConfigController extends BaseController {

	@Autowired
	private TsConfigService tsConfigService;
	
	@ModelAttribute
	public TsConfig get(@RequestParam(required=false) String id) {
		TsConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsConfigService.get(id);
		}
		if (entity == null){
			entity = new TsConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("unify:tsConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(TsConfig tsConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TsConfig> page = tsConfigService.findPage(new Page<TsConfig>(request, response), tsConfig); 
		model.addAttribute("page", page);
		return "config/unify/tsConfigList";
	}

	@RequiresPermissions("unify:tsConfig:view")
	@RequestMapping(value = "form")
	public String form(TsConfig tsConfig, Model model) {
		model.addAttribute("tsConfig", tsConfig);
		return "config/unify/tsConfigForm";
	}

	@RequiresPermissions("unify:tsConfig:edit")
	@RequestMapping(value = "save")
	public String save(TsConfig tsConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsConfig)){
			return form(tsConfig, model);
		}
		tsConfigService.save(tsConfig);
		addMessage(redirectAttributes, "保存统一配置成功");
		return "redirect:"+Global.getAdminPath()+"/unify/tsConfig/?repage";
	}
	
	@RequiresPermissions("unify:tsConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(TsConfig tsConfig, RedirectAttributes redirectAttributes) {
		tsConfigService.delete(tsConfig);
		addMessage(redirectAttributes, "删除统一配置成功");
		return "redirect:"+Global.getAdminPath()+"/unify/tsConfig/?repage";
	}

}