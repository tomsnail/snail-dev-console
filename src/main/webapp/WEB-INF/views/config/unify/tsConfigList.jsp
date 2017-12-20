<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统一配置管理</title>
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
		<li class="active"><a href="${ctx}/unify/tsConfig/">统一配置列表</a></li>
		<shiro:hasPermission name="unify:tsConfig:edit"><li><a href="${ctx}/unify/tsConfig/form">统一配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsConfig" action="${ctx}/unify/tsConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>标签：</label>
				<form:input path="label" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>关键字：</label>
				<form:input path="key" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_unify_conf_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>同步方法：</label>
				<form:select path="syncMethod" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_unify_conf_sync_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>同步系统：</label>
				<form:select path="syncSystem" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_unify_conf_sync_system')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>描述：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="立即更新"/></li>
			<li class="clearfix"></li>
		</ul>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>标签</th>
				<th>关键字</th>
				<th>值</th>
				<th>类型</th>
				<th>服务器</th>
				<th>同步方法</th>
				<th>同步系统</th>
				<th>同步时间</th>
				<shiro:hasPermission name="unify:tsConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tsConfig">
			<tr>
				<td><a href="${ctx}/unify/tsConfig/form?id=${tsConfig.id}">
					${tsConfig.name}
				</a></td>
				<td>
					${tsConfig.label}
				</td>
				<td>
					${tsConfig.key}
				</td>
				<td>
					${tsConfig.value}
				</td>
				<td>
					${fns:getDictLabel(tsConfig.type, 'ts_unify_conf_type', '')}
				</td>
				<td>
				
					${ts:getServerName(tsConfig.serverId)}
				
				</td>
				<td>
					${fns:getDictLabel(tsConfig.syncMethod, 'ts_unify_conf_sync_method', '')}
				</td>
				<td>
					${fns:getDictLabel(tsConfig.syncSystem, 'ts_unify_conf_sync_system', '')}
				</td>
				<td>
					${tsConfig.syncDate}
				</td>
				<shiro:hasPermission name="unify:tsConfig:edit"><td>
    				<a href="${ctx}/unify/tsConfig/form?id=${tsConfig.id}">修改</a>
					<a href="${ctx}/unify/tsConfig/delete?id=${tsConfig.id}" onclick="return confirmx('确认要删除该统一配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>