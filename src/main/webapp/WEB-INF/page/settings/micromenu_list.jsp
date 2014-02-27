<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$(function(){
	//query
	var btn_query = $("#settings_micromenu_query").linkbutton({
		iconCls: "icon-search"
	});
	btn_query.bind("click",function(){
		btn_ajax("${pageContext.request.contextPath}/settings/micromenu!query.action");
	});
	//create
	var btn_create = $("#settings_micromenu_create").linkbutton({
		iconCls: 'icon-add'
	});
	btn_create.bind("click",function(){
		btn_ajax("${pageContext.request.contextPath}/settings/micromenu!create.action");
	});
	//delete
	var btn_del = $("#settings_micromenu_delete").linkbutton({
		iconCls: 'icon-remove'
	});
	btn_del.bind("click",function(){
		btn_ajax("${pageContext.request.contextPath}/settings/micromenu!delete.action");
	});
	//
	function btn_ajax(url){
		if(!url){
			$.messager.alert("error","url为空！");
			return;
		}
		$.messager.confirm("确认","您确认要执行要？",function(r){ 
			if(r){
				$.messager.progress();
				$.ajax({
					url:url,
					type:"GET",
					dataType:"json",
					success:function(data){
						$.messager.progress("close");
						alert(data);
					}
				});
			}
		});
	}
});
//-->
</script>
<div style="float:left;margin-top:10px;margin-left:20px;">
	<a id="settings_micromenu_query" href="#">查询微信菜单</a>
	<a id="settings_micromenu_create" href="#">创建微信菜单</a>
	<a id="settings_micromenu_delete" href="#">删除微信菜单</a>
</div>