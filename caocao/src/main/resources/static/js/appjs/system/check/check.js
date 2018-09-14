
var prefix = "/system/check";

function rowStyle(row, index) {
    return {css:{'font-size': '13px;'}};
}

function onKeyDown(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];

	if(e && e.keyCode==13){ // enter 键
        reLoad(0);
	}
}
$(function() {
    $('#reSwitch input').bootstrapSwitch({
        onText:'On',
        offText:'Off'
    });
    $('#deSwitch input').bootstrapSwitch({
        onText:'On',
        offText:'Off'
    });

//日期时间选择器
    laydate.render({
        elem: '#searchtime'
        ,type: 'date'
        ,range: '~'
    });
    load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
					//	search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							var restatus = false;
							if($('#deStatus').length>0){
                                    restatus =  $('#deStatus').bootstrapSwitch('state');
							}
                            var rerepeat = false;
                            if($('#reRepeat').length>0){
                                rerepeat =  $('#reRepeat').bootstrapSwitch('state');
                            }
                            var searchtime = "";
                            if($('#searchtime').val().indexOf("~")!=-1){
                                searchtime = $('#searchtime').val();
							}
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                searchName:$('#searchName').val(),
                                searchtime:searchtime,
                                restatus:restatus,
                                rerepeat:rerepeat
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
                        onLoadSuccess: function(data){  //加载成功时执行
							if($("#searchName").val()!=""){
								if(data.rows.length>0){
                                    layer.confirm("查询到重复发票 " + data.rows.length+ " 条数据！", {
                                        btn : [ '新增重复', '取消' ]
                                        // 按钮
                                    }, function() {
                                        add($("#searchName").val(),"1");
                                    }, function() {

                                    });

								}else{
                                    add($("#searchName").val(),"0");
								}
							}
							if(!$("#reRepeat").length>0){
                                $('#exampleTable').bootstrapTable('hideColumn', 'status');
							}
                        },
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
									/*							{
									field : 'id', 
									title : '编号',
									width :50
								},*/
																{
									field : 'invoicenumber', 
									title : '发票编号',
									formatter : function (value,row,index) {
										if(value!=null){
											return "<span style='width:99%;padding-left:0px;white-space:nowrap;display:block;overflow:hidden;text-overflow:ellipsis;'>" +
												value+"</span>";
										}
									}
								},
																{
									field : 'invoicetime', 
									title : '开票时间',
									align : 'center',
									formatter : function(value, row, index) {
										if(value!=null){
											return value.substring(0,10);
										}
									}
								},
																{
									field : 'number', 
									title : '发票金额' ,
									align : 'center',
								},
																{
									field : 'realnumber', 
									title : '实报金额' ,
									align : 'center',
								},
																{
									field : 'createtime',
									title : '报销时间' ,
									align : 'center',
									formatter : function(value, row, index) {
										if(value!=null){
											return value.substring(0,7);
										}
									}
								},
																{
									field : 'userIdCreate',
									title : '创建人' ,
									align : 'center',
									formatter : function (value,row,index) {
										if(value!=null){
											return "<span style='width:99%;padding-left:0px;white-space:nowrap;display:block;overflow:hidden;text-overflow:ellipsis;'>" +
												value+"</span>";
										}
									}
								},
																{
									field : 'userIdExpense',
									title : '报销人' ,
									align : 'center',
									formatter : function (value,row,index) {
										if(value!=null){
											return "<span style='width:99%;padding-left:0px;white-space:nowrap;display:block;overflow:hidden;text-overflow:ellipsis;'>" +
												value+"</span>";
										}
									}
								},
									/*							{
									field : 'remark', 
									title : '备注' ,
									width : 100
								},*/
															{
									field : 'status',
									title : '删除状态' ,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == '1') {
											return '<span class="label label-danger">已删除</span>';
										} else if (value == '0') {
											return '<span class="label label-primary">&nbsp;正&nbsp;常&nbsp;</span>';
										}
									}
								},
																{
									field : 'repeat',
									title : '重复状态' ,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == '1') {
											return '<span class="label label-warning">&nbsp;重&nbsp;复&nbsp;</span>';
										} else if (value == '0') {
											return '<span class="label label-primary">&nbsp;正&nbsp;常&nbsp;</span>';
										}
									}
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										if(row.status=='1')
											return "";
                                        var div_s = "<div style='white-space:nowrap;'>";
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')" style="display:inline;"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')" style="display:inline;"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')" style="display:inline;"><i class="fa fa-key"></i></a> ';
                                        var div_e = "</div>";
										return div_s + e + d + div_e;
									}
								} ]
					});
}
//判断是否清空输入框，如果是新增完成后回掉，需要清空输入框
function reLoad(stype) {
	if(stype==-1){
        $('#searchName').val("");
	}
//	$('#exampleTable').bootstrapTable('refresh');
    $('#exampleTable').bootstrapTable('destroy');
    load();
}
function add(code,status) {
//	alert(code);
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '500px' ],
		content : prefix + '/add?code='+code+"&status="+status,// iframe的url
        success: function(layero, index) {
            //获取当前弹出窗口的索引及初始大小
            layerIndex      = index;
            layerInitWidth  = $("#layui-layer"+layerIndex).width();
            layerInitHeight = $("#layui-layer"+layerIndex).height();
            resizeLayer();
            // form.render(null, 'form-edit');
        },error : function(request) {
            parent.layer.alert("连接超时，请重新登录！");
            location.href = "/logout";
        }
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '500px' ],
		content : prefix + '/edit/' + id,
        success: function(layero, index) {
            //获取当前弹出窗口的索引及初始大小
            layerIndex      = index;
            layerInitWidth  = $("#layui-layer"+layerIndex).width();
            layerInitHeight = $("#layui-layer"+layerIndex).height();
            resizeLayer();
            // form.render(null, 'form-edit');
        },error : function(request) {
            parent.layer.alert("连接超时，请重新登录！");
            location.href = "/logout";
        }
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
                if(typeof(r) == 'string'){
                    if(r.indexOf("<html")>0){
                        location.href = "/logout";
                    }
                }
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			},error : function(request) {
                parent.layer.alert("连接超时，请重新登录！");
                location.href = "/logout";
            }
		});
	})
}

//打开Excel导入层
function openExcelupload(){
    layer.open({
        type : 2,
        title : '发票基础数据导入',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '400px' ],
        closeBtn:0,
        content : prefix + '/openExcelupload' ,// iframe的url
        success: function(layero, index) {
            //获取当前弹出窗口的索引及初始大小
            layerIndex      = index;
            layerInitWidth  = $("#layui-layer"+layerIndex).width();
            layerInitHeight = $("#layui-layer"+layerIndex).height();
            resizeLayer();
            // form.render(null, 'form-edit');
        }
    });
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
                if(typeof(r) == 'string'){
                    if(r.indexOf("<html")>0){
                        location.href = "/logout";
                    }
                }
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			},error : function(request) {
                parent.layer.alert("连接超时，请重新登录！");
                location.href = "/logout";
            }
		});
	}, function() {

	});
}