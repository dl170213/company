$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/sysDept/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
            parent.layer.alert("连接超时，请重新登录！");
            location.href = "/logout";
		},
		success : function(data) {
            if(typeof(data) == 'string'){
                if(data.indexOf("<html")>0){
                    location.href = "/logout";
                }
            }
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            name : {
                required : true
                ,remote : {
                    url : "/system/sysDept/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        parentId : function() {
                            return $("#parentId").val();
                        },
                        name : function() {
                            return $("#name").val();
                        }
                    }
                }
            }
        },
        messages : {
            name : {
                required : icon + "请输入机构名称",
                remote : icon +"同级机构下该机构名称已存在"
            }
        }
    })
}