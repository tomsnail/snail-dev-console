<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务执行跟踪管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/elasticjob/jobStatusTraceLog/">任务执行跟踪列表</a></li>
		<li class="active"><a href="${ctx}/elasticjob/jobStatusTraceLog/form?id=${jobStatusTraceLog.id}">任务执行跟踪<shiro:hasPermission name="elasticjob:jobStatusTraceLog:edit">${not empty jobStatusTraceLog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="elasticjob:jobStatusTraceLog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jobStatusTraceLog" action="${ctx}/elasticjob/jobStatusTraceLog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">作业名称：</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原始任务ID：</label>
			<div class="controls">
				<form:input path="originalTaskId" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务ID：</label>
			<div class="controls">
				<form:input path="taskId" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SLAVE：</label>
			<div class="controls">
				<form:input path="slaveId" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">源：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行类型：</label>
			<div class="controls">
				<form:select path="executionType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('JOB_EXE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分片序号：</label>
			<div class="controls">
				<form:input path="shardingItem" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('JOB_EXE_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行信息：</label>
			<div class="controls">
				<form:input path="message" htmlEscape="false" maxlength="4000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间：</label>
			<div class="controls">
				<input name="creationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${jobStatusTraceLog.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>