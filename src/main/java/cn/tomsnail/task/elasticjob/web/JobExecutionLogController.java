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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.task.elasticjob.entity.JobExecutionLog;
import cn.tomsnail.task.elasticjob.service.JobExecutionLogService;

/**
 * 任务执行日志Controller
 * @author yangsong
 * @version 2018-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/elasticjob/jobExecutionLog")
public class JobExecutionLogController extends BaseController {

	@Autowired
	private JobExecutionLogService jobExecutionLogService;
	
	@ModelAttribute
	public JobExecutionLog get(@RequestParam(required=false) String id) {
		JobExecutionLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobExecutionLogService.get(id);
		}
		if (entity == null){
			entity = new JobExecutionLog();
		}
		return entity;
	}
	
	@RequiresPermissions("elasticjob:jobExecutionLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobExecutionLog jobExecutionLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobExecutionLog> page = jobExecutionLogService.findPage(new Page<JobExecutionLog>(request, response), jobExecutionLog); 
		model.addAttribute("page", page);
		return "task/elasticjob/jobExecutionLogList";
	}

	@RequiresPermissions("elasticjob:jobExecutionLog:view")
	@RequestMapping(value = "form")
	public String form(JobExecutionLog jobExecutionLog, Model model) {
		model.addAttribute("jobExecutionLog", jobExecutionLog);
		return "task/elasticjob/jobExecutionLogForm";
	}

	@RequiresPermissions("elasticjob:jobExecutionLog:edit")
	@RequestMapping(value = "save")
	public String save(JobExecutionLog jobExecutionLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobExecutionLog)){
			return form(jobExecutionLog, model);
		}
		jobExecutionLogService.save(jobExecutionLog);
		addMessage(redirectAttributes, "保存任务执行日志成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobExecutionLog/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobExecutionLog:edit")
	@RequestMapping(value = "delete")
	public String delete(JobExecutionLog jobExecutionLog, RedirectAttributes redirectAttributes) {
		jobExecutionLogService.delete(jobExecutionLog);
		addMessage(redirectAttributes, "删除任务执行日志成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobExecutionLog/?repage";
	}

}