$().ready(function() {
	validateRule();
    //日期时间选择器
    laydate.render({
        elem: '#receiveddate'
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
    //.replace(/^(\d*\.?\d{0,2}).*/,'$1')
//    var num = obj.value;
//    obj.value = num.toFixed(2);
    if($("#count").val()!=null&&$("#count").val()!=""){
        if($("#pice").val()!=null&&$("#pice").val()!=""){
            $("#total").val(parseFloat($("#count").val())*parseFloat($("#pice").val()));
            $("#total").val($("#total").val().replace(/^(\d*\.?\d{0,3}).*/,'$1'));
        }else{
            $("#total").val("0.00");
        }
	}else{
        $("#total").val("0.00");
	}

}

function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/po/update",
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
			pm : {
				required : true
			},
            count:{
                required : true
			},
			workbody:{
                required : true
			}
			,pice:{
                required : true
			}
            ,customer:{
                required : true
            }
            ,worktype:{
                required : true
            }
            ,receiveddate:{
                required : true
			}
		},
		messages : {
			pm : {
				required : icon + "请输入项目名称"
			},
            count : {
                required : icon + "请输入数量"
            },
            workbody:{
                required : icon + "请输入工作内容"
            }
            ,pice:{
                required : icon + "请输入单价"
            }
            ,customer:{
                required : icon + "请输入客户编号"
            }
            ,worktype:{
                required : icon + "请输入工作类型"
            }
            ,receiveddate:{
                required : icon + "请选择收到日期"
            }
		}
	})
}