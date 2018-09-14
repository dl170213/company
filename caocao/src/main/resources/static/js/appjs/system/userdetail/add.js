/**
 * 不显示下拉按钮
 */

var test_ = $("#testNoBtn");
var test_1 = $("#testNoBtn1");
var testBsSuggest = $("#testNoBtn").bsSuggest({
    //url: "/rest/sys/getuserlist?keyword=",
    url: "/js/plugins/suggest/data.json",
    /*effectiveFields: ["userName", "shortAccount"],
     searchFields: [ "shortAccount"],
     effectiveFieldsAlias:{userName: "姓名"},*/
    showBtn: false,
    idField: "userId",
    keyField: "userName"
}).on('onDataRequestSuccess', function (e, result) {
    console.log('onDataRequestSuccess: ', result);
}).on('onSetSelectValue', function (e, keyword) {
    console.log('onSetSelectValue: ', keyword);
    test_1.val(keyword.key);
}).on('onUnsetSelectValue', function (e) {
    console.log("onUnsetSelectValue");
});

var testBsSuggest1 = $("#testNoBtn1").bsSuggest({
    //url: "/rest/sys/getuserlist?keyword=",
    url: "/js/plugins/suggest/data.json",
    /*effectiveFields: ["userName", "shortAccount"],
     searchFields: [ "shortAccount"],
     effectiveFieldsAlias:{userName: "姓名"},*/
    showBtn: false,
    idField: "userId",
    keyField: "userName"
}).on('onDataRequestSuccess', function (e, result) {
    console.log('onDataRequestSuccess: ', result);
}).on('onSetSelectValue', function (e, keyword) {
    console.log('onSetSelectValue: ', keyword);
    test_.val(keyword.key);
}).on('onUnsetSelectValue', function (e) {
    console.log("onUnsetSelectValue");
});


$().ready(function() {
    //日期时间选择器
    laydate.render({
        elem: '#entryTime'
        ,type: 'date'
        ,max: 0
    });
    laydate.render({
        elem: '#positiveTime'
        ,type: 'date'
        ,max: 0
    });
    laydate.render({
        elem: '#leaveTime'
        ,type: 'date'
        ,max: 0
    });
    laydate.render({
        elem: '#graduateyear'
        ,type: 'month'
        ,max: 0
    });
    laydate.render({
        elem: '#contracttime'
        ,type: 'date'
        ,max: 0
    });
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	/*$.ajax({
		cache : true,
		type : "POST",
		url : "/system/userdetail/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});*/
    parent.layer.msg("操作成功");
    parent.reLoad();
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            username : {
				required : true
			},
            name : {
                required : true
            }
		},
		messages : {
            username : {
				required : icon + "请输入工号！"
			},
            name : {
                required : icon + "请输入姓名！"
            }
		}
	})
}


var openUser = function(){
    layer.open({
        type:2,
        title:"选择员工",
        area : [ '300px', '450px' ],
        content:"/sys/user/treeView"
    })
}
function loadUser( userIdExpense,userNames){
    $("#userIdExpense").val(userIdExpense);
    $("#userNames").val(userNames);
}