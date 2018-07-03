<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务执行跟踪管理</title>
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
		<li class="active"><a href="${ctx}/elasticjob/jobStatusTraceLog/">任务执行跟踪列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="jobStatusTraceLog" action="${ctx}/elasticjob/jobStatusTraceLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>作业名称：</label>
				<form:input path="jobName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>原始任务ID：</label>
				<form:input path="originalTaskId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>任务ID：</label>
				<form:input path="taskId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>SLAVE：</label>
				<form:input path="slaveId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>执行类型：</label>
				<form:select path="executionType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('JOB_EXE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('JOB_EXE_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>完成时间：</label>
				<input name="creationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobStatusTraceLog.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>原始任务ID</th>
				<th>任务ID</th>
				<th>SLAVE</th>
				<th>源</th>
				<th>执行类型</th>
				<th>分片序号</th>
				<th>状态</th>
				<th>完成时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="elasticjob:jobStatusTraceLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobStatusTraceLog">
			<tr>
				<td><a href="${ctx}/elasticjob/jobStatusTraceLog/form?id=${jobStatusTraceLog.id}">
					${jobStatusTraceLog.jobName}
				</a></td>
				<td>
					${jobStatusTraceLog.originalTaskId}
				</td>
				<td>
					${jobStatusTraceLog.taskId}
				</td>
				<td>
					${jobStatusTraceLog.slaveId}
				</td>
				<td>
					${jobStatusTraceLog.source}
				</td>
				<td>
					${fns:getDictLabel(jobStatusTraceLog.executionType, 'JOB_EXE_TYPE', '')}
				</td>
				<td>
					${jobStatusTraceLog.shardingItem}
				</td>
				<td>
					${fns:getDictLabel(jobStatusTraceLog.state, 'JOB_EXE_STATE', '')}
				</td>
				<td>
					<fmt:formatDate value="${jobStatusTraceLog.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${jobStatusTraceLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${jobStatusTraceLog.remarks}
				</td>
				<shiro:hasPermission name="elasticjob:jobStatusTraceLog:edit"><td>
    				<a href="${ctx}/elasticjob/jobStatusTraceLog/form?id=${jobStatusTraceLog.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>