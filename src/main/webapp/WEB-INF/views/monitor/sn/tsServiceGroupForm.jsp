<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
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
		<li><a href="${ctx}/sn/tsServiceGroup/">服务组列表</a></li>
		<li class="active"><a href="${ctx}/sn/tsServiceGroup/form?id=${tsServiceGroup.id}&parent.id=${tsServiceGroupparent.id}">服务组<shiro:hasPermission name="sn:tsServiceGroup:edit">${not empty tsServiceGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sn:tsServiceGroup:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tsServiceGroup" action="${ctx}/sn/tsServiceGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">服务名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组名：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">KEY：</label>
			<div class="controls">
				<form:input path="nameKey" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级所属上级:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${tsServiceGroup.parent.id}" labelName="parent.name" labelValue="${tsServiceGroup.parent.name}"
					title="所属上级" url="/sn/tsServiceGroup/treeData" extId="${tsServiceGroup.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务节点：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>模块</th>
							<th>地址</th>
							<th>状态</th>
							<th>权重</th>
							<th>重试次数</th>
							<th>失败策略</th>
							<th>负载策略</th>
							<th>超时时间</th>
							<th>版本</th>
							<th>更新时间</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${nodeList}" var="tsServiceNode">
						<tr>
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
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sn:tsServiceGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>