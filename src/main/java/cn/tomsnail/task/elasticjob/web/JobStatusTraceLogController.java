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
import cn.tomsnail.task.elasticjob.entity.JobStatusTraceLog;
import cn.tomsnail.task.elasticjob.service.JobStatusTraceLogService;

/**
 * 任务执行跟踪Controller
 * @author yangsong
 * @version 2018-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/elasticjob/jobStatusTraceLog")
public class JobStatusTraceLogController extends BaseController {

	@Autowired
	private JobStatusTraceLogService jobStatusTraceLogService;
	
	@ModelAttribute
	public JobStatusTraceLog get(@RequestParam(required=false) String id) {
		JobStatusTraceLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobStatusTraceLogService.get(id);
		}
		if (entity == null){
			entity = new JobStatusTraceLog();
		}
		return entity;
	}
	
	@RequiresPermissions("elasticjob:jobStatusTraceLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobStatusTraceLog jobStatusTraceLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobStatusTraceLog> page = jobStatusTraceLogService.findPage(new Page<JobStatusTraceLog>(request, response), jobStatusTraceLog); 
		model.addAttribute("page", page);
		return "task/elasticjob/jobStatusTraceLogList";
	}

	@RequiresPermissions("elasticjob:jobStatusTraceLog:view")
	@RequestMapping(value = "form")
	public String form(JobStatusTraceLog jobStatusTraceLog, Model model) {
		model.addAttribute("jobStatusTraceLog", jobStatusTraceLog);
		return "task/elasticjob/jobStatusTraceLogForm";
	}

	@RequiresPermissions("elasticjob:jobStatusTraceLog:edit")
	@RequestMapping(value = "save")
	public String save(JobStatusTraceLog jobStatusTraceLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobStatusTraceLog)){
			return form(jobStatusTraceLog, model);
		}
		jobStatusTraceLogService.save(jobStatusTraceLog);
		addMessage(redirectAttributes, "保存任务执行跟踪成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobStatusTraceLog/?repage";
	}
	
	@RequiresPermissions("elasticjob:jobStatusTraceLog:edit")
	@RequestMapping(value = "delete")
	public String delete(JobStatusTraceLog jobStatusTraceLog, RedirectAttributes redirectAttributes) {
		jobStatusTraceLogService.delete(jobStatusTraceLog);
		addMessage(redirectAttributes, "删除任务执行跟踪成功");
		return "redirect:"+Global.getAdminPath()+"/elasticjob/jobStatusTraceLog/?repage";
	}

}