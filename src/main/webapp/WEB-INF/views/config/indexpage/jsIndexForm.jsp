<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页配置管理</title>
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
		<li><a href="${ctx}/indexpage/jsIndex/">首页配置列表</a></li>
		<li class="active"><a href="${ctx}/indexpage/jsIndex/form?id=${jsIndex.id}&parent.id=${jsIndexparent.id}">首页配置<shiro:hasPermission name="indexpage:jsIndex:edit">${not empty jsIndex.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="indexpage:jsIndex:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jsIndex" action="${ctx}/indexpage/jsIndex/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片链接：</label>
			<div class="controls">
				<form:hidden id="icon" path="icon" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="icon" type="files" uploadPath="/indexpage/jsIndex" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址链接：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orderInt" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级parent_id:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${jsIndex.parent.id}" labelName="parent.name" labelValue="${jsIndex.parent.name}"
					title="parent_id" url="/indexpage/jsIndex/treeData" extId="${jsIndex.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="indexpage:jsIndex:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>