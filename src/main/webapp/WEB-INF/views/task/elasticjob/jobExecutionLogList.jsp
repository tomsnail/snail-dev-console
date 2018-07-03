<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务执行日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/elasticjob/jobExecutionLog/">任务执行日志列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="jobExecutionLog" action="${ctx}/elasticjob/jobExecutionLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>作业名称：</label>
				<form:input path="jobName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>任务ID：</label>
				<form:input path="taskId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>主机：</label>
				<form:input path="hostname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>IP地址：</label>
				<form:input path="ip" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>执行信息：</label>
				<form:input path="executionMessage" htmlEscape="false" maxlength="4000" class="input-medium"/>
			</li>
			<li><label>失败原因：</label>
				<form:input path="failureCause" htmlEscape="false" maxlength="4000" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobExecutionLog.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>完成时间：</label>
				<input name="completeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobExecutionLog.completeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>作业名称</th>
				<th>任务ID</th>
				<th>主机</th>
				<th>IP地址</th>
				<th>分片序列号</th>
				<th>执行源</th>
				<th>执行信息</th>
				<th>失败原因</th>
				<th>是否成功</th>
				<th>开始时间</th>
				<th>完成时间</th>
				<th>备注</th>
				<shiro:hasPermission name="elasticjob:jobExecutionLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobExecutionLog">
			<tr>
				<td><a href="${ctx}/elasticjob/jobExecutionLog/form?id=${jobExecutionLog.id}">
					${jobExecutionLog.jobName}
				</a></td>
				<td>
					${jobExecutionLog.taskId}
				</td>
				<td>
					${jobExecutionLog.hostname}
				</td>
				<td>
					${jobExecutionLog.ip}
				</td>
				<td>
					${jobExecutionLog.shardingItem}
				</td>
				<td>
					${jobExecutionLog.executionSource}
				</td>
				<td>
					${fns:abbr(jobExecutionLog.executionMessage,30)}
					
				</td>
				<td>
					${fns:abbr(jobExecutionLog.failureCause,30)}
				</td>
				<td>
					${fns:getDictLabel(jobExecutionLog.isSuccess, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${jobExecutionLog.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${jobExecutionLog.completeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${jobExecutionLog.remarks}
				</td>
				<shiro:hasPermission name="elasticjob:jobExecutionLog:edit"><td>
    				<a href="${ctx}/elasticjob/jobExecutionLog/form?id=${jobExecutionLog.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>