package nk.gk.wyl.mysql.util.sql;

import nk.gk.wyl.mysql.util.DataType;
import nk.gk.wyl.mysql.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.util.sql
 * @ClassName: InsertSql
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/17 21:19
 * @Version: 1.0
 */
public class InsertSql {
    // logger 日志
    private static Logger logger = LoggerFactory.getLogger(InsertSql.class);

    /**
     * 创建sql语句
     * @param table 表名
     * @param uid 用户编号
     * @param obj 新增对象参数
     * @return
     */
    public static String insertSql(String table, String uid, Map<String,Object> obj){
        // 插入当前用户和创建时间
        if(!obj.containsKey("create_by")){
            obj.put("create_by",uid);
        }
        if(!obj.containsKey("create_time")){
            obj.put("create_time",Util.getDate());
        }
        // 插入的sql 字段，字段值 Map
        Map<String, String> fields_values = getFieldsValuesSqlExact(obj);
        // 执行的sql语句
        String insert_sql = "insert into " + table + " (" + fields_values.get("fields_sql") + ") values(" + fields_values.get("values_sql") + ")";
        logger.info("sql：" + insert_sql);
        return insert_sql;
    }


    /**
     * 拼接生成精确（=）查询的sql语句
     *
     * @param map 查询条件
     * @return 返回 map  key fields_sql 是字段sql  values_sql 是值sql
     */
    public static Map<String, String> getFieldsValuesSqlExact(Map<String, Object> map) {
        // 响应结果
        Map<String, String> result = new HashMap<>();
        // 字段 sql  例如 id,name,type,sex
        String fields_sql = "";
        // 值sql
        String values_sql = "";
        // 循环插入的参数
        for (Map.Entry<String, Object> key : map.entrySet()) {
            String field = key.getKey();
            Object obj = key.getValue();
            String type = DataType.getDataType(obj);
            System.out.println("type="+type);
            if (StringUtils.isEmpty(fields_sql)) {// 判断是否是空
                fields_sql = field;
                if ("String".equals(type)) {// 字符串时插入数据
                    values_sql = "'" + obj.toString() + "'";
                } else if("Long".equals(type)
                        ||"Short".equals(type)
                        ||"Double".equals(type)
                        ||"Float".equals(type)
                        ||"Integer".equals(type)){
                    values_sql =  obj + "";
                }else if("Date".equals(type)){
                    values_sql =   "'" +  Util.getStrDate((Date) obj)+"'";
                }else {
                    values_sql = "'" + obj + "'";
                }
            } else {
                fields_sql = fields_sql + "," + field;
                if ("String".equals(type)) {// 字符串时插入数据
                    values_sql = values_sql + ",'" + obj.toString() + "'";
                } else if("Long".equals(type)
                        ||"Short".equals(type)
                        ||"Double".equals(type)
                        ||"Float".equals(type)
                        ||"Integer".equals(type)){
                    values_sql = values_sql + "," + obj + "";
                }else if("Date".equals(type)){
                    values_sql = values_sql + ",'" +  Util.getStrDate((Date) obj)+"'";
                }else {
                    values_sql = values_sql + ",'" + obj + "'";
                }
            }
        }
        result.put("fields_sql", fields_sql);
        result.put("values_sql", values_sql);
        return result;
    }
}
