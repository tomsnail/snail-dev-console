/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.task.elasticjob.web;

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

import com.dangdang.ddframe.job.lite.console.service.RegistryCenterConfigurationService;
import com.dangdang.ddframe.job.lite.console.service.impl.RegistryCenterConfigurationServiceImpl;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.config.server.service.TsServerService;
import cn.tomsnail.task.elasticjob.entity.JobInfo;
import cn.tomsnail.task.elasticjob.service.JobInfoService;

/**
 * 作业列表Controller
 * @author yangsong
 * @version 2018-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/elasticjob/jobInfo")
public class JobInfoController extends BaseController {
	
	private RegistryCenterConfigurationService regCenterService = new RegistryCenterConfigurationServiceImpl();
	
	@Autowired
	private TsServerService tsServerService;

	@Autowired
	private JobInfoService jobInfoService;
	
	@ModelAttribute
	public JobInfo get(@RequestParam(required=false) String id) {
		JobInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobInfoService.get(id);
		}
		if (entity == null){
			entity = new JobInfo();
		}
		return entity;
	}
	
	public String zk(String zookeeper) {
		TsServer tsServer = tsServerService.get(zookeeper);
		if(tsServer!=null){
			return tsServer.getServerIp()+":"+tsServer.getServerPort();
		}else{
			return zookeeper;
		}
	}
	

	
	@RequiresPermissions("elasticjob:jobInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobInfo jobInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		jobInfo.setZookeeper(zk(jobInfo.getZookeeper()));
		Page<JobInfo> page = jobInfoService.findPage(new Page<JobInfo>(request, response), jobInfo); 
		model.addAttribute("page", page);
		return "task/elasticjob/jobInfoList";
	}

	@RequiresPermissions("elasticjob:jobInfo:view")
	@RequestMapping(value = "form")
	public String form(JobInfo jobInfo, Model model) {
		model.addAttribute("jobInfo", jobInfo);
		return "task/elasticjob/jobInfoForm";
	}

	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "save")
	public String save(JobInfo jobInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobInfo)){
			return form(jobInfo, model);
		}
		jobInfoService.save(jobInfo);
		addMessage(redirectAttributes, "保存作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "update")
	public String update(JobInfo jobInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobInfo)){
			return form(jobInfo, model);
		}
		if(StringUtils.isAnyBlank(jobInfo.getZookeeper(),jobInfo.getNamespace())){
			addMessage(redirectAttributes, "错误!ZK地址或者命名空间不能为空");
			return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
		}
		jobInfo.setZookeeper(zk(jobInfo.getZookeeper()));
		jobInfoService.updateAllJob(jobInfo);
		Page<JobInfo> page = jobInfoService.findPage(new Page<JobInfo>(request, response), jobInfo); 
		model.addAttribute("page", page);
		return "task/elasticjob/jobInfoList";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.delete(jobInfo);
		addMessage(redirectAttributes, "删除作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "shutdown")
	public String shutdown(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.shutdown(jobInfo);
		addMessage(redirectAttributes, "关闭作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "disable")
	public String disable(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.disable(jobInfo);
		addMessage(redirectAttributes, "停用作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "trigger")
	public String trigger(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.trigger(jobInfo);
		addMessage(redirectAttributes, "触发作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "enable")
	public String enable(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.enable(jobInfo);
		addMessage(redirectAttributes, "启用作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobInfo:edit")
	@RequestMapping(value = "updateSetting")
	public String updateSetting(JobInfo jobInfo, RedirectAttributes redirectAttributes) {
		jobInfoService.updateSetting(jobInfo);
		addMessage(redirectAttributes, "启用作业列表成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobInfo/?repage";
	}
	
	

}