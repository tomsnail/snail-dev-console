<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>Nginx代理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							proxyType: getDictLabel(${fns:toJson(fns:getDictList('ts_nginx_proxy_type'))}, row.proxyType),
							proxyMethod: getDictLabel(${fns:toJson(fns:getDictList('ts_nginx_proxy_method'))}, row.proxyMethod),
							isUse: getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, row.isUse),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/np/tsNginxProxy/">Nginx代理列表</a></li>
		<shiro:hasPermission name="np:tsNginxProxy:edit"><li><a href="${ctx}/np/tsNginxProxy/form">Nginx代理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsNginxProxy" action="${ctx}/np/tsNginxProxy/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>所属上级：</label>
			</li>
			<li><label>代理类型：</label>
				<form:select path="proxyType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_nginx_proxy_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>代理类型：</label>
				<form:select path="proxyMethod" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_nginx_proxy_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否可用：</label>
				<form:select path="isUse" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="syncAll();" value="立即更新"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>所属上级</th>
				<th>代理地址</th>
				<th>代理类型</th>
				<th>代理方式</th>
				<th>是否可用</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="np:tsNginxProxy:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/np/tsNginxProxy/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.parent.id}}
			</td>
			<td>
				{{row.proxyUrl}}
			</td>
			<td>
				{{dict.proxyType}}
			</td>
			<td>
				{{dict.proxyMethod}}
			</td>
			<td>
				{{dict.isUse}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="np:tsNginxProxy:edit"><td>
   				<a href="${ctx}/np/tsNginxProxy/form?id={{row.id}}">修改</a>
				<a href="${ctx}/np/tsNginxProxy/delete?id={{row.id}}" onclick="return confirmx('确认要删除该Nginx代理及所有子Nginx代理吗？', this.href)">删除</a>
				<a href="${ctx}/np/tsNginxProxy/form?parent.id={{row.id}}">添加下级Nginx代理</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>