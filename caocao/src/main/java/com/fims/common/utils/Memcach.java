package com.fims.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/9.
 */
public class Memcach {

    //缓存当前上传的excel的状态1:正在加载文件，2：正在验证文件，3：正在写入数据库，4：操作完毕
    public static Map<String,Object> EXCEL_STATUS ;

    //缓存当前上传的excel已完成的进度，当EXCEL_STATUS =2、3时有效
    public static Map<String,Object> EXCEL_PROCESS;

    static {
        EXCEL_STATUS = new HashMap<>();
        EXCEL_PROCESS = new HashMap<>();
    }
}
