
var prefix = "/system/userdetail"


function rowStyle(row, index) {
    var classes = ['active', 'success', 'info', 'warning', 'danger'];
    if (index ==1) {
        return {
            classes: 'info',
            css:{'font-size': '13px;'}
        };
    }
    if (index ==3) {
        return {
            classes: 'warning',
        	css:{'font-size': '13px;'}
        };
    }
    return {css:{'font-size': '13px;'}};
}

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
/*layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#test1', //绑定元素
        url: '/sys/user/importExcel', //上传接口
        size: 1000,
        accept: 'file',
        exts:"xls",
        before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
        	layer.load(); //上传loading
    	},
        done: function (r) {
            layer.closeAll('loading'); //关闭loading
        	layer.msg(r.msg);
            app.getData();
        },
        error: function (r) {
            layer.closeAll('loading'); //关闭loading
        	layer.msg(r.msg);
        }
    });
});*/
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
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset
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
						columns : [
								{
									checkbox : true,
                                    width : 50
								},
								/*{
									field : 'id', 
									title : '编号',
									width : '50px'
								},*/
								{
									field : 'username',
									title : '工号'
								},
								{
									field : 'name',
									title : '员工姓名'
								},
								/*{
									field : 'userId',
									title : '公司',
                                    width : '200px'
								},*/
								{
									field : 'deptment',
									title : '机构',
								},
								{
									field : 'project',
									title : '项目' ,
								},

								{
									field : 'reseve1',
									title : '状态' ,
								},
							/*{
									field : 'leaveTime', 
									title : '离职时间' ,
                                    width : '200px'
								},*/
								{
									field : 'duty', 
									title : '职务' ,
								},{
									field : 'entryTime',
									title : '入职时间' ,
									width : 200
								},
								/*{
									field : 'joblevel', 
									title : '岗位级别' ,
                                    width : '200px'
								},
								{
									field : 'idnumber', 
									title : '身份证号' ,
                                    width : '300px'
								},
								{
									field : 'idaddress', 
									title : '身份证地址' ,
                                    width : '400px'
								},
								{
									field : 'graduateschool', 
									title : '毕业学校' ,
                                    width : '300px'
								},
								{
									field : 'major', 
									title : '专业' ,
                                    width : '200px'
								},
								{
									field : 'education', 
									title : '学历' ,
                                    width : '200px'
								},
								{
									field : 'diploma', 
									title : '证书编号' ,
                                    width : '300px'
								},
								{
									field : 'graduateyear', 
									title : '毕业年份' ,
                                    width : '200px'
								},
								{
									field : 'contracttime', 
									title : '合同签订日期' ,
                                    width : '200px'
								},
								{
									field : 'remark', 
									title : '备注' ,
                                    width : '400px'
								},*/
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
									align : 'center',
                                    width :200,
									formatter : function(value, row, index) {
                                        var div_s = "<div style='white-space:nowrap;'>";
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"  style="display:inline;"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove_test(\''
												+ row.id
												+ '\')"  style="display:inline;"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"  style="display:inline;"><i class="fa fa-key"></i></a> ';
                                        var div_e = "</div>";
										return div_s + e + d + div_e;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '900px', '620px' ],
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
        area : [ '800px', '620px' ],
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

function remove_test(id){
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        layer.msg("删除成功");
        reLoad();
    })
}

function batchRemove_test(){
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
        layer.msg("批量删除成功");
        reLoad();
    }, function() {

    });

}

function download(){
    layer.msg("导入数据");
}
function upload(){
    layer.msg("导出数据");
}
function oneupload(){
    layer.msg("一键导出最近一周数据");
}
function remindSetting() {
    layer.open({
        type : 2,
        title : '提醒设置',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/remind/setting' // iframe的url
    });
}

