<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务节点管理</title>
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
		<li class="active"><a href="${ctx}/sn/tsServiceNode/">服务节点列表</a></li>
		<shiro:hasPermission name="sn:tsServiceNode:edit"><li><a href="${ctx}/sn/tsServiceNode/form">服务节点添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsServiceNode" action="${ctx}/sn/tsServiceNode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>服务组：</label>
			</li>
			<li><label>模块：</label>
				<form:input path="module" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>地址：</label>
				<form:input path="addressUrl" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li><label>节点状态：</label>
				<form:select path="nodeStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_service_node_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>失败策略：</label>
				<form:select path="failType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_service_fail_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>负载策略：</label>
				<form:select path="lbType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_service_lb_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>服务组</th>
				<th>模块</th>
				<th>地址</th>
				<th>节点状态</th>
				<th>权重</th>
				<th>重试次数</th>
				<th>失败策略</th>
				<th>负载策略</th>
				<th>超时时间</th>
				<th>版本</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sn:tsServiceNode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tsServiceNode">
			<tr>
				<td><a href="${ctx}/sn/tsServiceNode/form?id=${tsServiceNode.id}">
					${tsServiceNode.serviceId}
				</a></td>
				<td>
					${tsServiceNode.module}
				</td>
				<td>
					${tsServiceNode.addressUrl}
				</td>
				<td>
					${fns:getDictLabel(tsServiceNode.nodeStatus, 'ts_service_node_status', '')}
				</td>
				<td>
					${tsServiceNode.weight}
				</td>
				<td>
					${tsServiceNode.retryCount}
				</td>
				<td>
					${fns:getDictLabel(tsServiceNode.failType, 'ts_service_fail_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tsServiceNode.lbType, 'ts_service_lb_type', '')}
				</td>
				<td>
					${tsServiceNode.timeout}
				</td>
				<td>
					${tsServiceNode.version}
				</td>
				<td>
					<fmt:formatDate value="${tsServiceNode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tsServiceNode.remarks}
				</td>
				<shiro:hasPermission name="sn:tsServiceNode:edit"><td>
    				<a href="${ctx}/sn/tsServiceNode/form?id=${tsServiceNode.id}">修改</a>
					<a href="${ctx}/sn/tsServiceNode/delete?id=${tsServiceNode.id}" onclick="return confirmx('确认要删除该服务节点吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>