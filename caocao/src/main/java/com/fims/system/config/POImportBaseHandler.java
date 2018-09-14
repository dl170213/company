package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.domain.POImportBaseDO;
import com.fims.system.service.PoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/9/7.
 */
public class POImportBaseHandler implements IExcelVerifyHandler {
    @Autowired
    private PoService service ;

    public static PoService poservice;

    @PostConstruct
    public void init(){
        poservice = service;
    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {

        System.out.println("+++++++"+o.toString());
        POImportBaseDO podo = (POImportBaseDO)o;
        if(podo==null){
            return new ExcelVerifyHandlerResult(false,"空行");
        }
        /*Map<String,Object> map = new HashMap<String,Object>();
        if(poservice.count(map)>0){
            return new ExcelVerifyHandlerResult(false,"系统中存在PO数据，不允许导入");
        }*/
        if(!"".equals(podo.getReceiveddate())){//收到日期校验
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                System.out.println(df.format(df.parse(podo.getReceiveddate())));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,ex.getMessage());
            }
        }

        if(podo.getPm().length()>20){
            return new ExcelVerifyHandlerResult(false,"项目名称长度大于20");
        }

        if(!podo.getContractid().equals("")){
            if(podo.getContractid().length()>20){
                return new ExcelVerifyHandlerResult(false,"合同号长度大于20");
            }
            if(podo.getContractid().length()<6){
                return new ExcelVerifyHandlerResult(false,"合同号长度小于6");
            }
        }

        if(podo.getSp()!=null&&!"".equals(podo.getSp())&&podo.getSp().length()>50){
            return new ExcelVerifyHandlerResult(false,"SP序列长度大于50");
        }

        if(podo.getWbs_()!=null&&!"".equals(podo.getWbs_())&&podo.getWbs_().length()>100){
            return new ExcelVerifyHandlerResult(false,"WBS编号长度大于100");
        }

        /*//判断PO是否已存在
        //po可能存在多种
        //1. 正常PO
        //2. 负PO红冲
        //3. 再次下单正常PO
        //4. 可能存在一半PO生成SBI，另一半PO再次生成SBI
        if(!podo.getContractid().equals("")){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
            List<PoDO> list = poservice.getByNumber(podo.getContractid().replace("'",""));
            if(list!=null&&list.size()>0){
                //日期、合同号、数量、价格均一致时，才能确定两条数据一致
                for(PoDO pd:list){
                    String time1 =df.format(pd.getReceiveddate());
                    String time2 = "";
                    try {
                        time2 =df.format(format.parse(podo.getReceiveddate().replace("'","")));
                    }catch (Exception ex){
                        return new ExcelVerifyHandlerResult(false,ex.getMessage());
                    }
                    if(time1.equals(time2)){//日期相同
                        if(Long.parseLong(pd.getReseve1())==Long.parseLong(podo.getContractid().replace("'",""))){//合同号相同
                            if(pd.getCount()!=null&&!"".equals(pd.getCount())){
                                if(getRepaceString1(podo.getCount()).equals(pd.getCount())){//数量相同
                                    if(pd.getPice()!=null&&!"".equals(pd.getPice())&&getRepaceString1(podo.getPice()).equals(pd.getPice())){//价格相同
                                        return new ExcelVerifyHandlerResult(false,"PO数据已存在");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/

        if(podo.getCount()!=null&&!"".equals(podo.getCount())){

            try{
                System.out.println(Double.parseDouble(getRepaceString1(podo.getCount())));
            }catch(Exception ex){
                return new ExcelVerifyHandlerResult(false,"数量转格式失败");
            }
        }
        if(podo.getPice()!=null&&!"".equals(podo.getPice())){

            try{
                System.out.println(Double.parseDouble(getRepaceString1(podo.getPice())));
            }catch(Exception ex){
                return new ExcelVerifyHandlerResult(false,"单价转格式失败");
            }
        }

        if(podo.getTotal()!=null&&!"".equals(podo.getTotal())){

            try{
                System.out.println(Double.parseDouble(getRepaceString1(podo.getTotal())));
            }catch(Exception ex){
                return new ExcelVerifyHandlerResult(false,"总价转格式失败");
            }
        }

        if(podo.getWorkbody()!=null&&!"".equals(podo.getWorkbody())&&podo.getWorkbody().length()>100){
            return new ExcelVerifyHandlerResult(false,"工作内容长度大于100");
        }

        if(podo.getCustomer_()!=null&&!"".equals(podo.getCustomer_())&&podo.getCustomer_().length()>50){
            return new ExcelVerifyHandlerResult(false,"客户名称长度大于50");
        }

        if(podo.getWorktype()!=null&&!"".equals(podo.getWorktype())&&podo.getWorktype().length()>50){
            return new ExcelVerifyHandlerResult(false,"工作类型长度大于50");
        }

        //PoDO podo1 =  poservice.get(Long.parseLong("1"));
        return new ExcelVerifyHandlerResult(true);
    }

    public String getRepaceString1(String rs){
        return rs.replace("'","").replace(".","").replace(",",".").replace("，",".");
    }
}
