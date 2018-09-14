var prefix = "/sys/user"

$(function() {
	var deptId = '';
	getTreeData();
	load(deptId);

//你也可以忽略 base 设定的根目录，直接在 extend 指定路径（主要：该功能为 layui 2.2.0 新增）
/*    layui.extend({
        mod2: '{/}js/lay/modules' // {/}的意思即代表采用自有路径，即不跟随 base 路径
    });
    layui.use(['treeselect'],
        function () {
            layui.treeselect(
                {
                    elem: "#treeselecttest",
                    data: '/system/sysDept/tree',//可以是treedata，也可以是 获取treedata的URL地址
                    method: "GET"
                });
        });*/
});

function load(deptId) {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
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
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						name : $('#searchName').val(),
						deptId : $('#deptId').val()
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					/*{
						checkbox : true
					},
					{
						field : 'userId', // 列字段名
						title : '序号' // 列标题
					},*/
					{
						field : 'name',
						title : '姓名'
					},
					{
						field : 'username',
						title : '账号(工号)'
					},
					{
						field : 'email',
						title : '邮箱'
					},
                    {
                        field : 'deptName',
                        title : '机构'
                    },
					{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
                            var div_s = "<div style='white-space:nowrap;'>";
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.userId
								+ '\')" style="display:inline;"><i class="fa fa-edit "></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.userId
								+ '\')" style="display:inline;"><i class="fa fa-remove"></i></a> ';
                            var c = '<a class="btn btn-success btn-sm " href="#" title="角色"  mce_href="#" onclick="role(\''
                                + row.userId
                                + '\')" style="display:inline;"><i class="fa fa-user"></i></a> ';
							var f = '<a class="btn btn-warning btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
								+ row.userId
								+ '\')" style="display:inline;"><i class="fa fa-key"></i></a> ';
                            var div_e = "</div>";
							return div_s + e + c + f + div_e;
						}
					} ]
			});
}
function reLoad() {
//	$('#exampleTable').bootstrapTable('refreshOptions',{pageNumber:1});
    $('#exampleTable').bootstrapTable('destroy');
    load(deptId);
}
function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '增加账号',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '450px' ],
		content : prefix + '/add',// iframe的url
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

function role(id) {

	if(id == '1'){
        layer.msg("超级管理员角色不能修改");
        return;
	}
    // iframe层
    layer.open({
        type : 2,
        title : '账号角色',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '600px', '425px' ],
        content : '/sys/role/roleselect/'+id,// iframe的url
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
			url : "/sys/user/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
function edit(id) {
	layer.open({
		type : 2,
		title : '账号修改',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '450px' ],
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
function resetPwd(id) {
    layer.confirm('确定要重置该账号的密码？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix + "/resetPwd",
            type : "POST",
            data : {
                'id' : id
            },
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
    })
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
			ids[i] = row['userId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}
function getTreeData() {
	$.ajax({
		type : "GET",
		url : "/system/sysDept/tree",
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#jstree').jstree({
		'core' : {
			'data' : tree
		},
		"plugins" : [ "search" ]
	});
	$('#jstree').jstree().open_all();
}
$('#jstree').on("changed.jstree", function(e, data) {
	if (data.selected == -1) {
		var opt = {
			query : {
				deptId : '',
			}
		}
		$('#exampleTable').bootstrapTable('refresh', opt);
	} else {
		var opt = {
			query : {
				deptId : data.selected[0],
			}
		}
		$('#exampleTable').bootstrapTable('refresh',opt);
	}

});

var openDept = function(){
    layer.open({
        type:2,
        title:"选择机构",
        area : [ '350px', '450px' ],
        content : prefix+"/deptTreeView"
    })
}

function showOK() {
    layer.msg("操作成功");
}

function loadDept( deptId,deptName){
    $("#deptId").val(deptId);
    $("#depart").val(deptName);

    if (deptId == -1) {
        var opt = {
            query : {
                deptId : '',
            }
        }
    //    $('#exampleTable').bootstrapTable('refresh', opt);
    } else {
        var opt = {
            query : {
                deptId : deptId,
            }
        }
     //   $('#exampleTable').bootstrapTable('refresh',opt);
    }
}