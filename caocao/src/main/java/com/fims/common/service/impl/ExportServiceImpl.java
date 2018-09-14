package com.fims.common.service.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.fims.common.domain.POExpoertDO;
import com.fims.common.domain.ProductExportDO;
import com.fims.common.domain.SBIDetailExportDO;
import com.fims.common.domain.SBIExportDO;
import com.fims.common.service.ExportService;
import com.fims.common.utils.ExcelUtils;
import com.fims.common.utils.R;
import com.fims.system.domain.PoDO;
import com.fims.system.domain.SbiDO;
import com.fims.system.service.PoService;
import com.fims.system.service.SbiService;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27.
 */
@Service
public class ExportServiceImpl implements ExportService{

    @Autowired
    private PoService poService;
    @Autowired
    private SbiService sbiService;
    @Override
    public void ProductExport(HttpServletResponse response) {
        List<ProductExportDO> list_pedo = new ArrayList<>();
        ProductExportDO pedo = new ProductExportDO("电脑","Thinkpad","1","PC-079CJ6"
        ,"869475004106534","仓库","仓库","1",new Date(),"9150.00","测试",
                "http://www.baidu.com","E:\\dl\\code\\caocao\\src\\main\\resources\\static\\img\\a3.jpg");

        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        list_pedo.add(pedo);
        ProductExportDO pedo1 = new ProductExportDO("电脑","Thinkpad","1","PC-079CJ6"
                ,"869475004106534","仓库","仓库","2",new Date(),"9150.00","测试",
                "http://www.baidu.com","E:\\dl\\code\\caocao\\src\\main\\resources\\static\\img\\a1.jpg");
        list_pedo.add(pedo1);

        ProductExportDO pedo2 = new ProductExportDO("电脑","Thinkpad","1","PC-079CJ6"
                ,"869475004106534","仓库","仓库","3",new Date(),"9150.00","测试",
                "http://www.baidu.com","E:\\dl\\code\\caocao\\src\\main\\resources\\static\\img\\a2.jpg");
        list_pedo.add(pedo2);
        ExcelUtils.exportExcel(list_pedo,"测试","ceshi",pedo.getClass(),"ceshi.xls",response);
    }

