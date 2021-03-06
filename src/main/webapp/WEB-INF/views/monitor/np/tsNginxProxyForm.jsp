<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>Nginx代理管理</title>
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
	
	<shiro:hasPermission name="unify:tsConfig:edit">
	
		<script type="text/javascript">
		
			function saveAndSync(){
				
				$("#inputForm").attr("action","${ctx}/np/tsNginxProxy/saveAndSync");
				$("#inputForm").submit();
				
			}
		
		</script>
	
	</shiro:hasPermission>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/np/tsNginxProxy/">Nginx代理列表</a></li>
		<li class="active"><a href="${ctx}/np/tsNginxProxy/form?id=${tsNginxProxy.id}&parent.id=${tsNginxProxyparent.id}">Nginx代理<shiro:hasPermission name="np:tsNginxProxy:edit">${not empty tsNginxProxy.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="np:tsNginxProxy:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tsNginxProxy" action="${ctx}/np/tsNginxProxy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级所属上级:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${tsNginxProxy.parent.id}" labelName="parent.name" labelValue="${tsNginxProxy.parent.name}"
					title="所属上级" url="/np/tsNginxProxy/treeData" extId="${tsNginxProxy.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代理地址：</label>
			<div class="controls">
				<form:textarea path="proxyUrl" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">真实地址：</label>
			<div class="controls">
				<form:textarea path="realUrl" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代理类型：</label>
			<div class="controls">
				<form:select path="proxyType" class="input-xlarge required">
					<form:options items="${fns:getDictList('ts_nginx_proxy_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代理方式：</label>
			<div class="controls">
				<form:select path="proxyMethod" class="input-xlarge required">
					<form:options items="${fns:getDictList('ts_nginx_proxy_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务器:</label>
			<div class="controls">
				<sys:treeselect id="serverId" name="serverId" value="${tsNginxProxy.serverId}" labelName="" labelValue="${tsNginxProxy.serverName}"
					title="服务器" url="/server/tsServer/treeData"  cssClass="required" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<form:select path="isUse" class="input-xlarge required">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="unify:tsConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveAndSync();" value="保存并更新"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="np:tsNginxProxy:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>