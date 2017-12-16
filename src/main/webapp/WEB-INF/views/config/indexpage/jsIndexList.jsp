<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页配置管理</title>
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
		<li class="active"><a href="${ctx}/indexpage/jsIndex/">首页配置列表</a></li>
		<shiro:hasPermission name="indexpage:jsIndex:edit"><li><a href="${ctx}/indexpage/jsIndex/form">首页配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jsIndex" action="${ctx}/indexpage/jsIndex/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>分组：</label>
				<form:input path="groupName" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>图片链接</th>
				<th>地址链接</th>
				<th>排序</th>
				<th>分组</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="indexpage:jsIndex:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jsIndex">
			<tr>
				<td><a href="${ctx}/indexpage/jsIndex/form?id=${jsIndex.id}">
					${jsIndex.name}
				</a></td>
				<td>
					${fns:abbr(jsIndex.icon,40)}
				</td>
				<td>
					${fns:abbr(jsIndex.url,40)}
				</td>
				<td>
					${jsIndex.sort}
				</td>
				<td>
					${jsIndex.groupName}
				</td>
				<td>
					<fmt:formatDate value="${jsIndex.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${jsIndex.remarks}
				</td>
				<shiro:hasPermission name="indexpage:jsIndex:edit"><td>
    				<a href="${ctx}/indexpage/jsIndex/form?id=${jsIndex.id}">修改</a>
					<a href="${ctx}/indexpage/jsIndex/delete?id=${jsIndex.id}" onclick="return confirmx('确认要删除该首页配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>