    @Override
    public void poExport(HttpServletResponse response, Map<String, Object> map) {
        try {
            List<PoDO> list = poService.list(map);
            List<POExpoertDO> list_ex = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            POExpoertDO poexdo = new POExpoertDO();
            if(list.size()>0){
                for(int i=0;i<list.size();i++){
                    PoDO podo = list.get(i);
                    poexdo = new POExpoertDO();
                    poexdo.setContractid(podo.getReseve1());//合同号
                    poexdo.setReceiveddate(sdf.format(podo.getReceiveddate()));//收到日期
                    poexdo.setPm(podo.getPm());
                    poexdo.setSp_(podo.getSp());
                    poexdo.setWbs_(podo.getWbs());
                    poexdo.setCount(podo.getCount());
                    poexdo.setWorkbody(podo.getWorkbody());
                    poexdo.setPice(podo.getPice());
                    poexdo.setTotal(poService.getPoSum(podo.getReseve1()));
                    /*if(podo.getSbi()!=null&&!"".equals(podo.getSbi())){
                        poexdo.setTotal(sbiService.getBySbi(podo.getSbi()).getPosttax());
                    }else{
                        poexdo.setTotal(podo.getTotal());
                    }*/
                    poexdo.setCustomer_(podo.getCustomer());
                    poexdo.setWorktype(podo.getWorktype());
                    poexdo.setSbi(podo.getSbi());
                    list_ex.add(poexdo);
                }
            }
            String sheet = "po";
            String filename = sdf.format(new Date());
            if(map.get("searchtime")!=null&&!"".equals(map.get("searchtime").toString())){
                String [] stimes = map.get("searchtime").toString().split("~");
                filename = sdf.format(stimes[0])+"-"+sdf.format(stimes[1]);
                sheet = stimes[0].substring(2,4)+stimes[0].substring(5,7)+stimes[0].substring(8,10)+"-"+stimes[1].substring(3,5)+stimes[1].substring(6,8)+stimes[1].substring(9,11);
            }
            ExportParams xp =  new ExportParams("PO数据导出表", sheet);

            xp.setType(ExcelType.XSSF);
         //   xp.setAddIndex(true);
            xp.setHeight((short)15.75);
            ExcelUtils.defaultExport(list_ex,poexdo.getClass(),filename+".xls",response,xp);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

//        ExcelUtils.defaultExport(list_ex,POExpoertDO.class,"PO数据导出_"+sdf.format(new Date())+".xls",response,xp);
       // ExcelUtils.

    }

    /**
     * 计算相同合同号的po  价格合计
     * */
    public String sumTotal(int index,List<PoDO> list){
        return "";
    }

    @Override
    public void sbiExport(HttpServletResponse response, Map<String, Object> map) {
        List<SbiDO> list = sbiService.list(map);
        List<SBIExportDO> list_ex = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(list.size()>0){
            for(SbiDO sbido:list){
                SBIExportDO sbiexdo = new SBIExportDO();
                sbiexdo.setPm(sbido.getPm());
                sbiexdo.setWorktype(sbido.getWorktype());
                sbiexdo.setPretax(sbido.getPretax());
                sbiexdo.setTax(sbido.getTax());
                sbiexdo.setPosttax(sbido.getPosttax());
                sbiexdo.setCustomer(sbido.getCustomer());
                sbiexdo.setInvoicenumber(sbido.getInvoicenumber());
                sbiexdo.setTicketdate(sbido.getTicketdate()==null?"":sdf.format(sbido.getTicketdate()));
                sbiexdo.setReseve1(sdf.format(sbido.getReseve1()));
                sbiexdo.setCreatetime(sdf.format(sbido.getCreatetime()));
                sbiexdo.setSbi(sbido.getSbi());
                list_ex.add(sbiexdo);
            }
        }
        System.out.println(list.toString());
        String sheet = "sbi";
        if(map.get("searchtime")!=null&&!"".equals(map.get("searchtime").toString())){
            String [] stimes = map.get("searchtime").toString().split("~");
            sheet = stimes[0].substring(2,4)+stimes[0].substring(5,7)+stimes[0].substring(8,10)+"-"+stimes[1].substring(3,5)+stimes[1].substring(6,8)+stimes[1].substring(9,11);
        }
        ExcelUtils.exportExcel(list_ex,"SBI数据导出表",sheet,SBIExportDO.class,sdf.format(new Date())+".xls",response);
    }

    @Override
    public void sbiDetailExport(HttpServletResponse response, Map<String, Object> map) {
        List<SbiDO> list = sbiService.list(map);
        List<SBIDetailExportDO> list_ex = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(list.size()>0){
            for(SbiDO sbido:list){
                //获取该SBI中所有的PO信息
                List<PoDO> listpo = poService.getBySbi(sbido.getSbi());

                if(listpo!=null&&listpo.size()>0){
                    for(PoDO pd : listpo){
                        SBIDetailExportDO sbiexdo = new SBIDetailExportDO();
                        sbiexdo.setSbi(sbido.getSbi());
                        sbiexdo.setReseve1(sdf.format(sbido.getReseve1()));
                        sbiexdo.setPosttax(sbido.getPosttax());
                        sbiexdo.setCustomer(sbido.getCustomer());
                        sbiexdo.setPo_contractid(pd.getReseve1());
                        sbiexdo.setPo_pm(pd.getPm());
                        sbiexdo.setPo_worktype(pd.getWorktype());
                        sbiexdo.setPo_count(pd.getCount());
                        sbiexdo.setPo_pice(pd.getPice());
                        sbiexdo.setPo_total(pd.getTotal());
                        list_ex.add(sbiexdo);
                    }
                }else{
                    SBIDetailExportDO sbiexdo = new SBIDetailExportDO();
                    sbiexdo.setSbi(sbido.getSbi());
                    sbiexdo.setReseve1(sdf.format(sbido.getReseve1()));
                    sbiexdo.setPosttax(sbido.getPosttax());
                    sbiexdo.setCustomer(sbido.getCustomer());
                    sbiexdo.setPo_contractid("");
                    sbiexdo.setPo_pm(sbido.getPm());
                    sbiexdo.setPo_worktype(sbido.getWorktype());
                    sbiexdo.setPo_count("");
                    sbiexdo.setPo_pice("");
                    sbiexdo.setPo_total("");
                    list_ex.add(sbiexdo);
                }
            }
        }
        System.out.println(list.toString());
        String sheet = "sbi";
        if(map.get("searchtime")!=null&&!"".equals(map.get("searchtime").toString())){
            String [] stimes = map.get("searchtime").toString().split("~");
            sheet = stimes[0].substring(2,4)+stimes[0].substring(5,7)+stimes[0].substring(8,10)+"-"+stimes[1].substring(3,5)+stimes[1].substring(6,8)+stimes[1].substring(9,11);
        }
        ExcelUtils.exportExcel(list_ex,"SBI详细数据导出表",sheet,SBIDetailExportDO.class,sdf.format(new Date())+".xls",response);
    }

    @Override
    public void poExportOld(HttpServletResponse response, Map<String, Object> map) {
        try {
            List<PoDO> list = poService.list(map);
            List<POExpoertDO> list_ex = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            POExpoertDO poexdo = new POExpoertDO();
            String sheet = "po";
            String filename = sdf.format(new Date());
            if(map.get("searchtime")!=null&&!"".equals(map.get("searchtime").toString())){
                String [] stimes = map.get("searchtime").toString().split("~");
                filename = sdf.format(stimes[0])+"-"+sdf.format(stimes[1]);
                sheet = stimes[0].substring(2,4)+stimes[0].substring(5,7)+stimes[0].substring(8,10)+"-"+stimes[1].substring(3,5)+stimes[1].substring(6,8)+stimes[1].substring(9,11);
            }
            // 创建一个新的Excel
            HSSFWorkbook workBook = new HSSFWorkbook();
            // 创建sheet页
            HSSFSheet sheet0 = workBook.createSheet();
            sheet0.createFreezePane(0,1,1,1);
            // sheet页名称
            workBook.setSheetName(0, sheet);
            // 设置Sheet页为默认
            sheet0.setSelected(true);
            sheet0.setAutobreaks(true);
            sheet0.setColumnWidth(0, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(1, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(2, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(3, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(4, (int)((40 + 0.72) * 256));
            sheet0.setColumnWidth(5, (int)((8 + 0.72) * 256));
            sheet0.setColumnWidth(6, (int)((40 + 0.72) * 256));
            sheet0.setColumnWidth(7, (int)((8 + 0.72) * 256));
            sheet0.setColumnWidth(8, (int)((8 + 0.72) * 256));
            sheet0.setColumnWidth(9, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(10, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(11, (int)((15 + 0.72) * 256));
            sheet0.setColumnWidth(12, (int)((15 + 0.72) * 256));

            int idnex_row = 0;
            int idnex_cell = 0;
            HSSFRow headerRow = sheet0.createRow((short) idnex_row++); // 取得Excel对象的第一行

        //    headerRow.setHeight((short)15.75);
            headerRow.setHeightInPoints((short)15.75);

            HSSFCell headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("收到日期");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("项目名称");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("合同号");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("SP序列");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("WBS编号");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("数量");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("工作内容");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("单价");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("总价");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("客户名称");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("工作类型");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            headerRow_cell = headerRow.createCell((short) idnex_cell++);
            headerRow_cell.setCellValue("SBI编号");
            headerRow_cell.setCellStyle(getHeaderStyle(workBook));
            if(list.size()>0){
                for(int i=0;i<list.size();i++){
                   PoDO podo = list.get(i);
                   /*  poexdo = new POExpoertDO();
                    poexdo.setContractid(podo.getReseve1());//合同号
                    poexdo.setReceiveddate(sdf.format(podo.getReceiveddate()));//收到日期
                    poexdo.setPm(podo.getPm());
                    poexdo.setSp_(podo.getSp());
                    poexdo.setWbs_(podo.getWbs());
                    poexdo.setCount(podo.getCount());
                    poexdo.setWorkbody(podo.getWorkbody());
                    poexdo.setPice(podo.getPice());
                    poexdo.setTotal(poService.getPoSum(podo.getReseve1()));
                    poexdo.setCustomer_(podo.getCustomer());
                    poexdo.setWorktype(podo.getWorktype());
                    poexdo.setSbi(podo.getSbi());
                    list_ex.add(poexdo);*/

                    idnex_cell = 0;
                    HSSFRow headerRow1 = sheet0.createRow((short) idnex_row++); // 取得Excel对象的第一行
                    headerRow.setHeightInPoints((short)15.75);
                    HSSFCell headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(sdf.format(podo.getReceiveddate()));//"收到日期"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getPm());//"项目名称"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getReseve1());//"合同号"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getSp());//"SP序列"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getWbs());//"WBS编号"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getCount());//"数量"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getWorkbody());//"工作内容"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getPice());//"单价"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(poService.getPoSum(podo.getReseve1()));//"总价"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getCustomer());//"客户名称"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getWorktype());//"工作类型"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));
                    headerRow_cell1 = headerRow1.createCell((short) idnex_cell++);
                    headerRow_cell1.setCellValue(podo.getSbi());//"SBI编号"
                    headerRow_cell1.setCellStyle(getTextStyle(workBook,idnex_cell-1));


                    /*//判断是否需要合并单元格
                    if(i>0&&isMerge(list.get(i-1),list.get(i))){
                        //创建合并单元格  ---begin
                        CellRangeAddress region = new CellRangeAddress(idnex_row-1, idnex_row, 0, 0);// 下标从0开始 起始行号，终止行号， 起始列号，终止列号
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 1, 1);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 2, 2);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 3, 3);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 4, 4);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 8, 8);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 9, 9);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 10, 10);
                        sheet0.addMergedRegion(region);
                        region = new CellRangeAddress(idnex_row-1, idnex_row, 11, 11);
                        sheet0.addMergedRegion(region);
                    }*/
                }
            }

            for(int i=1;i<sheet0.getPhysicalNumberOfRows()-1;i++){
                int count = 0;
                for(int j=i+1;j<sheet0.getPhysicalNumberOfRows();j++){
                    if(sheet0.getRow(i).getCell(2).getStringCellValue().equals(sheet0.getRow(j).getCell(2).getStringCellValue())){
                        count++;
                    }else{
                        break;
                    }
                }
            //    System.out.println(i+",,,"+count);
                if(count>0){
                    //创建合并单元格  ---begin
                    CellRangeAddress region = new CellRangeAddress(i, i+count, 0, 0);// 下标从0开始 起始行号，终止行号， 起始列号，终止列号
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 1, 1);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 2, 2);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 3, 3);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 4, 4);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 8, 8);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 9, 9);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 10, 10);
                    sheet0.addMergedRegion(region);
                    region = new CellRangeAddress(i, i+count, 11, 11);
                    sheet0.addMergedRegion(region);
                    i+=count;
                }
            }
            // 通过Response把数据以Excel格式保存
            response.reset();
            // 设置response流信息的头类型，MIME码
            response.setContentType("application/msexcel;charset=UTF-8");
            try {
                response.addHeader("Content-Disposition", "attachment;filename=\"" + new String((filename + ".xls").getBytes("GBK"), "ISO8859_1") + "\"");
                // 创建输出流对象
                OutputStream out = response.getOutputStream();
                // 将创建的Excel对象利用二进制流的形式强制输出到客户端去
                workBook.write(out);
                // 强制将数据从内存中保存
                out.flush();
                out.close();
          //      logger.warn("导出完成："+(System.currentTimeMillis()-t1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean isMerge(PoDO pd,PoDO pd1){
        return pd.getReseve1().equals(pd.getReseve1());
    }

    public CellStyle getHeaderStyle(Workbook wb){
        HSSFCellStyle cellStyle = (HSSFCellStyle)wb.createCellStyle();  //创建设置EXCEL表格样式对象 cellStyle

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/*        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中*/

        HSSFFont font = (HSSFFont)wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);//设置字体大小
   //     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setBold(true);

        cellStyle.setFont(font);//选择创建的字体格式

        return cellStyle;
    }
    public CellStyle getTextStyle(Workbook wb,int type){
        HSSFCellStyle cellStyle = (HSSFCellStyle)wb.createCellStyle();  //创建设置EXCEL表格样式对象 cellStyle

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/*        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中*/

        HSSFFont font = (HSSFFont)wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);//设置字体大小
        //     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示

        if(type == 5 || type == 7 || type == 8){
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        //    cellStyle.setDataFormat(HSSFCell.CELL_TYPE_NUMERIC);
        }
        cellStyle.setFont(font);//选择创建的字体格式

        return cellStyle;
    }
}
