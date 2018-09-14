package com.fims.common.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelImportEnum;
import com.fims.common.domain.*;
import com.fims.common.service.ImportService;
import com.fims.common.utils.*;
import com.fims.system.config.*;
import com.fims.system.dao.InvoiceuserDao;
import com.fims.system.domain.*;
import com.fims.system.service.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImageConsumer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/7/27.
 */
@Service
public class ImportServiceImpl implements ImportService{

    @Autowired
    private PoService poService;
    @Autowired
    private SbiService sbiService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private UserService userService;
    @Autowired
    private PoSbiService posbiService;
    @Autowired
    private InvoiceuserDao invoiceuserDao;
    @Override
    public int importProduct(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(1);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new ExcelImportHandler());
        ExcelImportResult<ProductExportDO> eir = null;
        try{
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), ProductExportDO.class, params);
            FileOutputStream fos = new FileOutputStream("E:\\baseModetest.xls");
            eir.getWorkbook().write(fos);
            fos.close();
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(eir.isVerfiyFail()){

            System.out.println("----------校验失败---------"+eir.getFailList().size());
            for (ProductExportDO pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
                System.out.println();
            }
        }else{
            for (ProductExportDO pedo :eir.getList())
                System.out.println(pedo.toString());
        }

        return 0;
    }

    /*
    *  导入PO
    * */
    @Override
    public R importPO(MultipartFile file,String seachname) {
        Long xs = System.currentTimeMillis();
        Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(0);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new POImportHandler());
        //校验导入的模板是否正确
        params.setImportFields(new String[]{"'Delivery Date","项目名称","'Purchase Doc Number","SP",
                "'WBS","'Description","'Net Price","'Unconfirmed","'Plant","工作类型"});
        ExcelImportResult<POImportDO> eir = null;
        try{
            Memcach.EXCEL_STATUS.put(seachname,2);//改变状态，正在校验文件
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), POImportDO.class, params);
         //   FileOutputStream fos = new FileOutputStream("E:\\baseModetest.xls");
         //   eir.getWorkbook().write(fos);
         //   fos.close();
        }catch (ExcelImportException e){
            System.out.println(e.getType()+":"+e.getMessage());
            Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
            r.put("code",1);//校验异常
            if(e.getType().equals(ExcelImportEnum.IS_NOT_A_VALID_TEMPLATE)){
                r.put("msg","导入文件与模板格式不匹配，请确认无误后再次导入！");//校验异常
            }else{
                r.put("msg",e.getMessage());//校验异常
            }
            return r;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);//校验异常
            r.put("msg","导入失败!"+e.getMessage());//校验异常
            return r;
        }

        if(eir.isVerfiyFail()){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            System.out.println("----------校验失败---------"+eir.getFailList().size());
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            for (POImportDO pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
            }
            System.out.println(Memcach.EXCEL_STATUS.toString());
            r.put("failList",eir.getFailList());
        }else{
            Memcach.EXCEL_STATUS.put(seachname,3);//写入数据库
            Memcach.EXCEL_PROCESS.put(seachname,0+" / "+eir.getList().size());//设置进度

            if(eir.getList().size()==0){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！未查到数据！");//校验异常
                return r;
            }

            r = writeDatabasePO(eir,seachname);
        }
        Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
        System.out.println(System.currentTimeMillis()-xs);
        return r;
    }

    /**
     * PO数据写入数据库
     * */
    public R writeDatabasePO(ExcelImportResult<POImportDO> eir,String seachname){
        R r = new R();
        List<POImportDO> lsit = eir.getList();
        for (int i=0;i<lsit.size();i++){

            POImportDO poImportDO = lsit.get(i);
            PoDO podo = new PoDO();
            SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
            String timeStr = getRepaceString(poImportDO.getReceiveddate());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                podo.setReceiveddate(df.parse(df.format(format.parse(timeStr))));
            }catch (Exception ex){

                r.put("code",3);//导入失败
                r.put("msg","时间转换失败，行号："+i);//失败信息
                return r;
            }

            podo.setPm(getRepaceString(poImportDO.getPm()));//项目名称
            podo.setReseve1(Long.parseLong(getRepaceString(poImportDO.getContractid()))+"");//合同号
            if(poImportDO.getSp_()!=null){
                podo.setSp(getRepaceString(poImportDO.getSp_()));//SP
            }
            if(poImportDO.getWbs_()!=null){
                podo.setWbs(getRepaceString(poImportDO.getWbs_()));//WBS
            }
            podo.setCount(getRepaceString1(poImportDO.getCount()));//数量
            podo.setWorkbody(getRepaceString(poImportDO.getWorkbody()));//工作内容
            podo.setPice(getRepaceString1(poImportDO.getPice()));//单价
            //podo.setTotal(Double.parseDouble(podo.getCount())*Double.parseDouble(podo.getPice())+"");//总价
            podo.setTotal(DateUtils.mul(Double.parseDouble(podo.getCount()),Double.parseDouble(podo.getPice()))+"");//总价
            podo.setCustomer(getRepaceString(poImportDO.getCustomer_()));//客户名称
            if(poImportDO.getWorktype()!=null&&!"".equals(poImportDO.getWorktype())){//存在即填入
                podo.setWorktype(getRepaceString(poImportDO.getWorktype()));
            }else{
                if(poImportDO.getSp_()==null&&poImportDO.getWorktype()==null){//不存在，先判断SP和WorkType都为空==CARE
                    podo.setWorktype("CARE");
                }else if(poImportDO.getSp_().substring(0,2).equals("SP")){//不存在，SP首字母为SP则是NI
                    podo.setWorktype("NI");
                }
            }
     //       System.out.println("/-/-/-///-"+podo.toString());
            if(poService.save(podo)<=0){
                r.put("code",3);//校验成功
                r.put("msg","写入数据库失败，行号:"+(poImportDO.getRowNum()+1));//导入成功
            }
            Memcach.EXCEL_PROCESS.put(seachname,(i+1)+" / "+eir.getList().size());//设置进度
        }
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        return r;
    }

    /*
    *  导入SBI esaypoi
    * */
    @Override
    public R importSBI(MultipartFile file,String seachname) {
        Long xs = System.currentTimeMillis();
        Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        ImportParams params = new ImportParams();
        params.setHeadRows(0);
        params.setTitleRows(0);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new SBIImportHandler());
        ExcelImportResult<SBIImportDO> eir = null;
        try{
            Memcach.EXCEL_STATUS.put(seachname,2);//改变状态，正在校验文件
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), SBIImportDO.class, params);
            //   FileOutputStream fos = new FileOutputStream("E:\\baseModetest.xls");
            //   eir.getWorkbook().write(fos);
            //   fos.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);//校验异常
            r.put("msg","校验失败!"+e.getMessage());//校验异常
            return r;
        }
        if(eir.isVerfiyFail()){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            System.out.println("----------校验失败---------"+eir.getFailList().size());
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            for (SBIImportDO pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
            }
            System.out.println(Memcach.EXCEL_STATUS.toString());
            r.put("failList",eir.getFailList());
        }else{
            Memcach.EXCEL_STATUS.put(seachname,3);//写入数据库
            Memcach.EXCEL_PROCESS.put(seachname,0+" / "+eir.getList().size());//设置进度
            if(eir.getList().size()==0){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！未查到数据！");//校验异常
                return r;
            }
       //     r = writeDatabasePO(eir,seachname);
        }
        Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
        System.out.println(System.currentTimeMillis()-xs);
        return r;
    }

    /*
     *  导入SBI poi
     * */
    @Override
    public R importSBI1(MultipartFile file,String seachname) {
        Long xs = System.currentTimeMillis();
    //    Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        boolean ischeck = true;//校验文件是否通过
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = (Sheet)wb.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            int totalRows = sheet.getPhysicalNumberOfRows();
        //    int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            SbiDO sd = new SbiDO();
            String pms = "";
            String types = "";
            if(totalRows==0){
                r.put("code",1);
                r.put("msg","校验失败!文件中没有数据！");
                return r;
            }
            for(int i=0;i<totalRows;i++){
                row = sheet.getRow(i);
                System.out.print("# "+i+"\t");
                if(row==null){
                    totalRows++;
                    System.out.println();
                    continue;
                }
                Map<String,Object> map = new HashMap<>();
                String errormsg = "";
                for(int j=0;j<=17;j++){
                    String cell_string = getCellString(row.getCell(j));
                    if(i==8&&j==1){
                        if(cell_string==null){
                            errormsg+="客户名称不能为空！";
                        }else {
                            sd.setCustomer(getCustomer(getRepaceString(cell_string)));
                        }
                    }
                    if(i==14&&j==1){
                        if(cell_string==null){
                            errormsg+="SBI不能为空！";
                        }else{
                            sd.setSbi(Long.parseLong(getRepaceString(cell_string))+"");
                        }
                    }
                    if(i==15&&j==1){
                        if(cell_string==null){
                            errormsg+="生成日期不能为空！";
                        }else{
                            SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                             //   System.out.println(df.format(format.parse(cell_string.replace("'",""))));
                                sd.setCreatetime(df.parse(df.format(format.parse(cell_string.replace("'","")))));
                            }catch (Exception ex){
                                errormsg +=ex.getMessage()+"!";
                            }
                        }
                    }
                    if(i>25&&(j==8||j==11||j==13||j==15)){
                        System.out.println(getRepaceString(getCellString(row.getCell(0))));
                        if(getCellString(row.getCell(0))!=null&&!"".equals(getCellString(row.getCell(0)))){
                            if (j==8){
                                if((getCellString(row.getCell(8))==null||"".equals(getRepaceString(getCellString(row.getCell(8)))))){
                                    errormsg+="PO信息的合同号不能为空！";
                                }else{
                                    System.out.println(i+","+j+","+getRepaceString(getCellString(row.getCell(8))));
                                    List<PoDO> lpds = poService.getByNumber(Long.parseLong(getRepaceString(getCellString(row.getCell(8))))+"");
                                    if(lpds!=null&&lpds.size()>0){
                                        //       pms+=lpds.get(0).getPm();
                                        //       types+=lpds.get(0).getWorktype();
                                    }else{
                                        errormsg+="PO信息不存在！";
                                    }
                                }
                            }

                            if(getCellString(row.getCell(11))==null||"".equals(getRepaceString(getCellString(row.getCell(11))))){
                                errormsg+="PO信息的单价不能为空！";
                            }
                            if(getCellString(row.getCell(13))==null||"".equals(getRepaceString(getCellString(row.getCell(13))))){
                                errormsg+="PO信息的数量不能为空！";
                            }
                            if(getCellString(row.getCell(15))==null||"".equals(getRepaceString(getCellString(row.getCell(15))))){
                                errormsg+="PO信息的总价不能为空！";
                            }
                        }
                        if(j==15){
                            if(getCellString(row.getCell(10))!=null&&getCellString(row.getCell(10)).indexOf("TotalNetValue")!=-1){
                                if(getCellString(row.getCell(15))==null||"".equals(getRepaceString(getCellString(row.getCell(15))))){
                                    errormsg+="税前金额不能为空！";
                                }else{
                                    sd.setPretax(getRepaceString(getCellString(row.getCell(15))));
                                }
                            }
                            if(getCellString(row.getCell(10))!=null&&getCellString(row.getCell(10)).indexOf("Sales tax")!=-1){
                                if(getCellString(row.getCell(15))==null||"".equals(getRepaceString(getCellString(row.getCell(15))))){
                                    errormsg+="税收金额不能为空！";
                                }else{
                                    sd.setTax(getRepaceString(getCellString(row.getCell(15))));
                                }
                            }
                            if(getCellString(row.getCell(10))!=null&&getCellString(row.getCell(10)).indexOf("FinalAmount")!=-1){
                                if(getCellString(row.getCell(15))==null||"".equals(getRepaceString(getCellString(row.getCell(15))))){
                                    errormsg+="税后金额不能为空！";
                                }else{
                                    sd.setPosttax(getRepaceString(getCellString(row.getCell(15))));
                                }
                            }
                        }
                    }
                }
                if(!"".equals(errormsg)){//增加校验错误信息
                    map.put("errorMsg",errormsg);
                    if(i<23){
                        map.put("rowNum",i+1);
                    }else{
                        map.put("rowNum",i);
                    }

                    list.add(map);
                    ischeck = false;
                }
                System.out.println();
            }
            if(ischeck){
                System.out.println(sd.toString());
            }else{//校验失败
                r.put("code",2);
                r.put("msg","校验失败!");
                r.put("failList",list);
                Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            }
        }catch(IOException ie){
            if(ie.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//shibai
                r.put("code",1);//校验异常
                r.put("msg","导入失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);
            r.put("msg","校验异常!"+ie.getMessage());
            return r;
        }
        return r;
    }

    /*
     *  导入SBI2 poi
     * */
    @Override
    public R importSBI2(MultipartFile file,String seachname) {
        Long xs = System.currentTimeMillis();
        //    Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        boolean ischeck = true;//校验文件是否通过
        int step = 4;//导入文件类型，4：负数；2：正数
        List<Map<String,Object>> list = new ArrayList<>();//统计错误信息
        List<PoDO> list_po = new ArrayList<>();//如果是负数类型，统计新的PO数据
        String pos = "";
        try {
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = (Sheet)wb.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            int totalRows = sheet.getPhysicalNumberOfRows();
            //    int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            SbiDO sd = new SbiDO();
            String pms = "";
            String types = "";
            if(totalRows==0){
                r.put("code",1);
                r.put("msg","校验异常!文件中没有数据！");
                return r;
            }
            for(int i=0;i<totalRows;i++){
                row = sheet.getRow(i);
                System.out.print("# "+i+"\t");
                if(row==null){
                    totalRows++;
                    System.out.println();
                    continue;
                }

                String errormsg = "";
                String cell_string = "";
                if(i==8){
                    cell_string = getCellString(row.getCell(1));
                    if(cell_string==null){
                        errormsg+="客户名称不能为空！";
                    }else {
                        sd.setCustomer(getCustomer(getRepaceString(cell_string)));
                    }
                }
                if(i==14){
                    cell_string = getCellString(row.getCell(1));
                    if(cell_string==null){
                        errormsg+="SBI不能为空！";
                    }else{
                        try{
                            sd.setSbi(Long.parseLong(getRepaceString(cell_string))+"");
                            if(sbiService.getBySbi(sd.getSbi())!=null){
                                errormsg+="SBI已存在！";
                            }
                        }catch (Exception ex){
                            errormsg+="SBI格式错误！"+ex.getMessage();
                        }
                    }
                }
                if(i==15){
                    cell_string = getCellString(row.getCell(1));
                    if(cell_string==null){
                        errormsg+="生成日期不能为空！";
                    }else{
                        SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            //   System.out.println(df.format(format.parse(cell_string.replace("'",""))));
                            sd.setReseve1(df.parse(df.format(format.parse(cell_string.replace("'","")))));
                        }catch (Exception ex){
                            errormsg +=ex.getMessage()+"!";
                        }
                    }
                }
                if(i>23){
                    //进入PO解析
                    cell_string = getCellString(sheet.getRow(i).getCell(0));
                    if(cell_string!=null&&!"".equals(getRepaceString(cell_string))&&"Plant".equals(getRepaceString(cell_string))){

                        step = 1;//正数类型每次+1
                        if(sheet.getRow(i+1)!=null&&sheet.getRow(i+1).getCell(8)!=null){
                            String cell_ = getCellString(sheet.getRow(i+1).getCell(8));
                            if(cell_!=null&&!"".equals(getRepaceString(cell_))&&
                                    getRepaceString(cell_).indexOf("The item above includes the following")!=-1){
                                System.out.println("负数类型");
                                step = 4;
                            }
                        }

                        int index =i+1;//获取第一个PO数据
                        while(true){
                            errormsg = "";
                            Row row1 = sheet.getRow(index);
                            if(row1==null){
                                //数据不存在
                                break;
                            }
                            String cell_s = getCellString(row1.getCell(0));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                break;
                            }
                            cell_s = getCellString(row1.getCell(2));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="工作内容不能为空！";
                            }
                            cell_s = getCellString(row1.getCell(11));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="PO信息数量不能为空！";
                            }
                            cell_s = getCellString(row1.getCell(13));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="PO信息价格不能为空！";
                            }
                            cell_s = getCellString(row1.getCell(15));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="PO信息总价不能为空！";
                            }
                            List<PoDO> lpds = null;
                            cell_s = getCellString(row1.getCell(8));
                            if(cell_s!=null&&!"".equals(getRepaceString(cell_s))){
                                try{
                                    Map<String,Object> map = new HashMap<>(16);
                                    map.put("reseve1",Long.parseLong(getRepaceString(cell_s))+"");
                                    map.put("workbody",getCellString(row1.getCell(2)));
                                    //查询未生成SBI，且工作内容相同的PO数据
                                    lpds = poService.getByNumberAndSBInull(map);
                                }catch(Exception ex){
                                    errormsg+="PO合同号格式错误！";
                                }
                                if(lpds!=null&&lpds.size()>0){
                                    if(errormsg.equals("")){//更新新的PO数据

                                        //先按每条数据不可能重复去做
                                        PoDO po = lpds.get(0);
                                        if(sd.getSbi()!=null){
                                            po.setSbi(sd.getSbi());
                                        }
                                        if(step==4){//负数
                                            po.setCount("-"+getRepaceString1(getCellString(row1.getCell(11))));
                                            po.setPice(getRepaceString1(getCellString(row1.getCell(13))));
                                            po.setTotal(getRepaceString1(getCellString(row1.getCell(15))));
                                        }else{

                                            //正数时判断数量是否正确
                                            BigDecimal a =new BigDecimal(Double.parseDouble(po.getCount()));
                                            BigDecimal b =new BigDecimal(getRepaceString1(getCellString(row1.getCell(11))));
                                            if(a.compareTo(b)<0){//SBI中PO数据的数量大于PO中的数量
                                                errormsg+="PO："+getCellString(row1.getCell(8))+"的数量大于系统中该PO的数量";
                                            }
                                            if(a.compareTo(b) == 0){//SBI中PO数据的数量大于PO中的数量
                                                po.setReseve2("0");
                                            }
                                            if(a.compareTo(b) > 0){//SBI中PO数据的数量大于PO中的数量
                                                po.setReseve2("1");

                                        //        String s = getRepaceString1(getCellString(row1.getCell(11)));
                                                System.out.println(getRepaceString1(getCellString(row1.getCell(11))));
                                                po.setReseve3(getRepaceString1(getCellString(row1.getCell(11)))+"");

                                            }
                                        }
                                        list_po.add(po);
                                    }
                                }else{
                                    errormsg+="PO信息不存在或已生成SBI！";
                                }
                            }
                            if(!"".equals(errormsg)){//新增错误信息
                                Map<String,Object> map = new HashMap<>();
                                map.put("errorMsg",errormsg);
                                map.put("rowNum",index);
                                list.add(map);
                                ischeck = false;
                            }
                            index+=step;
                        }
                        index+=3;
                        if(step==1){
                            index--;
                        }
                        Row row1 = sheet.getRow(index);//判断税前总价
                        String cell_s = getCellString(row1.getCell(10));
                        if(cell_s!=null&&cell_s.contains("TotalNetValue")){
                            cell_s = getCellString(row1.getCell(15));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="SBI税前金额不能为空！";
                            }else{
                                sd.setPretax(getRepaceString1(cell_s));
                            }
                        }else{
                            errormsg+="SBI税前金额不存在！";
                        }
                        row1 = sheet.getRow(index+1);//判断税价
                        cell_s = getCellString(row1.getCell(10));
                        if(cell_s!=null&&cell_s.contains("Sales tax")){
                            cell_s = getCellString(row1.getCell(15));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="SBI税收金额不能为空！";
                            }else{
                                sd.setTax(getRepaceString1(cell_s));
                            }
                        }else{
                            errormsg+="SBI税收金额不存在！";
                        }
                        row1 = sheet.getRow(index+3);//判断税价
                        cell_s = getCellString(row1.getCell(10));
                        if(cell_s!=null&&cell_s.contains("FinalAmount")){
                            cell_s = getCellString(row1.getCell(15));
                            if(cell_s==null||"".equals(getRepaceString(cell_s))){
                                errormsg+="SBI税后金额不能为空！";
                            }
                            else{
                                sd.setPosttax(getRepaceString1(cell_s));
                            }
                        }else{
                            errormsg+="SBI税后金额不存在！";
                        }
                    }
                }else if(!"".equals(errormsg)){//增加校验错误信息
                    Map<String,Object> map = new HashMap<>();
                    map.put("errorMsg",errormsg);
                    map.put("rowNum",i+1);
                    list.add(map);
                    ischeck = false;
                }
                if(sd.getPosttax()!=null){
                    break;
                }
                System.out.println();
            }
            if(ischeck){
                System.out.println(sd.toString());
                //开始写入数据库
                r  = writeDataSbi(sd,list_po,step);
            }else{//校验失败
                r.put("code",2);
                r.put("msg","校验失败!");
                r.put("failList",list);
                Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            }
        }catch(IOException ie){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            r.put("code",1);
            r.put("msg","校验失败!"+ie.getMessage());
            return r;
        }
        return r;
    }

    public R writeDataSbi(SbiDO sbi,List<PoDO> list_po,int step){
        R r = new R();
        r.put("code",0);
        r.put("msg","操作成功");
        //新增sbi
        //修改或者新增po
        String pms = "";
        String wktys = "";
        String po = "";
        if(step==4){//如果是负数PO需要新增数据
            for (int i=0;i<list_po.size();i++){

                if(poService.save(list_po.get(i))<=0){
                 //   removePO_SBi(sbi.getSbi());
                    r.put("code",3);
                    r.put("msg","写入数据库失败！第 "+(i+1)+" 条PO数据");
                    return r;
                }
                if(pms.indexOf(list_po.get(i).getPm())==-1){
                    pms+=list_po.get(i).getPm()+",";
                }
                if(wktys.indexOf(list_po.get(i).getWorktype())==-1){
                    wktys+=list_po.get(i).getWorktype()+",";
                }
            }
        }else{
            for (int i=0;i<list_po.size();i++){

                //判断PO数量是不是相等
                if(list_po.get(i).getReseve2().equals("0")){//相等
                    list_po.get(i).setReseve2(null);
                    //这个位置可能不需要更新po数据
                    if(poService.update(list_po.get(i))<=0){
                        //     removePO_SBi(sbi.getSbi());
                        r.put("code",3);
                        r.put("msg","更新数据库失败！第 "+(i+1)+" 条PO数据");
                        return r;
                    }
                }else if(list_po.get(i).getReseve2().equals("1")){//小于系统值
                    //按当前值更新PO数据
                    PoDO pd = new PoDO();
                    pd.setId(list_po.get(i).getId());
                    pd.setSbi(list_po.get(i).getSbi());
                    pd.setCount(list_po.get(i).getReseve3());
                    double a = Double.parseDouble(list_po.get(i).getReseve3());
                    double b = Double.parseDouble(list_po.get(i).getPice());
                    pd.setTotal(DateUtils.mul(a,b)+"");

                    if(poService.update(pd)<=0){
                        //     removePO_SBi(sbi.getSbi());
                        r.put("code",3);
                        r.put("msg","更新数据库失败！第 "+(i+1)+" 条PO数据");
                        return r;
                    }
                    PoDO pd1 = new PoDO();
               //     pd1.setSbi(list_po.get(i).getSbi());
                    pd1.setCustomer(list_po.get(i).getCustomer());
                    pd1.setWorktype(list_po.get(i).getWorktype());
                    pd1.setPm(list_po.get(i).getPm());
                    pd1.setReseve1(list_po.get(i).getReseve1());
                    pd1.setReceiveddate(list_po.get(i).getReceiveddate());
                    pd1.setSp(list_po.get(i).getSp());
                    pd1.setWbs(list_po.get(i).getWbs());
                    pd1.setWorkbody(list_po.get(i).getWorkbody());
                    pd1.setPice(list_po.get(i).getPice());
                    //计算PO数量剩余个数插入系统
                    pd1.setCount(DateUtils.subtract(Double.parseDouble(list_po.get(i).getCount()),Double.parseDouble(list_po.get(i).getReseve3()))+"");
                    pd1.setTotal(DateUtils.mul(Double.parseDouble(pd1.getCount()),Double.parseDouble(list_po.get(i).getPice()))+"");
                    if(poService.save(pd1)<=0){
                        //     removePO_SBi(sbi.getSbi());
                        r.put("code",3);
                        r.put("msg","更新数据库失败！第 "+(i+1)+" 条PO数据");
                        return r;
                    }
                }

                if(pms.indexOf(list_po.get(i).getPm())==-1){
                    pms+=list_po.get(i).getPm()+",";
                }
                if(wktys.indexOf(list_po.get(i).getWorktype())==-1){
                    wktys+=list_po.get(i).getWorktype()+",";
                }

            }
        }
        sbi.setPm(pms);
        sbi.setWorktype(wktys);
        sbi.setCreatetime(new Date());
        if(sbiService.save(sbi)<=0){
            r.put("code",3);
            r.put("msg","SBI信息写入数据库失败！");
            return r;
        }

        return r;
    }

    /*
    * 删除相关连数据
    * **/
    public void removePO_SBi(String sbiid){
        posbiService.removeBySbi(sbiid);
    }

    /*
    *  导入Check
    * */
    @Override
    public R importCheck(MultipartFile file,String seachname,String creatid) {
        Long xs = System.currentTimeMillis();
        Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(0);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new CheckImportHandler());
        params.setImportFields(new String []{"月份","发票号","姓名","工号"});
        ExcelImportResult<CheckImportDo> eir = null;
        try{
            Memcach.EXCEL_STATUS.put(seachname,2);//改变状态，正在校验文件
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), CheckImportDo.class, params);
            //   FileOutputStream fos = new FileOutputStream("E:\\baseModetest.xls");
            //   eir.getWorkbook().write(fos);
            //   fos.close();
        }catch (ExcelImportException e){
            System.out.println(e.getType()+":"+e.getMessage());
            Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
            r.put("code",1);//校验异常
            if(e.getType().equals(ExcelImportEnum.IS_NOT_A_VALID_TEMPLATE)){
                r.put("msg","导入文件与模板格式不匹配，请确认无误后再次导入！");//校验异常
            }else{
                r.put("msg",e.getMessage());//校验异常
            }
            return r;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","校验失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);//校验异常
            r.put("msg","校验失败!"+e.getMessage());//校验异常
            return r;
        }


        if(eir.isVerfiyFail()){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            System.out.println("----------校验失败---------"+eir.getFailList().size());
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            for (CheckImportDo pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
            }
            System.out.println(Memcach.EXCEL_STATUS.toString());
            r.put("failList",eir.getFailList());
        }else{
            Memcach.EXCEL_STATUS.put(seachname,3);//写入数据库
            Memcach.EXCEL_PROCESS.put(seachname,0+" / "+eir.getList().size());//设置进度
            if(eir.getList().size()==0){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！未查到数据！");//校验异常
                return r;
            }
            r = writeDatabaseCheck(eir,seachname,creatid);
        }
        Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
        System.out.println(System.currentTimeMillis()-xs);
        return r;
    }

    /**
     * 发票数据写入数据库
     * */
    public R writeDatabaseCheck(ExcelImportResult<CheckImportDo> eir,String seachname,String creatid){
        R r = new R();
        List<CheckImportDo> lsit = eir.getList();
        for (int i=0;i<lsit.size();i++){

            CheckImportDo checkImportDO = lsit.get(i);
            CheckDO cd = new CheckDO();
            if(checkImportDO.getMonth()!=null&&!"".equals(checkImportDO.getMonth())){
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cd .setCreatetime(sdf.parse(checkImportDO.getMonth().replace(".","-")+"-15"));
                }catch (Exception ex){
                    r.put("code",0);//校验成功
                    r.put("msg","时间转化失败，行号:"+(checkImportDO.getRowNum()+1));//导入成功
                    return r;
                }
            }
            cd.setInvoicenumber(checkImportDO.getNumber());
            if(checkImportDO.getName()!=null&&!"".equals(checkImportDO.getName())){
                cd.setUserIdExpense(checkImportDO.getName());
                cd.setUsername(checkImportDO.getUsername());
            }
            cd.setUserIdCreate(creatid);
            cd.setStatus("0");//未删除
            if(checkService.getByNumber(checkImportDO.getNumber()).size()>0){
                cd.setRepeat("1");//如果是已存在的发票，设置为重复
            }else{
                cd.setRepeat("0");
            }
            if(checkService.save(cd)<=0){
                r.put("code",3);//校验成功
                r.put("msg","写入数据库失败，行号:"+(checkImportDO.getRowNum()+1));//导入成功
            }else if(cd.getUserIdExpense()!=null&&!"".equals(cd.getUserIdExpense())){
                //根据报销员工判断，该员工是否已经维护到系统中，如果没有，需要维护
                /*if(invoiceuserDao.getByUsername(cd.getUserIdExpense())==null){
                    invoiceuserDao.save(new InvoiceuserDO(cd.getUserIdExpense()));
                }*/
                if(userService.getByUsername(cd.getUsername())==null){
                    UserDO ud = new UserDO();
                    ud.setName(cd.getUserIdExpense());
                    ud.setUsername(cd.getUsername());
                    ud.setDeptId(Long.parseLong("0"));
                    ud.setStatus(0);
                    ud.setPassword("111111");
                    ud.setPassword(MD5Utils.encrypt(ud.getUsername(), ud.getPassword()));
                    if(userService.save(ud)<=0){
                        r.put("code",3);//校验成功
                        r.put("msg","新增员工账号失败，行号:"+(checkImportDO.getRowNum()+1));//导入成功
                    }
                }
            }
            Memcach.EXCEL_PROCESS.put(seachname,(i+1)+" / "+eir.getList().size());//设置进度
        }
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        return r;
    }

    /*
  *  导入SBI/PO 基础数据
  * */
    @Override
    public R importPOSBI(MultipartFile file,String seachname) {
        Long xs = System.currentTimeMillis();
        Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        R r = new R();
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(0);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new SBIImportBaseHandler());
        ExcelImportResult<SBIImportBaseDO> eir = null;
        try{
            Memcach.EXCEL_STATUS.put(seachname,2);//改变状态，正在校验文件
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), SBIImportBaseDO.class, params);
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);//校验异常
            r.put("msg","校验失败!"+e.getMessage());//校验异常
            return r;
        }

        if(eir.isVerfiyFail()){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            System.out.println("----------校验失败---------"+eir.getFailList().size());
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            for (SBIImportBaseDO pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
            }
            System.out.println(Memcach.EXCEL_STATUS.toString());
            r.put("failList",eir.getFailList());
        }else{
            Memcach.EXCEL_STATUS.put(seachname,3);//写入数据库
            Memcach.EXCEL_PROCESS.put(seachname,0+" / "+eir.getList().size());//设置进度
            if(eir.getList().size()==0){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！未查到数据！");//校验异常
                return r;
            }
            r = writeDatabasePOSBI(eir,seachname);
        }
        Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
        System.out.println(System.currentTimeMillis()-xs);
        return r;
    }

    /**
     * POSBI数据写入数据库
     * */
    public R writeDatabasePOSBI(ExcelImportResult<SBIImportBaseDO> eir,String seachname){
        R r = new R();
        List<SBIImportBaseDO> lsit = eir.getList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        for (int i=0;i<lsit.size();){
            SBIImportBaseDO sbiImportDO = lsit.get(i);
            if(sbiImportDO.getSbi()!=null&&!"".equals(sbiImportDO.getSbi())){//sbi存在，新建sbi
                String pms = "";
                String types = "";
                int index = 0;
                double post = 0.00;
                SbiDO sd = new SbiDO();
                sd.setSbi(sbiImportDO.getSbi());
                for (int j=i;j<lsit.size();j++){
                    if(sd.getSbi().equals(lsit.get(j).getSbi())){//相同的sbi
                        post = DateUtils.add(DateUtils.string22double(lsit.get(j).getPosttax()),post);
                        if(pms.indexOf(lsit.get(j).getPm())==-1){
                            pms+=lsit.get(j).getPm()+",";
                        }
                        if(types.indexOf(lsit.get(j).getWorktype())==-1){
                            types+=lsit.get(j).getWorktype()+",";
                        }
                        //整理PO信息
                        PoDO po = new PoDO();
                        try{
                            po.setReceiveddate(sdf.parse(lsit.get(j).getRecivedate()));
                        }catch (ParseException pe){
                            r.put("code",3);//校验成功
                            r.put("msg","写入收到日期时转换失败，行号："+lsit.get(j).getRowNum());//导入成功
                            return r;
                        }
                        po.setPm(lsit.get(j).getPm());
                        po.setReseve1(lsit.get(j).getContract());
                        po.setTotal(DateUtils.string22doubleString(lsit.get(j).getPosttax()));
                        po.setWorktype(lsit.get(j).getWorktype());
                        /*if(lsit.get(j).getSbi()!=null&&!"".equals(lsit.get(j).getSbi())){
                            po.setSbi(lsit.get(j).getSbi());
                        }*/
                        if(poService.save(po)<=0){
                            r.put("code",3);//校验成功
                            r.put("msg","导入PO数据失败，行号："+lsit.get(j).getRowNum());//导入成功
                            return r;
                        }
                        //保存po _sbi关联数据
                        PoSbiDO psd = new PoSbiDO();
                        psd.setPoid(po.getReseve1());
                        psd.setSbiid(lsit.get(j).getSbi());
                        posbiService.save(psd);

                        lsit.remove(j);//清除相同的数据
                        j--;//保持当前索引位置
                        index++;//去掉数据源中已保存的Po数据
                        Memcach.EXCEL_PROCESS.put(seachname,(i+1)+" / "+eir.getList().size());//设置进度
                    }
                }
                sd.setPm(pms);
                sd.setWorktype(types);
                sd.setPosttax(post+"");
                sd.setCreatetime(new Date());
                try {
                    sd.setReseve1(sdf1.parse(sbiImportDO.getGenerate()));
                }catch (ParseException pe){
                    r.put("code",3);//校验成功
                    r.put("msg","导入SBI数据失败，SBI生成日期格式错误，行号："+sbiImportDO.getRowNum());//导入成功
                    return r;
                }
                if(sbiService.save(sd)<=0){
                    r.put("code",3);//校验成功
                    r.put("msg","导入SBI数据失败，行号："+sbiImportDO.getRowNum());//导入成功
                    return r;
                }
              //  i+=index;
            }else{//未生成SBI 的数据
                PoDO po = new PoDO();
                try{
                    po.setReceiveddate(sdf.parse(sbiImportDO.getRecivedate()));
                }catch (ParseException pe){
                    r.put("code",3);//校验成功
                    r.put("msg","写入收到日期时转换失败，行号："+sbiImportDO.getRowNum());//导入成功
                    return r;
                }
                po.setPm(sbiImportDO.getPm());
                po.setReseve1(sbiImportDO.getContract());
                po.setTotal(DateUtils.string22doubleString(sbiImportDO.getPosttax()));
                po.setWorktype(sbiImportDO.getWorktype());
                if(poService.save(po)<=0){
                    r.put("code",3);//校验成功
                    r.put("msg","导入PO数据失败，行号："+sbiImportDO.getRowNum());//导入成功
                    return r;
                }
                Memcach.EXCEL_PROCESS.put(seachname,(i+1)+" / "+eir.getList().size());//设置进度
                i++;
            }
        }
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        return r;
    }

    /*
     *  导入PO 基础数据(未生成SBI的所有数据)
     * */
    @Override
    public R importPOBase(MultipartFile file,String seachname) {
        R r = new R();
        //判断系统中是否已经有PO数据，如果已经有数据将退出，导出失败
        Map<String,Object> map = new HashMap<>(16);
        if(poService.count(map)>0){
            List<Map<String,Object>> el = new ArrayList<>();
            map.put("rowNum"," ");
            map.put("errorMsg","系统中已经有PO数据，不允许导入PO基础数据！");
            el.add(map);
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            r.put("failList",el);
            Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
            return r;
        }

        Long xs = System.currentTimeMillis();
        Memcach.EXCEL_STATUS.put(seachname,1);//正在加载文件
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(0);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new POImportBaseHandler());
        params.setImportFields(new String[]{"收到日期","项目名称","合同号","SP序列","WBS编号","数量",
                "工作内容","单价","总价","客户名称","工作类型"});
        ExcelImportResult<POImportBaseDO> eir = null;
        try{
            Memcach.EXCEL_STATUS.put(seachname,2);//改变状态，正在校验文件
            eir= ExcelImportUtil.importExcelMore(file.getInputStream(), POImportBaseDO.class, params);
        }catch (ExcelImportException e){
            System.out.println(e.getType()+":"+e.getMessage());
            Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
            r.put("code",1);//校验异常
            if(e.getType().equals(ExcelImportEnum.IS_NOT_A_VALID_TEMPLATE)){
                r.put("msg","导入文件与模板格式不匹配，请确认无误后再次导入！");//校验异常
            }else{
                r.put("msg",e.getMessage());//校验异常
            }
            return r;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage()==null){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！空文件！");//校验异常
                return r;
            }
            r.put("code",1);//校验异常
            r.put("msg","校验失败!"+e.getMessage());//校验异常
            return r;
        }

        if(eir.isVerfiyFail()){
            Memcach.EXCEL_STATUS.put(seachname,-1);//校验失败
            System.out.println("----------校验失败---------"+eir.getFailList().size());
            r.put("code",2);//校验失败
            r.put("msg","校验失败");//校验失败
            for (POImportBaseDO pedo :eir.getFailList()){
                System.out.println(pedo.getErrorMsg()+",,,"+(pedo.getRowNum()+1)+",,,"+pedo.toString());
            }
            System.out.println(Memcach.EXCEL_STATUS.toString());
            r.put("failList",eir.getFailList());
        }else{
            Memcach.EXCEL_STATUS.put(seachname,3);//写入数据库
            Memcach.EXCEL_PROCESS.put(seachname,0+" / "+eir.getList().size());//设置进度
            if(eir.getList().size()==0){
                Memcach.EXCEL_STATUS.put(seachname,-1);//操作完毕
                r.put("code",1);//校验异常
                r.put("msg","导入失败！未查到数据！");//校验异常
                return r;
            }
            r = writeDatabasePOBase(eir,seachname);
        }
        Memcach.EXCEL_STATUS.put(seachname,4);//操作完毕
        System.out.println(System.currentTimeMillis()-xs);
        return r;
    }

    /**
     * PO基础数据写入数据库
     * */
    public R writeDatabasePOBase(ExcelImportResult<POImportBaseDO> eir,String seachname){
        R r = new R();
        List<POImportBaseDO> lsit = eir.getList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0;i<lsit.size();i++){
            POImportBaseDO poImportDO = lsit.get(i);
            PoDO podo = new PoDO();
            try {
                podo.setReceiveddate(df.parse(poImportDO.getReceiveddate()));
            }catch (Exception ex){
                r.put("code",3);//导入失败
                r.put("msg","时间转换失败，行号："+i);//失败信息
                return r;
            }
            podo.setPm(getRepaceString(poImportDO.getPm()));//项目名称
            podo.setReseve1(Long.parseLong(getRepaceString(poImportDO.getContractid()))+"");//合同号
            if(poImportDO.getSp()!=null){
                podo.setSp(getRepaceString(poImportDO.getSp()));//SP
            }
            if(poImportDO.getWbs_()!=null){
                podo.setWbs(getRepaceString(poImportDO.getWbs_()));//WBS
            }
            podo.setCount(stringToDouble2(getRepaceString(poImportDO.getCount())));//数量
            podo.setWorkbody(getRepaceString(poImportDO.getWorkbody()));//工作内容
            podo.setPice(stringToDouble2(getRepaceString(poImportDO.getPice())));//单价
            podo.setTotal(stringToDouble2(getRepaceString(poImportDO.getTotal())));//总价
            podo.setCustomer(getRepaceString(poImportDO.getCustomer_()));//客户名称
            if(poImportDO.getWorktype()!=null&&!"".equals(poImportDO.getWorktype())){//存在即填入
                podo.setWorktype(getRepaceString(poImportDO.getWorktype()));
            }else{
                if(poImportDO.getSp()==null&&poImportDO.getWorktype()==null){//不存在，先判断SP和WorkType都为空==CARE
                    podo.setWorktype("CARE");
                }else if(poImportDO.getSp().substring(0,2).equals("SP")){//不存在，SP首字母为SP则是NI
                    podo.setWorktype("NI");
                }
            }
            //       System.out.println("/-/-/-///-"+podo.toString());
            if(poService.save(podo)<=0){
                r.put("code",3);//校验成功
                r.put("msg","写入数据库失败，行号:"+(poImportDO.getRowNum()+1));//导入成功
            }
            Memcach.EXCEL_PROCESS.put(seachname,(i+1)+" / "+eir.getList().size());//设置进度
        }
        r.put("code",0);//校验成功
        r.put("msg","操作成功");//导入成功
        return r;
    }

    public String  getCellString(Cell cell){
        String cellValue = "";
        if(null != cell){
            // 以下是判断数据的类型
            switch (cell.getCellType())
            {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    //	System.out.print("-- > 数字");
                    cellValue = cell.getNumericCellValue() + "";
                    //时间格式
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        Date dd = cell.getDateCellValue();
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellValue = df.format(dd);
                    }

                    if(cellValue.substring(cellValue.length()-2, cellValue.length()).equals(".0")){
                        cellValue=cellValue.substring(0, cellValue.length()-2);
                    }
                    break;

                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    //	System.out.print("-- >字符串");
                    cellValue = cell.getStringCellValue();
                    break;

                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;

                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;

                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellValue = null;
                    break;

                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellValue = "非法字符";
                    break;

                default:
                    cellValue = "未知类型";
                    break;
            }

            System.out.print(""+cellValue+"\t");
        }
        return cellValue;
    }

    public String getRepaceString(String rs){
        return rs.replace("'","");
    }

    public String getRepaceString1(String rs){
        return rs.replace("'","").replace(".","").replace(",",".").replace("，",".");
    }

    public String getCustomer(String customer){
        if(customer.indexOf("Beijing")!=-1){
            return "诺基亚通信系统技术（北京）有限公司";
        }else if(customer.indexOf("Shanghai")!=-1){
            return "诺基亚通信（上海）有限公司";
        }else {
            return "诺基亚通信网络科技服务有限公司";
        }
    }

    /*
    * 小数字符串转2位小数字符串
    * */
    public String stringToDouble2(String str){
        Double cny = Double.parseDouble(str);//转换成Double
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        return df.format(cny);
    }
}
