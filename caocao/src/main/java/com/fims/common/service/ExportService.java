package com.fims.common.service;

import com.fims.common.utils.R;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27.
 */
public interface ExportService {

    void ProductExport(HttpServletResponse response);

    void poExport(HttpServletResponse response, Map<String, Object> map);

    void sbiExport(HttpServletResponse response, Map<String, Object> map);

    void sbiDetailExport(HttpServletResponse response, Map<String, Object> map);

    void poExportOld(HttpServletResponse response, Map<String, Object> map);

}
