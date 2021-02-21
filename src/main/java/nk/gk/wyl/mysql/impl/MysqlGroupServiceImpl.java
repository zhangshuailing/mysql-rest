package nk.gk.wyl.mysql.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import nk.gk.wyl.mysql.api.MysqlGroupService;
import nk.gk.wyl.mysql.api.MysqlSelectService;
import nk.gk.wyl.mysql.util.QueryUtil;
import nk.gk.wyl.mysql.util.sql.GroupCountSql;
import nk.gk.wyl.mysql.util.sql.GroupSumSql;
import nk.gk.wyl.mysql.util.sql.SelectSql;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.impl
 * @ClassName: MysqlGroupServiceImpl
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/18 14:12
 * @Version: 1.0
 */
@Service
public class MysqlGroupServiceImpl implements MysqlGroupService {

    @Autowired
    private MysqlSelectService mysqlSelectService;


    /**
     * 分组统计查询
     *
     * @param sqlSessionTemplate   实例
     * @param table                表名
     * @param exact_search         精确查找
     * @param search               模糊匹配
     * @param in_search            in 子查询
     * @param rang_search          区间查询 {field[字段名]:value[字段值 {start:"",end:"",format:"number 数字，time 时间 date 日期 year 年 month 月"}]}
     * @param order                排序
     * @param fields               统计字段集合
     * @param field                统计求和字段
     * @param size                 数量 <=0 时 查询全部
     * @param is_logic             true/false 是否是逻辑删除
     * @param is_delete_flag       逻辑删除时的字段
     * @param is_delete_flag_value 逻辑删除时的字段值
     * @param type  count/sum
     * @return 返回结果集合
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> group(SqlSessionTemplate sqlSessionTemplate,
                                           String table,
                                          Map<String, Object> exact_search,
                                           Map<String, String> search,
                                           Map<String, List<String>> in_search,
                                           Map<String, Map<String, Object>> rang_search,
                                           Map<String, String> order,
                                           String[] fields,
                                           String field,
                                           int size,
                                           boolean is_logic,
                                           String is_delete_flag,
                                           String is_delete_flag_value,
                                           String type) throws Exception {
        String sql = "";
        if("count".equals(type)){
            sql = GroupCountSql.sql(table,
                    fields,
                    exact_search,
                    search,
                    in_search,
                    order,
                    rang_search,
                    size,
                    is_logic,
                    is_delete_flag,
                    is_delete_flag_value);
        }else{
            sql = GroupSumSql.sql(table,
                    fields,
                    exact_search,
                    search,
                    in_search,
                    order,
                    rang_search,
                    field,
                    size,
                    is_logic,
                    is_delete_flag,
                    is_delete_flag_value);
        }
        return mysqlSelectService.findList(sqlSessionTemplate,sql);
    }

    /**
     * 分组统计查询
     *
     * @param sqlSessionTemplate 实例
     * @param obj                参数
     * @param table              表名
     * @param fields             统计字段集合
     * @param size               展示的条数（前N条数据）
     * @return 返回集合数据
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> group(SqlSessionTemplate sqlSessionTemplate,
                                           Map<String, Object> obj,
                                           String table,
                                           String[] fields,
                                           int size) throws Exception {
        return (List<Map<String, Object>>) commonGroup(sqlSessionTemplate,obj,table,fields,"",size,"count",false);
    }

    /**
     * 通用接口（分组统计count  分组求和统计 sum）
     * @param sqlSessionTemplate
     * @param obj
     * @param table
     * @param fields
     * @param field
     * @param size
     * @param type
     * @param isPage
     * @return
     * @throws Exception
     */
    public Object commonGroup(SqlSessionTemplate sqlSessionTemplate,
                                                 Map<String, Object> obj,
                                                 String table,
                                                 String[] fields,
                                                 String field,
                                                 int size,
                                                 String type,
                                                 boolean isPage) throws Exception{
        int pageSize = 0;
        int pageNo = 10;
        // 分页数据
        Page page = null;
        if(isPage){
            try {
                pageSize = Integer.parseInt(obj.get("pageSize") == null || "".equals(obj.get("pageSize").toString()) ? "10" : obj.get("pageSize").toString());
            } catch (Exception e) {
                throw new Exception("pageSize 参数类型错误");
            }

            try {
                pageNo = Integer.parseInt(obj.get("pageNo") == null || "".equals(obj.get("pageNo").toString()) ? "1" : obj.get("pageNo").toString());
            } catch (Exception e) {
                throw new Exception("pageSize 参数类型错误");
            }
            page = PageHelper.startPage(pageNo, pageSize, true);
        }
       Map<String, Object> exact_search = QueryUtil.getMapObject(obj, "exact_search");
        Map<String, String> search = QueryUtil.getMap(obj, "search");
        Map<String, List<String>> in_search = QueryUtil.getList(obj, "in_search");
        Map<String, String> order = QueryUtil.getMap(obj, "order");
        Map<String, Map<String, Object>> rang_search = QueryUtil.getRangSearch(obj, "rang_search");
        boolean is_logic = QueryUtil.checkBoolean(obj,"is_logic");
        String is_delete_flag = "";
        String is_delete_flag_value = "";
        if(is_logic){
            is_delete_flag = QueryUtil.getValue(obj,"is_delete_flag");
            is_delete_flag_value = QueryUtil.getValue(obj,"is_delete_flag_value");
        }
        List<Map<String, Object>> list = group(sqlSessionTemplate,
                table,
                exact_search,
                search,
                in_search,
                rang_search,
                order,
                fields,
                field,
                size,
                is_logic,
                is_delete_flag,
                is_delete_flag_value,
                type);
        if(isPage){
            // 返回数据
            Map<String, Object> result = new HashMap<>();
            SelectSql.pageResult(result,page,list,pageNo,pageSize);
            return result;
        }else {
            return list;
        }
    }

    /**
     * 分组统计查询
     *
     * @param sqlSessionTemplate 实例
     * @param map                参数
     * @param table              表名
     * @param fields             统计字段集合
     * @return 返回集合数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> groupPage(SqlSessionTemplate sqlSessionTemplate,
                                               Map<String, Object> map,
                                               String table,
                                               String[] fields) throws Exception {
        return (Map<String, Object>) commonGroup(sqlSessionTemplate,map,table,fields,"",0,"count",true);
    }

    /**
     * 分组统计查询
     *
     * @param sqlSessionTemplate 实例
     * @param obj                参数
     * @param table              表名
     * @param fields             统计字段集合
     * @param field              求和字段
     * @param size               展示的条数（前N条数据）
     * @return 返回集合数据
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> groupSum(SqlSessionTemplate sqlSessionTemplate,
                                              Map<String, Object> obj,
                                              String table,
                                              String[] fields,
                                              String field,
                                              int size) throws Exception {
        return  (List<Map<String, Object>>) commonGroup(sqlSessionTemplate,obj,table,fields,field,size,"sum",false);
    }

    /**
     * 分组统计查询
     *
     * @param sqlSessionTemplate 实例
     * @param map                参数
     * @param table              表名
     * @param fields             统计字段集合
     * @param field
     * @return 返回集合数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> groupSumPage(SqlSessionTemplate sqlSessionTemplate,
                                            Map<String, Object> map,
                                            String table,
                                            String[] fields,
                                            String field) throws Exception {
        return (Map<String, Object>) commonGroup(sqlSessionTemplate,map,table,fields,field,0,"sum",true);
    }
}
