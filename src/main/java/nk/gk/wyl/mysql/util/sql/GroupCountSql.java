package nk.gk.wyl.mysql.util.sql;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.api
 * @ClassName: MysqlGroupService
 * @Author: zsl
 * @Description: 分组统计接口
 * @Date: 2021/2/18 14:11
 * @Version: 1.0
 */
public class GroupCountSql {
    /**
     * 分组统计sql
     * @param table
     * @param fields
     * @param exact_search
     * @param search
     * @param in_search
     * @param order
     * @param rang_search
     * @param size
     * @param is_logic true/false 是否是逻辑删除
     * @param is_delete_flag  逻辑删除时的字段
     * @param is_delete_flag_value  逻辑删除时的字段值
     * @return
     */
    public static String sql(String table, String[] fields, Map<String, Object> exact_search,
                             Map<String, String> search,
                             Map<String, List<String>> in_search,
                             Map<String, String> order,
                             Map<String, Map<String, Object>> rang_search,
                             int size,
                             boolean is_logic,
                             String is_delete_flag,
                             String is_delete_flag_value) {
        String resultSql = SelectSql.getSelectSqlFields(fields) ;
        // 获取 where 子句
        String whereSql = SelectSql.getWhereSql(exact_search,search,in_search,rang_search);
        String sql = "select " + resultSql +",count(1) count "+ " from "+ table;
        if(!"".equals(whereSql)){
            sql =  sql + " where ";
        }
        // 逻辑删除
        if(is_logic){
            sql = sql +  is_delete_flag +"='"+is_delete_flag_value+"'";
        }
        sql = sql + whereSql + " group by "+resultSql;
        if(order != null && !order.isEmpty()){
            sql = sql + SelectSql.getOrderSql(order);
        }else{
            sql = sql + " order by count desc";
        }
        if(size>0){
            sql = "select *  from ("+sql +") where rownum <=" +size;
        }
        return sql;
    }
}
