<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业列表管理</title>
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
		
		function updateJobs(){
			$("#searchForm").attr("action","${ctx}/elasticjob/jobInfo/update");
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/elasticjob/jobInfo/">作业列表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="jobInfo" action="${ctx}/elasticjob/jobInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>ZOOKEEPER：</label>
				<sys:treeselect id="zookeeper" name="zookeeper" value="" labelName="parent.name" labelValue=""
					title="ZOOKEEPER" url="/server/tsServer/treeData"  cssClass="" allowClear="true"/>
			</li>
			
			<li><label>命名空间：</label>
				<form:input path="namespace" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="updateSubmit" class="btn btn-primary" type="button" value="更新作业信息" onclick="updateJobs()"/></li>
			<br>
			<br>
			<li><label>作业名称：</label>
				<form:input path="jobName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作业类型：</label>
				<form:select path="jobType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('JOB_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>监控：</label>
				<form:select path="monitorExecution" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>失败切换：</label>
				<form:select path="failover" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>失败不启动：</label>
				<form:select path="misfire" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:input path="jobState" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>ZOOKEEPER</th>
				<th>命名空间</th>
				<th>作业类型</th>
				<th>执行周期</th>
				<th>作业分片数</th>
				<th>监控</th>
				<th>失败切换</th>
				<th>失败不启动</th>
				<th>描述</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="elasticjob:jobInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobInfo">
			<tr>
				<td><a href="${ctx}/elasticjob/jobInfo/form?id=${jobInfo.id}">
					${jobInfo.jobName}
				</a></td>
				<td>
					${jobInfo.zookeeper}
				</td>
				<td>
					${jobInfo.namespace}
				</td>
				<td>
					${fns:getDictLabel(jobInfo.jobType, 'JOB_TYPE', '')}
				</td>
				<td>
					${jobInfo.jobCrontab}
				</td>
				<td>
					${jobInfo.shardingTotalCount}
				</td>
				<td>
					${fns:getDictLabel(jobInfo.monitorExecution, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(jobInfo.failover, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(jobInfo.misfire, 'yes_no', '')}
				</td>
				<td>
					${jobInfo.jobDesc}
				</td>
				<td>
					${jobInfo.jobState}
				</td>
				<td>
					<fmt:formatDate value="${jobInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${jobInfo.remarks}
				</td>
				<shiro:hasPermission name="elasticjob:jobInfo:edit"><td>
					<c:if test="${jobInfo.jobState=='OK'}">
    					<a href="${ctx}/elasticjob/jobInfo/trigger?id=${jobInfo.id}">触发</a>
    					<a href="${ctx}/elasticjob/jobInfo/disable?id=${jobInfo.id}">失效</a>
    					<a href="${ctx}/elasticjob/jobInfo/shutdown?id=${jobInfo.id}">终止</a>
					</c:if>
					<c:if test="${jobInfo.jobState=='DISABLED'}">
    					<a href="${ctx}/elasticjob/jobInfo/enable?id=${jobInfo.id}">启用</a>
					</c:if>
					<a href="${ctx}/elasticjob/jobInfo/updateSetting?id=${jobInfo.id}">更新配置</a>
					<a href="${ctx}/elasticjob/jobExecutionLog/?jobName=${jobInfo.jobName}">日志</a>
					<a href="${ctx}/elasticjob/jobInfo/form?id=${jobInfo.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>