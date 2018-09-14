var datenow = new Date().getTime();
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#upload', //绑定元素
        url: '/system/po/importExcelBase', //上传接口
        size: 10240,//KB
        accept: 'file',
        exts:"xls|xlsx",
        data: {
            seachname:datenow
        },
        //   auto:false,
        //   bindAction:"#upload",
        before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            layer.load(1); //上传loading
            $("#file_status").text("正在上传文件");
            $("#error_msg").text("");
            $("#closebtn").addClass("readOnly");
            getprocess(datenow);//获取上传进度
        },
        done: function (r) {
            if(typeof(r) == 'string'){
                if(r.indexOf("<html")>0){
                    location.href = "/logout";
                }
            }
            layer.closeAll('loading'); //关闭loading
            if(r.code==0||r.code==1||r.code==3){
                layer.msg(r.msg);
            }else{
                layer.msg(r.failList[0].errorMsg);
                $("#errorMsgDiv").removeClass("hidden");
                $.each(r.failList,function (key,value) {//打印错误信息
                    $("#error_msg").text($("#error_msg").text()+"\n第 "+(value.rowNum+1)+" 行："+value.errorMsg);
                });
            }
            $("#file_status").text(r.msg);
            $("#closebtn").removeClass("readOnly");
            //    app.getData();
        },
        error: function (r) {
            layer.closeAll('loading'); //关闭loading
            layer.msg(r.msg);
            $("#file_status").text(r.msg);
            $("#error_msg").text("");
            $("#closebtn").removeClass("readOnly");
        }
    });
});

//获取上传进度
function getprocess(seachname) {
    $.ajax({
        url : "/sys/online/importProcess",
        type : "get",
        data : {
            'importId' : seachname
        },
        success : function(r) {
            if($("#file_status").text().indexOf("失败")!=-1){
                return;
            }
            if (r.code==1) {
                $("#file_status").text("正在上传文件");
            }else if (r.code==2) {
                $("#file_status").text("正在校验文件");
            }else if (r.code==3) {
                $("#file_status").text("正在写入数据库,当前进度"+r.process);
            }else if (r.code==4) {
                $("#file_status").text("操作完毕");
            }
            if(r.code!=4&&r.code!=-1){
                setTimeout(function () {
                    getprocess(seachname);
                },100);
            }
        }
    });
}


function closeimport() {
    $.ajax({
        url : "/sys/online/removeProcess",
        type : "post",
        data : {
            'importId' : datenow
        },
        success : function(r) {

        }
    });
    parent.reLoad();
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}