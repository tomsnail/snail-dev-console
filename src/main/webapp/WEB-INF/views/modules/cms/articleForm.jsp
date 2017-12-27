<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
                        top.$.jBox.tip('请选择归属栏目','warning');
                    }else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
                        top.$.jBox.tip('请填写正文','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属栏目:</label>
			<div class="controls">
                <sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>&nbsp;
                <span>
                    <input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()"><label for="url">外部链接</label>
                </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				&nbsp;<label>颜色:</label>
				<form:select path="color" class="input-mini">
					<form:option value="" label="默认"/>
					<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">正文:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/photo" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">相关文章:</label>
			<div class="controls">
				<form:hidden id="articleDataRelation" path="articleData.relation" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<ol id="articleSelectList"></ol>
				<a id="relationButton" href="javascript:" class="btn">添加相关</a>
				<script type="text/javascript">
					var articleSelect = [];
					function articleSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						for (var i=0; i<articleSelect.length; i++){
							if (articleSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							articleSelect.splice(index,1);
						}else{
							articleSelect.push([id,title]);
						}
						articleSelectRefresh();
					}
					function articleSelectRefresh(){
						$("#articleDataRelation").val("");
						$("#articleSelectList").children().remove();
						for (var i=0; i<articleSelect.length; i++){
							$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
							$("#articleDataRelation").val($("#articleDataRelation").val()+articleSelect[i][0]+",");
						}
					}
					$.getJSON("${ctx}/cms/article/findByIds",{ids:$("#articleDataRelation").val()},function(data){
						for (var i=0; i<data.length; i++){
							articleSelect.push([data[i][1],data[i][2]]);
						}
						articleSelectRefresh();
					});
					$("#relationButton").click(function(){
						top.$.jBox.open("iframe:${ctx}/cms/article/selectList?pageSize=8", "添加相关",$(top.document).width()-220,$(top.document).height()-180,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</div>
		</div>
		
		
		
		
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>