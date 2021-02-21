package nk.gk.wyl.mysql.util.sql;

import nk.gk.wyl.mysql.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Description: 删除的sql 语句
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/29 10:23
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/29 10:23
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class DelSql {

    public static String is_deleted= "is_deleted";

    public static String getIs_deleted() {
        return is_deleted;
    }

    // logger 日志
    private static Logger logger = LoggerFactory.getLogger(DelSql.class);

    /**
     * 根据数据编号删除数据
     *
     * @param table 编号
     * @param id    数据编号
     * @return 返回sql语句
     * @throws Exception 抛出异常
     */
    public static String sql(String table, String id) {
        String sql = "delete from " + table + " " + SelectSql.getCommonWhereSql() + " and id ='" + id + "'";
        logger.info(sql);
        return sql;
    }

    /**
     * 清除表
     * @param table
     * @return
     */
    public static String deleteFromTable(String table){
        String sql = "delete from " + table ;
        logger.info(sql);
        return sql;
    }

    /**
     * 根据数据查询条件删除数据
     *
     * @param table 表名
     * @param query 查询条件
     * @return 返回sql语句
     */
    public static String sql(String table, Map<String, Object> query) {
        String sql = "delete from " + table + " " + SelectSql.getQuerySql("", query);
        logger.info(sql);
        return sql;
    }

    private final String delete_prefix = "";

    /**
     * 根据数据编号逻辑删除数据
     *
     * @param table 表名
     * @param id    数据编号
     * @return 返回sql语句
     */
    public static String sqlUpdateDel(String table, String id, String uid) {
        String sql = "update " + table + " set "+is_deleted+"='1' , delete_time='" + Util.getStrDate() + "' , delete_by='" + uid + "'  where id = '" + id + "'";
        logger.info(sql);
        return sql;
    }

    /**
     * 根据数据查询条件逻辑删除数据
     *
     * @param table 表名
     * @param query 查询条件
     * @return 返回sql语句
     */
    public static String sqlUpdateDel(String table, Map<String, Object> query, String uid) {
        String sql = "update " + table + " set "+is_deleted+"='1' , delete_time='" + Util.getStrDate() + "' , delete_by='" + uid + "'" + SelectSql.getQuerySql(null, query);
        logger.info(sql);
        return sql;
    }

    /**
     * 根据数据查询条件逻辑删除数据
     *
     * @param table  表名
     * @param values 集合数据
     * @return 返回sql语句
     */
    public static String sqlUpdateDelIn(String table, List<String> values, String field, String uid) {
        String sql = "update " + table + " set "+is_deleted+"='1' , delete_time='" + Util.getStrDate() + "' , delete_by='" + uid + "'" + SelectSql.getWhereSqlIn(field, values);
        logger.info(sql);
        return sql;
    }
}
