package nk.gk.wyl.mysql.api;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.api
 * @ClassName: MysqlSelectService
 * @Author: zsl
 * @Description: 查询接口
 * @Date: 2021/2/18 12:32
 * @Version: 1.0
 */
public interface MysqlSelectService {

    /**
     * 根据数据编号获取数据
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param id 编号
     * @param fields 显示字段集合
     * @param is_logic true/false 是否是逻辑删除
     * @param is_delete_flag 删除字段
     * @param is_delete_flag_value 删除字段的值
     * @return
     * @throws Exception
     */
    Map<String,Object> findDataById(SqlSessionTemplate sqlSessionTemplate,
                                    String table,
                                    Object id,
                                    String[] fields,
                                    boolean is_logic,
                                    String is_delete_flag,
                                    String is_delete_flag_value) throws Exception;

    /**
     * 列表查询（总和）
     * @param obj 查询条件
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param fields 显示字段集合
     * @param size 数量 <=0 时 查询全部
     * @return List<Map<String,Object>> 返回集合
     * @throws Exception 异常信息
     */
    List<Map<String,Object>> findList(Map<String,Object> obj,
                                      SqlSessionTemplate sqlSessionTemplate,
                                      String table,
                                      String[] fields,
                                      int size) throws Exception;

    /**
     * 根据查询条件获取列表（精确匹配）
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param query 查询条件
     * @param order 排序
     * @param fields 显示字段集合
     * @return List<Map<String,Object>> 返回集合
     * @throws Exception
     */
    List<Map<String, Object>> findListExact(SqlSessionTemplate sqlSessionTemplate,
                                            String table,
                                            Map<String, Object> query,
                                            Map<String, String> order,
                                            String[] fields,
                                            int size) throws Exception;

    /**
     * 根据查询条件获取列表（in子句）
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param query 查询条件
     * @param order 排序
     * @param fields 显示字段集合
     * @param size 数量 <=0 时 查询全部
     * @return List<Map<String,Object>> 返回集合
     * @throws Exception
     */
    List<Map<String, Object>> findListIn(SqlSessionTemplate sqlSessionTemplate,
                                         String table,
                                         Map<String, List<String>> query,
                                         Map<String, String> order,
                                         String[] fields,
                                         int size) throws Exception;

    /**
     * 根据查询条件获取列表（模糊匹配）
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param query 查询条件
     * @param order 排序
     * @param fields 显示字段集合
     * @param size 数量 <=0 时 查询全部
     * @return List<Map<String,Object>> 返回集合
     * @throws Exception
     */
    List<Map<String, Object>> findListLike(SqlSessionTemplate sqlSessionTemplate,
                                           String table,
                                           Map<String, String> query,
                                           Map<String, String> order,
                                           String[] fields,
                                           int size) throws Exception;

    /**
     * 根据查询条件获取列表
     * @param sqlSessionTemplate 实例
     * @param table 表名
     * @param exact_search 精确查找
     * @param search 模糊匹配
     * @param in_search in 子查询
     * @param rang_search 区间查询 {field[字段名]:value[字段值 {start:"",end:"",format:"number 数字，time 时间 date 日期 year 年 month 月"}]}
     * @param order 排序
     * @param fields 显示字段集合
     * @param size 数量 <=0 时 查询全部
     * @return List<Map<String,Object>> 返回集合
     * @throws Exception
     */
    List<Map<String, Object>> findList(SqlSessionTemplate sqlSessionTemplate,String table,
                                       Map<String, Object> exact_search,
                                       Map<String, String> search,
                                       Map<String, List<String>> in_search,
                                       Map<String, Map<String,Object>> rang_search,
                                       Map<String, String> order,
                                       String[] fields,
                                       int size) throws Exception;

    /**
     * 根据 sql 语句获取列表
     * @param sqlSessionTemplate
     * @param sql sql语句
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findList(SqlSessionTemplate sqlSessionTemplate,String sql) throws Exception;

    /**
     * 分页数据
     *
     * @param table        表名
     * @param pageNo       页码
     * @param pageSize     每页显示数据
     * @param exact_search 精确查询
     * @param search       模糊匹配
     * @param in_search    in子句查询
     * @param order        排序
     * @param fields       显示字段数组
     * @return 返回数据
     * @throws Exception 异常信息
     */
    Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,String table,
                             int pageNo,
                             int pageSize,
                             Map<String, Object> exact_search,
                             Map<String, String> search,
                             Map<String, List<String>> in_search,
                             Map<String, Map<String,Object>> rang_search,
                             Map<String, String> order,
                             String[] fields) throws Exception;

    /**
     * 分页数据
     *
     * @param table  表名
     * @param map    组合参数
     * @param fields 显示字段数组
     * @return 返回数据
     * @throws Exception 异常信息
     */
    Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,
                             String table,
                             Map<String, Object> map,
                             String[] fields) throws Exception;


    /**
     * 分页数据（通过sql）
     * @param sqlSessionTemplate
     * @param map
     * @param sql
     * @return
     * @throws Exception
     */
    Map<String,Object> page(SqlSessionTemplate sqlSessionTemplate,
                            Map<String,Object> map,
                            String sql) throws Exception;

    /**
     * 分页数据（通过sql）
     * @param sqlSessionTemplate
     * @param map
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    Map<String,Object> page(SqlSessionTemplate sqlSessionTemplate,
                            Map<String,Object> map,
                            String sql,
                            int pageNo,
                            int pageSize) throws Exception;

    /**
     * 通过表名获取其字段注释
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param table_schema 数据库
     * @return
     * @throws Exception
     */
    Map<String,Object> getCommentsByTableName(SqlSessionTemplate sqlSessionTemplate,
                                     String table,String table_schema) throws Exception;

    /**
     * 根据单个字段 和字段集合获取数据
     * @param sqlSessionTemplate mysql 实例
     * @param table 表名
     * @param field 字段名
     * @param values 字段集合  List<String>
     * @param order 排序 value: asc/desc
     * @param fields 显示字段集合
     * @return 返回集合
     * @throws Exception 异常信息
     */
    List<Map<String,Object>> findListByIns(SqlSessionTemplate sqlSessionTemplate,
                                           String table,
                                           String field,
                                           List<String> values,
                                           Map<String, String> order,
                                           String[] fields) throws Exception;

    /**
     * 根据单个字段 和 单个字段值获取数据
     * @param sqlSessionTemplate
     * @param table
     * @param field
     * @param value
     * @param order
     * @param fields
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> findListByOneFieldAndValue(SqlSessionTemplate sqlSessionTemplate,
                                                        String table,
                                                        String field,
                                                        Object value,
                                                        Map<String, String> order,
                                                        String[] fields) throws Exception;
}
