
var prefix = "/system/po"
$(function() {
	load();
    //日期时间选择器
    laydate.render({
        elem: '#searchtime'
        ,type: 'date'
        ,max: 0
        ,range: '~'
    });
});

function rowStyle(row, index) {
    return {css:{'font-size': '13px;'}};
}
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
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
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
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
                        onLoadSuccess : function(data) {
                         //   var data = $('#exampleTable').bootstrapTable('getData', true);
                            //合并单元格
                         //   mergeCells(data, "reseve1", 0, $('#exampleTable'));

                        },
						columns : [
								{
									checkbox : true
								},
								/*								{
									field : 'id', 
									title : '编号' 
								},	*/				{
									field : 'reseve1',
									title : '合同号'
								},/*
																{
									field : 'pmid', 
									title : '项目id' 
								},*/
																{
									field : 'pm', 
									title : '项目' ,
								},
																{
									field : 'sp', 
									title : 'SP序列号' 
								},
												/*				{
									field : 'wbs', 
									title : 'WBS编号' ,
									width:100,
									formatter : function (value,row,index) {
										if(value!=null){
											return "<span style='width:99%;padding-left:0px;white-space:nowrap;display:block;overflow:hidden;text-overflow:ellipsis;'>" +
												value+"</span>";
										}
									}
								},*/
																{
									field : 'count', 
									title : '数量' 
								},
													/*			{
									field : 'workbody', 
									title : '工作内容',
									width:150,
									formatter : function (value,row,index) {
										if(value!=null){
											return "<span style='width:99%;padding-left:0px;white-space:nowrap;display:block;overflow:hidden;text-overflow:ellipsis;'>" +
												value+"</span>";
										}
									}
								},*/
																{
									field : 'pice', 
									title : '单价' 
								},
																{
									field : 'total', 
									title : '总价' 
								},
																{
									field : 'customer', 
									title : '客户名称' 
								},
																{
									field : 'worktype', 
									title : '工作类型' 
								},
																{
									field : 'sbi', 
									title : 'SBI号' 
								},
																{
									field : 'receiveddate',
									title : '收到日期' ,
									width: 140,
									formatter : function(value, row, index) {
										if(value!=null){
											return value.substring(0,10);
										}
									}
								},
									/*							{
									field : 'reseve1', 
									title : '预留1' 
								},
																{
									field : 'reseve2', 
									title : '预留2' 
								},
																{
									field : 'reseve3', 
									title : '预留3' 
								},*/
																{
									title : '操作',
									field : 'id',
                                    width : 150,
									align : 'center',
									formatter : function(value, row, index) {
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
										return  div_s + e + d + div_e;
									}
								} ]
					});
}
function reLoad() {
//	$('#exampleTable').bootstrapTable('refresh');
    $('#exampleTable').bootstrapTable('destroy');
    load();
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' ,// iframe的url
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
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id ,// iframe的url
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
//打开Excel导入层
function openExcelupload(){
    layer.open({
        type : 2,
        title : 'PO数据导入',
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

//打开Excel导入层
function openExceluploadBase(){
    layer.open({
        type : 2,
        title : 'PO基础数据导入',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '400px' ],
        closeBtn:0,
        content : prefix + '/openExceluploadBase' ,// iframe的url
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


//打开Excel导出层
function poexport() {
    layer.load();
    location.href = "/system/po/poexportdata?searchName="+$("#searchName").val()+"&searchtime="+$("#searchtime").val();
    layer.closeAll('loading'); //关闭loading
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
			}
		});
	})
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
			}
		});
	}, function() {

	});
}


/**
 * 合并单元格
 * @param data  原始数据（在服务端完成排序）
 * @param fieldName 合并属性名称
 * @param colspan   合并列
 * @param target    目标表格对象
 */
function mergeCells(data,fieldName,colspan,target){
    //声明一个map计算相同属性值在data对象出现的次数和
    var sortMap = {};
    for(var i = 0 ; i < data.length ; i++){
        for(var prop in data[i]){
            if(prop == fieldName){
                var key = data[i][prop]
                if(sortMap.hasOwnProperty(key)){
                    sortMap[key] = sortMap[key] * 1 + 1;
                } else {
                    sortMap[key] = 1;
                }
                break;
            }
        }
    }
    for(var prop in sortMap){
        console.log(prop,sortMap[prop])
    }
    var index = 0;
    for(var prop in sortMap){
        var count = sortMap[prop] * 1;
        $(target).bootstrapTable('mergeCells',{index:index, field:fieldName, colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'pm', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'sp', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'wbs', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'total', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'customer', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'worktype', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'sbi', colspan: colspan, rowspan: count});
        $(target).bootstrapTable('mergeCells',{index:index, field:'reseve2', colspan: colspan, rowspan: count});

		/*mergeCells(data, "pm", 0, $('#exampleTable'));
		 mergeCells(data, "sp", 0, $('#exampleTable'));
		 mergeCells(data, "wbs", 0, $('#exampleTable'));
		 mergeCells(data, "total", 0, $('#exampleTable'));
		 mergeCells(data, "customer", 0, $('#exampleTable'));
		 mergeCells(data, "worktype", 0, $('#exampleTable'));
		 mergeCells(data, "sbi", 0, $('#exampleTable'));
		 mergeCells(data, "reseve2", 0, $('#exampleTable'));*/
        index += count;
    }
}