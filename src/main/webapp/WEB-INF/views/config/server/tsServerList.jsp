<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务器配置管理</title>
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
							serverType: getDictLabel(${fns:toJson(fns:getDictList('ts_server_type'))}, row.serverType),
							isMaster: getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, row.isMaster),
							serviceType: getDictLabel(${fns:toJson(fns:getDictList('ts_server_service_type'))}, row.serviceType),
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
		<li class="active"><a href="${ctx}/server/tsServer/">服务器配置列表</a></li>
		<shiro:hasPermission name="server:tsServer:edit"><li><a href="${ctx}/server/tsServer/form">服务器配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsServer" action="${ctx}/server/tsServer/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>服务器名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>上级所属：</label>
				<sys:treeselect id="parent" name="parent.id" value="${tsServer.parent.id}" labelName="parent.name" labelValue="${tsServer.parent.name}"
					title="上级所属" url="/server/tsServer/treeData"  cssClass="" allowClear="true"/>
			</li>
			<li><label>IP地址：</label>
				<form:input path="serverIp" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>端口：</label>
				<form:input path="serverPort" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>URL：</label>
				<form:input path="serverUrl" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户名：</label>
				<form:input path="serverUsername" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>服务器类型：</label>
				<form:select path="serverType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ts_server_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>服务器名称</th>
				<th>上级所属</th>
				<th>IP地址</th>
				<th>端口</th>
				<th>URL</th>
				<th>用户名</th>
				<th>服务器类型</th>
				<th>是否主用</th>
				<th>服务类型</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="server:tsServer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/server/tsServer/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.parent.id}}
			</td>
			<td>
				{{row.serverIp}}
			</td>
			<td>
				{{row.serverPort}}
			</td>
			<td>
				{{row.serverUrl}}
			</td>
			<td>
				{{row.serverUsername}}
			</td>
			<td>
				{{dict.serverType}}
			</td>
			<td>
				{{dict.isMaster}}
			</td>
			<td>
				{{dict.serviceType}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="server:tsServer:edit"><td>
   				<a href="${ctx}/server/tsServer/form?id={{row.id}}">修改</a>
				<a href="${ctx}/server/tsServer/delete?id={{row.id}}" onclick="return confirmx('确认要删除该服务器配置及所有子服务器配置吗？', this.href)">删除</a>
				<a href="${ctx}/server/tsServer/form?parent.id={{row.id}}&serviceType={{row.serviceType}}">添加下级服务器配置</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>