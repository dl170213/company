/**
 * 不显示下拉按钮
 */
var testBsSuggest = $("#testNoBtn").bsSuggest({
    //url: "/rest/sys/getuserlist?keyword=",
    url: "/js/plugins/suggest/data.json",
	/* effectiveFields: ["userName", "userId"],
	searchFields: [ "shortAccount"],
	 effectiveFieldsAlias:{userName: "项目",userId:"编号"},*/
    showBtn: false,
    idField: "userId",
    keyField: "userName"
}).on('onDataRequestSuccess', function (e, result) {
    console.log('onDataRequestSuccess: ', result);
}).on('onSetSelectValue', function (e, keyword) {
    console.log('onSetSelectValue: ', keyword);
}).on('onUnsetSelectValue', function (e) {
    console.log("onUnsetSelectValue");
});

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	/*$.ajax({
		cache : true,
		type : "POST",
		url : "/system/userdetail/update",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}