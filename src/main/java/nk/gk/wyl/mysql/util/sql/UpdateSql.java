package nk.gk.wyl.mysql.util.sql;

import nk.gk.wyl.mysql.util.DataType;
import nk.gk.wyl.mysql.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.util.sql
 * @ClassName: UpdateSql
 * @Author: zsl
 * @Description: 更新sql
 * @Date: 2021/2/19 21:02
 * @Version: 1.0
 */
public class UpdateSql {
    // logger 日志
    private static Logger logger = LoggerFactory.getLogger(UpdateSql.class);

    /**
     * 根据id 更新sql 语句
     * @param table 表名
     * @param id 值
     * @param update 更新对象
     * @return
     */
    public static String sql (String table, Object id, Map<String,Object> update,String uid){
        if (update.containsKey("id")) {
            update.remove("id");
        }
        String updateSql = sql(update,uid);
        String sql = "update "+table+" set " + updateSql + " where id =";
        // id 暂时定 String  int
        if("Number".equals(DataType.getDataType(id))){
            sql = sql + id;
        }else{
            sql = sql + "'"+id+"'";
        }
        logger.info(sql);
        return sql;
    }

    /**
     * 根据id 更新sql 语句
     * @param update 更新对象
     * @return
     */
    public static String sql (Map<String,Object> update,String uid){
        if(!update.containsKey("update_by")){
            update.put("update_by",uid);
        }
        if(!update.containsKey("update_time")){
            update.put("update_time",Util.getDate());
        }
        // sql 语句
        String values_sql = "";
        // 循环更新的参数
        for (Map.Entry<String, Object> key : update.entrySet()) {
            String field = key.getKey();
            Object obj = key.getValue();
            String type = DataType.getDataType(obj);
            if (StringUtils.isEmpty(values_sql)) {// 判断是否是空
                if ("String".equals(type)) {// 字符串时插入数据
                    values_sql = field + "= '" + obj.toString() + "'";
                } else if("Long".equals(type)
                        ||"Short".equals(type)
                        ||"Double".equals(type)
                        ||"Float".equals(type)
                        ||"Integer".equals(type)){
                    values_sql = field + "= " + obj.toString() + "";
                }else if("Date".equals(type)){
                    values_sql = field + "= " + "to_date('"+ Util.getStrDate((Date) obj)+"','yyyy-MM-dd hh24:mi:ss')";
                }else {
                    values_sql = field + "= '" + obj + "'";
                }
            } else {
                if ("String".equals(type)) {// 字符串时插入数据
                    values_sql = values_sql + "," +field + "= '" + obj.toString() + "'";
                } else if("Long".equals(type)
                        ||"Short".equals(type)
                        ||"Double".equals(type)
                        ||"Float".equals(type)
                        ||"Integer".equals(type)){
                    values_sql = field + "= " + obj.toString() + "";
                }else if("Date".equals(type)){
                    values_sql = values_sql + "," + field + "= " + "to_date('"+ Util.getStrDate((Date) obj)+"','yyyy-MM-dd hh24:mi:ss')";
                }else {
                    values_sql =  values_sql + "," + field + "= '" + obj + "'";
                }
            }
        }
        return values_sql;
    }
}
