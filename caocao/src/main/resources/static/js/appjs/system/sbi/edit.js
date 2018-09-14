$().ready(function() {
	validateRule();
    laydate.render({
        elem: '#reseve1'
        ,type: 'date'
    });
    laydate.render({
        elem: '#ticketdate'
        ,type: 'date'
    });
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function clearNoNum(obj){
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
    obj.value = obj.value.replace(/^(\d*\.?\d{0,2}).*/,'$1');
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

}

function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/sbi/update",
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
            pretax : {
				required : true
			},
            posttax:{
                required : true
            },
            tax:{
                required : true
            }
		},
		messages : {
            pretax : {
				required : icon + "请输入税前金额"
			},
            posttax:{
                required : icon + "请输入税后金额"
            },
            tax:{
                required : icon + "请输入税收金额"
            }
		}
	})
}