var userExpense = $("#userIdExpense");
var usernamenumber = $("#username");
var testBsSuggest = $("#userIdExpense").bsSuggest({
    //url: "/rest/sys/getuserlist?keyword=",
    url: "/sys/user/getAllUser",
    effectiveFields: ["name","username"],
    /* searchFields: [ "shortAccount"],*/
    effectiveFieldsAlias:{name: "姓名",username: "工号"},
    showBtn: false,
    idField: "username",
    keyField: "name"
}).on('onDataRequestSuccess', function (e, result) {
    console.log('onDataRequestSuccess: ', result);
}).on('onSetSelectValue', function (e, keyword) {
    console.log('onSetSelectValue: ', keyword);
    usernamenumber.val(keyword.id);
}).on('onUnsetSelectValue', function (e) {
    console.log("onUnsetSelectValue");
});

var usernamesuggest = $("#username").bsSuggest({
    //url: "/rest/sys/getuserlist?keyword=",
    url: "/sys/user/getAllUser",
    effectiveFields: ["username","name"],
    /* searchFields: [ "shortAccount"],*/
    effectiveFieldsAlias:{username: "工号",name: "姓名"},
    showBtn: false,
    idField: "name",
    keyField: "username"
}).on('onDataRequestSuccess', function (e, result) {
    console.log('onDataRequestSuccess: ', result);
}).on('onSetSelectValue', function (e, keyword) {
    console.log('onSetSelectValue: ', keyword);
    userExpense.val(keyword.id);
}).on('onUnsetSelectValue', function (e) {
    console.log("onUnsetSelectValue");
});

$().ready(function() {
    //执行一个laydate实例
    var insdate =laydate.render({
        elem: '#invoicetime' //指定元素
        ,type: 'date'
    });
    //日期时间选择器
    laydate.render({
        elem: '#createtime'
        ,type: 'month'
    });
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

//税率修改后更新总金额、实报金额
function taxtax(obj){
    obj.value = obj.value.replace(/[^\d]/g,'');  //清除“数字”以外的字符
    //  alert(Number($("#number").val())+Number($("#number").val())*Number(obj.value*0.01));

    $("#total").val("");
    $("#realnumber").val("");

    if($("#number").val()!=null&&$("#number").val()!=""){
        $("#total").val((Number($("#number").val())+Number($("#number").val())*Number(obj.value*0.01)).toFixed(2));
        $("#realnumber").val($("#total").val());
    }
}
//发票金额修改后更新总金额、实报金额
function clearNoNum1(obj){
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.match(/^\d+(?:\.\d{0,2})?/);
    $("#total").val("");
    $("#realnumber").val("");
    if($("#tax").val()!=null&&$("#tax").val()!=""){
        $("#total").val((Number(obj.value)+Number(obj.value)*Number(Number($("#tax").val())*0.01)).toFixed(2));
        $("#realnumber").val($("#total").val());
    }
}

//总金额、实报金额保留两位小数
function clearNoNum(obj){
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.match(/^\d+(?:\.\d{0,2})?/);
}

function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/check/update",
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
				parent.reLoad(-1);
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
    // 字母和数字的验证
    jQuery.validator.addMethod("isChar", function(value, element) {
        var chrnum = /^([a-zA-Z0-9]+)$/;
        return this.optional(element) || (chrnum.test(value));
    }, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

    // 浮点数
    jQuery.validator.addMethod("isFloatNumber", function(value, element) {
        var chrnum = /^[0-9]+(.[0-9]{1,3})?$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入有效金额");
    jQuery.validator.addMethod("isExpenseNull", function(value, element) {
        if(usernamenumber.val()!=""&&userExpense.val()==""){
            return false;
        }
        return true;
    }, "请输入员工工号");
    jQuery.validator.addMethod("isUsernameNull", function(value, element) {
        if(userExpense.val()!=""&&usernamenumber.val()==""){
            return false;
        }
        return true;
    }, "请输入员工姓名");


    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            invoicenumber : {
                required : true,
                isChar:true,
                maxlength:20
            },
            userIdExpense : {
                required : false,
                isExpenseNull: true,
                maxlength: 20,
                minlength: 2,
                remote : {
                    url : "/sys/user/checkUser", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        name : function() {
                            return $("#userIdExpense").val();
                        },
                        username : function() {
                            return $("#username").val();
                        }
                    }
                }
            },
            username : {
                required : false,
                isUsernameNull:true,
                maxlength: 20,
                minlength: 4,
                remote : {
                    url : "/sys/user/checkUser", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        name : function() {
                            return $("#userIdExpense").val();
                        },
                        username : function() {
                            return $("#username").val();
                        }
                    }
                }
            }
        },
        messages : {
            invoicenumber : {
                required : icon + "请输入发票编号",
                isChar: icon + "只能输入数字和字母(字符A-Z, a-z, 0-9)",
                maxlength: icon + "请输入有效发票编号（最多20位）"
            },
            userIdExpense : {
                isExpenseNull: icon + "请选择员工姓名",
                maxlength: icon + "员工姓名最多20位",
                minlength: icon + "员工姓名最少2位",
                remote:icon + "员工工号不存在或者员工姓名与员工工号不匹配"
            },
            username : {
                isUsernameNull: icon + "请选择员工工号",
                maxlength: icon + "员工工号最多20位",
                minlength: icon + "员工工号最少4位",
                remote:icon + "员工工号不存在或者员工姓名与员工工号不匹配"
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