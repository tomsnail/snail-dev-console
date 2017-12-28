<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务组管理</title>
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
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
	
	
	<shiro:hasPermission name="sn:tsServiceGroup:edit">
	
		<script type="text/javascript">
		
			function syncAll(){
				
				$("#searchForm").attr("action","${ctx}/sn/tsServiceGroup/syncAll");
				$("#searchForm").submit();
				
			}
		
		</script>
	
	</shiro:hasPermission>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sn/tsServiceGroup/">服务组列表</a></li>
		<shiro:hasPermission name="sn:tsServiceGroup:edit"><li><a href="${ctx}/sn/tsServiceGroup/form">服务组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsServiceGroup" action="${ctx}/sn/tsServiceGroup/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>服务名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>组名：</label>
				<form:input path="groupName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>KEY：</label>
				<form:input path="nameKey" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>所属上级：</label>
				<sys:treeselect id="parent" name="parent.id" value="${tsServiceGroup.parent.id}" labelName="parent.name" labelValue="${tsServiceGroup.parent.name}"
					title="所属上级" url="/sn/tsServiceGroup/treeData"  cssClass="" allowClear="true"/>
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
				<th>服务名</th>
				<th>组名</th>
				<th>KEY</th>
				<th>可用节点数</th>
				<th>所属上级</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sn:tsServiceGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/sn/tsServiceGroup/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.groupName}}
			</td>
			<td>
				{{row.nameKey}}
			</td>
			<td>
				{{row.useNodeNumber}}
			</td>
			<td>
				{{row.parent.id}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="sn:tsServiceGroup:edit"><td>
   				<a href="${ctx}/sn/tsServiceGroup/form?id={{row.id}}">修改</a>
				<a href="${ctx}/sn/tsServiceGroup/delete?id={{row.id}}" onclick="return confirmx('确认要删除该服务组及所有子服务组吗？', this.href)">删除</a>
				<a href="${ctx}/sn/tsServiceGroup/form?parent.id={{row.id}}">添加下级服务组</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>