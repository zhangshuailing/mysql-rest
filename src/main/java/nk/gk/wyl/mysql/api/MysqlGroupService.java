package nk.gk.wyl.mysql.api;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.api
 * @ClassName: MysqlGroupService
 * @Author: zsl
 * @Description: 分组接口
 * @Date: 2021/2/18 14:11
 * @Version: 1.0
 */
public interface MysqlGroupService {

    /**
     * 分组统计查询
     * @param sqlSessionTemplate 实例
     * @param table 表名
     * @param exact_search 精确查找
     * @param search 模糊匹配
     * @param in_search in 子查询
     * @param rang_search 区间查询 {field[字段名]:value[字段值 {start:"",end:"",format:"number 数字，time 时间 date 日期 year 年 month 月"}]}
     * @param order 排序
     * @param fields 统计字段集合
     * @param field 统计求和字段
     * @param size 数量 <=0 时 查询全部
     * @param is_logic true/false 是否是逻辑删除
     * @param is_delete_flag  逻辑删除时的字段
     * @param is_delete_flag_value  逻辑删除时的字段值
     * @param type  count/sum
     * @return 返回结果集合
     * @throws Exception
     */
    List<Map<String,Object>> group(SqlSessionTemplate sqlSessionTemplate,String table,
                                  Map<String, Object> exact_search,
                                   Map<String, String> search,
                                   Map<String, List<String>> in_search,
                                   Map<String, Map<String,Object>> rang_search,
                                   Map<String, String> order,
                                   String[] fields,
                                   String field,
                                   int size,
                                   boolean is_logic,
                                   String is_delete_flag,
                                   String is_delete_flag_value,
                                   String type) throws Exception;

    /**
     * 分组统计查询
     * @param sqlSessionTemplate 实例
     * @param obj 参数
     * @param table 表名
     * @param fields 统计字段集合
     * @param size 展示的条数（前N条数据）
     * @return 返回集合数据
     * @throws Exception
     */
    List<Map<String,Object>> group(SqlSessionTemplate sqlSessionTemplate,
                                   Map<String,Object> obj,
                                   String table,
                                   String[] fields,
                                   int size) throws Exception;


    /**
     * 分组统计查询
     * @param sqlSessionTemplate 实例
     * @param map 参数
     * @param table 表名
     * @param fields 统计字段集合
     * @return 返回集合数据
     * @throws Exception
     */
    Map<String, Object> groupPage(SqlSessionTemplate sqlSessionTemplate,
                                   Map<String,Object> map,
                                   String table,
                                   String[] fields) throws Exception;

    /**
     * 分组统计查询
     * @param sqlSessionTemplate 实例
     * @param obj 参数
     * @param table 表名
     * @param fields 统计字段集合
     * @param field  求和字段
     * @param size 展示的条数（前N条数据）
     * @return 返回集合数据
     * @throws Exception
     */
    List<Map<String,Object>> groupSum(SqlSessionTemplate sqlSessionTemplate,
                                      Map<String,Object> obj,
                                      String table,
                                      String[] fields,
                                      String field,
                                      int size) throws Exception;

    /**
     * 分组统计查询
     * @param sqlSessionTemplate 实例
     * @param map 参数
     * @param table 表名
     * @param fields 统计字段集合
     * @return 返回集合数据
     * @throws Exception
     */
    Map<String, Object> groupSumPage(SqlSessionTemplate sqlSessionTemplate,
                                  Map<String,Object> map,
                                  String table,
                                  String[] fields,
                                  String field) throws Exception;


}
