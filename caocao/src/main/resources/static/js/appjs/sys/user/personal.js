var prefix = "/sys/user"
$(function () {
    validateRule();
    $("#pwdOld").val("");
    /*laydate.render({
        elem: '#birth'
        ,type: 'datetime'
      //  ,value:new Date()
    });*/
    $.extend($.validator.messages, {
        equalTo: "两次新密码不一致",
    });
});
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    var hobbyStr = getHobbyStr();
    $("#hobby").val(hobbyStr);
    if($("#basicInfoForm").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/user/updatePeronal",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    parent.layer.alert("连接超时，请重新登录！");
                    location.href = "/logout";
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新成功");
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        }

});
$("#pwd_save").click(function () {
    if($("#modifyPwd").valid()){
        $.ajax({
            cache : true,
            type : "POST",
            url :"/sys/user/userResetPwd",
            data : $('#modifyPwd').serialize(),
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
                    parent.layer.alert("更新密码成功");
                    $("#photo_info").click();
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });
    }
});

function validateRule() {
    $("#modifyPwd").validate({
        errorPlacement:function(error, element){
            element.parent("div").next("div .gg-error").html(error);
        },
        rules : {
            pwdOld : {
                required : true
            },
            pwdNew : {
                required : true
            },
            confirm_password : {
                required : true,
                equalTo:'#pwdNew'
            }
        }
    });
}


function getHobbyStr(){
    var hobbyStr ="";
    $(".hobby").each(function () {
        if($(this).is(":checked")){
            hobbyStr+=$(this).val()+";";
        }
    });
   return hobbyStr;
}

function oldshow() {
    $("#pwdOld").attr("type","text");
}
function oldunshow() {
    $("#pwdOld").attr("type","password");
}
function newshow() {
    $("#pwdNew").attr("type","text");
}
function newunshow() {
    $("#pwdNew").attr("type","password");
}
function pwdshow() {
    $("#confirm_password").attr("type","text");
}
function pwdunshow() {
    $("#confirm_password").attr("type","password");
}