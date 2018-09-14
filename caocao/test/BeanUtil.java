import com.fims.system.domain.UserDO;
import com.fims.system.vo.UserVO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


import javax.xml.bind.annotation.XmlElement;


public class  BeanUtil{


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        createTable(UserDO.class, null);
// createTable(Book.class, null);
    }
    public static void createTable(Class obj,String tableName) throws IOException{


        Field[] fields = null;
        fields = obj.getDeclaredFields();
        Class annotationType = null;
        Object param = null;
        String column = null;
        XmlElement xmlElement = null;
        StringBuilder sb = null;
        sb = new StringBuilder(50);
        if(tableName==null||tableName.equals("")){
//未传表明默认用类名
            tableName = obj.getName();
            tableName = tableName.substring(tableName.lastIndexOf(".")+1);
        }
        sb.append("\r\ndrop table if exists  ").append(tableName).append(";\r\n");
        sb.append("create table ").append(tableName).append(" ( \r\n");
//        System.out.println(tableName);
        boolean firstId = true;
        File file = null;
        for(Field f:fields){
            column = f.getName();
            sb.append(column).append(" ");
//            System.out.println(column+","+f.getType());
            param = f.getType();
//            sb.append(column);//一般第一个是主键
            if(param instanceof Integer){
                sb.append(" INTEGER ");
            }else{
                sb.append(" VARCHAR(30) ");//根据需要自行修改
            }
            if(firstId){//类型转换
                sb.append(" PRIMARY KEY ");
                firstId = false;
            }
            //获取字段中包含fieldMeta的注解
            //2、获取属性上的所有注释
            Annotation[] allAnnotations = f.getAnnotations();
            for(Annotation an : allAnnotations){
                sb.append(" COMMIT '");
                xmlElement = (XmlElement)an;
                annotationType = an.annotationType();
                param = ((XmlElement) an).name();
                System.out.println("属性【"+f.getName()+"-----的注释类型有: " + param);
                sb.append(param).append("'");
            }
            sb.append(",\n ");
        }
        String sql = null;
        sql = sb.toString();
        sql = sb.substring(0, sql.length()-1)+" )ENGINE =INNODB DEFAULT  CHARSET= utf8;\r\n";
        System.out.println(sql);
//        file = new File("建表.txt");
//        if (!file.getParentFile().exists()) {
//            if (!file.getParentFile().mkdirs()) {
//            }
//        }
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        System.out.println("文件路径:"+file.getAbsolutePath());
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
//        out.write(sql) ;
//        out.flush();
//        out.close() ;

    }

}