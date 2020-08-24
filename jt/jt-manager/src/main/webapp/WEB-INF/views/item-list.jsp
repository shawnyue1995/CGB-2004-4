<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemList" title="商品列表" 
       data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url:'/item/query',method:'get',pageSize:20,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">商品ID</th>
            <th data-options="field:'title',width:200">商品标题</th>
            <!--formatter 作用执行格式化的操作,将返回值结果进行展现 会传递2个参数 参数1.当前标签的value值  参数2:行号  -->
            <th data-options="field:'cid',width:100,align:'center',formatter:KindEditorUtil.findItemCatName">商品类目</th>
            <th data-options="field:'sellPoint',width:100">卖点</th>
            <th data-options="field:'price',width:70,align:'right',formatter:KindEditorUtil.formatPrice">价格</th>
            <th data-options="field:'num',width:70,align:'right'">库存数量</th>
            <th data-options="field:'barcode',width:100">条形码</th>
            <th data-options="field:'status',width:60,align:'center',formatter:KindEditorUtil.formatItemStatus">状态</th>
            <th data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>
    //调用获取选中表格数据的id.
    function getSelectionsIds(){
    	var itemList = $("#itemList");
    	//获取选中的用户的信息整行元素
    	var sels = itemList.datagrid("getSelections"); //数组结构
    	var ids = [];  //将选中的元素封装到ids数组中.
    	//获取选中元素的ID
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	//springMVC参数传递时,一般都是字符串类型 需要将数组转化为字符串.
    	ids = ids.join(",");   //id字符串类型  1,2,3,4,5
    	return ids;
    }
    
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	$(".tree-title:contains('新增商品')").parent().click();
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	//获取用户选中数据的id的字符串.   1,23,3,4,5,6
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个商品才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个商品!');
        		return ;
        	}

        	//在指定的位置进行弹出框操作.
        	$("#itemEditWindow").window({
        		onLoad :function(){ //弹出框之后加载指定的数据.
        			//回显数据
        			var data = $("#itemList").datagrid("getSelections")[0];
        			//console.info(data);
        			data.priceView = KindEditorUtil.formatPrice(data.price);
        			//.form方法是easyUI提供的专门实现数据回显的函数. 如果name名称相同在,则赋值.
        			$("#itemeEditForm").form("load",data);
        			
        			// 加载商品描述
        			//_data = SysResult.ok(itemDesc)
        			$.getJSON('/item/query/item/desc/'+data.id,function(_data){
        				if(_data.status == 200){

        					itemEditEditor.html(_data.data.itemDesc);
        				}
        			});
        			
        			//加载商品规格
        			$.getJSON('/item/param/item/query/'+data.id,function(_data){
        				if(_data && _data.status == 200 && _data.data && _data.data.paramData){
        					$("#itemeEditForm .params").show();
        					$("#itemeEditForm [name=itemParams]").val(_data.data.paramData);
        					$("#itemeEditForm [name=itemParamId]").val(_data.data.id);
        					
        					//回显商品规格
        					 var paramData = JSON.parse(_data.data.paramData);
        					
        					 var html = "<ul>";
        					 for(var i in paramData){
        						 var pd = paramData[i];
        						 html+="<li><table>";
        						 html+="<tr><td colspan=\"2\" class=\"group\">"+pd.group+"</td></tr>";
        						 
        						 for(var j in pd.params){
        							 var ps = pd.params[j];
        							 html+="<tr><td class=\"param\"><span>"+ps.k+"</span>: </td><td><input autocomplete=\"off\" type=\"text\" value='"+ps.v+"'/></td></tr>";
        						 }
        						 
        						 html+="</li></table>";
        					 }
        					 html+= "</ul>";
        					 $("#itemeEditForm .params td").eq(1).html(html);
        				}
        			});

                    /**
                    *  实现商品分类数据的ajax回显.
                    *  步骤:
                    *      1.想办法获取商品分类的cid.
                    *      2.利用ajax请求获取商品分类的名称
                    *      3.在指定的位置显示分类名称.
                    */
                    var cid = data.cid;
                    $.get("/item/cat/queryItemName",{"itemCatId":cid},function(name){
                        //先找cid的input标签之后找它的兄弟 span.之后完成赋值操作.
                        $("input[name='cid']").siblings("span").text(name);
                                                               //.html("传递的是html标签元素");
                    });

        			KindEditorUtil.init({
        				"pics" : data.image,
        				"cid" : data.cid,
        				fun:function(node){
        					KindEditorUtil.changeItemParam(node, "itemeEditForm");
        				} 
        			});
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
            //动态获取选中id   ids格式   id1,id2,id3 字符串
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/item/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品成功!',undefined,function(){
            					$("#itemList").datagrid("reload");
            				});
            			}else{
            				$.messager.alert("提示",data.msg);
            			}
            		});
        	    }
        	});
        }
    },'-',{
        text:'下架',
        iconCls:'icon-remove',
        handler:function(){
        	//获取选中的ID串中间使用","号分割
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品!');
        		return ;
        	}
        	$.messager.confirm('确认','确定下架ID为 '+ids+' 的商品吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/item/2",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','下架商品成功!',undefined,function(){
            					$("#itemList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
        text:'上架',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品!');
        		return ;
        	}
        	$.messager.confirm('确认','确定上架ID为 '+ids+' 的商品吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/item/1",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','上架商品成功!',undefined,function(){
            					$("#itemList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>