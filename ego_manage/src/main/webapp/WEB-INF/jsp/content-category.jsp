<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	var oldMenuName = "";
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){
            e.preventDefault();
            $(this).tree('select',node.target);
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onBeforeEdit:function(node){
        	oldMenuName=node.text;
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        				$.messager.alert('提示','创建'+node.text+' 分类成功!');
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!\n'+data.msg);
        				_tree.tree("remove",node.target);
        			}
        		});
        	}else{
        		$.post("/content/category/update",{id:node.id,name:node.text},function(data){
        			if(data.status == 200){
        				$.messager.alert('提示','修改'+node.text+' 分类成功!');
        			}else{
        				$.messager.alert('提示','修改'+node.text+' 分类失败!');
        				_tree.tree("update",{
            				target : node.target,
            				text:oldMenuName
            			});
        			}
        		});
        	}
        }
	});
});
function menuHandler(item){
	var tree = $("#contentCategory");
	var node = tree.tree("getSelected");
	if(item.name === "add"){
		tree.tree('append', {
            parent: (node?node.target:null),
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0);
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete/",{parentId:node.parentId,id:node.id},function(data){
					if(data.status==200){
						tree.tree("remove",node.target);
					}else{
						$.messager.alert('提示','删除'+node.text+'分类失败!');
					}
				});	
			}
		});
	}
}
</script>