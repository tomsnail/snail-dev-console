<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统一配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
				
				$("#inputForm").attr("action","${ctx}/unify/tsConfig/saveAndSync");
				$("#inputForm").submit();
				
			}
		
		</script>
	
	</shiro:hasPermission>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/unify/tsConfig/">统一配置列表</a></li>
		<li class="active"><a href="${ctx}/unify/tsConfig/form?id=${tsConfig.id}">统一配置<shiro:hasPermission name="unify:tsConfig:edit">${not empty tsConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="unify:tsConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tsConfig" action="${ctx}/unify/tsConfig/save" method="post" class="form-horizontal">
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
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">键值：</label>
			<div class="controls">
				<form:input path="key" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">值：</label>
			<div class="controls">
				<form:textarea path="value" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*  不超过2000字符</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务器:</label>
			<div class="controls">
				<sys:treeselect id="serverId" name="serverId" value="${tsConfig.serverId}" labelName="" labelValue="${ts:getServerName(tsConfig.serverId)}"
					title="服务器" url="/server/tsServer/treeData"  cssClass="required" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:options items="${fns:getDictList('ts_unify_conf_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务：</label>
			<div class="controls">
				<form:input path="service" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模块：</label>
			<div class="controls">
				<form:input path="module" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工程：</label>
			<div class="controls">
				<form:input path="project" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统：</label>
			<div class="controls">
				<form:input path="system" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步方法：</label>
			<div class="controls">
				<form:select path="syncMethod" class="input-xlarge required">
					<form:options items="${fns:getDictList('ts_unify_conf_sync_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步系统：</label>
			<div class="controls">
				<form:select path="syncSystem" class="input-xlarge required">
					<form:options items="${fns:getDictList('ts_unify_conf_sync_system')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步周期：</label>
			<div class="controls">
				<form:input path="syncDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="unify:tsConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveAndSync();" value="保存并更新"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="unify:tsConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>