package nk.gk.wyl.mysql.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import nk.gk.wyl.mysql.api.MysqlSelectService;
import nk.gk.wyl.mysql.mapper.MysqlMapper;
import nk.gk.wyl.mysql.util.QueryUtil;
import nk.gk.wyl.mysql.util.sql.SelectSql;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.impl
 * @ClassName: MysqlSelectServiceImpl
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/18 12:32
 * @Version: 1.0
 */
@Service
public class MysqlSelectServiceImpl implements MysqlSelectService {

    // mapper类地址
    private String sqlMapping;

    @Resource
    public void setMapper(MysqlMapper t) {
        sqlMapping = t.getClass().getInterfaces()[0].getName() + ".";
    }

    /**
     * 根据数据编号获取数据
     *
     * @param sqlSessionTemplate   mysql 实例
     * @param table                表名
     * @param id                   编号
     * @param is_logic             true/false 是否是逻辑删除
     * @param is_delete_flag       删除字段
     * @param is_delete_flag_value 删除字段的值
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findDataById(SqlSessionTemplate sqlSessionTemplate,
                                            String table,
                                            Object id,
                                            String[] fields,
                                            boolean is_logic,
                                            String is_delete_flag,
                                            String is_delete_flag_value) throws Exception {
        String sql = SelectSql.findDataByIdSql(table,
                fields,
                id,
                is_logic,
                is_delete_flag,
                is_delete_flag_value);
        List<Map<String,Object>> list = findList(sqlSessionTemplate,sql);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    /**
     * 列表查询（总和）
     *
     * @param obj                查询条件
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param size               数量 <=0 时 查询全部
     * @return List<Map < String , Object>> 返回集合
     * @throws Exception 异常信息
     */
    @Override
    public List<Map<String, Object>> findList(Map<String, Object> obj,
                                              SqlSessionTemplate sqlSessionTemplate,
                                              String table,
                                              String[] fields,
                                              int size) throws Exception {
        Map<String, Object> exact_search = QueryUtil.getMapObject(obj, "exact_search");
        Map<String, String> search = QueryUtil.getMap(obj, "search");
        Map<String, List<String>> in_search = QueryUtil.getList(obj, "in_search");
        Map<String, String> order = QueryUtil.getMap(obj, "order");
        Map<String, Map<String, Object>> rang_search = QueryUtil.getRangSearch(obj, "rang_search");
        return findList(sqlSessionTemplate,table,exact_search,search,in_search,rang_search,order,fields,size);
    }

    /**
     * 根据查询条件获取列表（精确匹配）
     *
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param query              查询条件
     * @param fields             显示字段集合
     * @return List<Map < String , Object>> 返回集合
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findListExact(SqlSessionTemplate sqlSessionTemplate,
                                                   String table,
                                                   Map<String, Object> query,
                                                   Map<String, String> order,
                                                   String[] fields,
                                                   int size) throws Exception {
        return findList(sqlSessionTemplate,table,query,null,null,null,order,fields,size);
    }

    /**
     * 根据查询条件获取列表（in子句）
     *
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param query              查询条件
     * @param fields             显示字段集合
     * @return List<Map < String , Object>> 返回集合
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findListIn(SqlSessionTemplate sqlSessionTemplate,
                                                String table,
                                                Map<String, List<String>> query,
                                                Map<String, String> order,
                                                String[] fields,
                                                int size) throws Exception {
        return findList(sqlSessionTemplate,table,null,null,query,null,order,fields,size);
    }

    /**
     * 根据查询条件获取列表（模糊匹配）
     *
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param query              查询条件
     * @param fields             显示字段集合
     * @return List<Map < String , Object>> 返回集合
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findListLike(SqlSessionTemplate sqlSessionTemplate,
                                                  String table,
                                                  Map<String, String> query,
                                                  Map<String, String> order,
                                                  String[] fields,
                                                  int size) throws Exception {
        return findList(sqlSessionTemplate,table,null,query,null,null,order,fields,size);
    }

    /**
     * 根据查询条件获取列表
     *
     * @param sqlSessionTemplate 实例
     * @param table              表名
     * @param exact_search       精确查找
     * @param search             模糊匹配
     * @param in_search          in 子查询
     * @param rang_search        区间查询 {field[字段名]:value[字段值 {start:"",end:"",format:"number 数字，time 时间 date 日期 year 年 month 月"}]}
     * @param order              排序
     * @param fields             显示字段集合
     * @return List<Map < String , Object>> 返回集合
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findList(SqlSessionTemplate sqlSessionTemplate,
                                              String table,
                                              Map<String, Object> exact_search,
                                              Map<String, String> search,
                                              Map<String, List<String>> in_search,
                                              Map<String, Map<String, Object>> rang_search,
                                              Map<String, String> order,
                                              String[] fields,
                                              int size) throws Exception {
        String sql = SelectSql.sql(table,fields,exact_search,search,in_search,order,rang_search,size);
        return findList(sqlSessionTemplate,sql);
    }

    /**
     * 根据 sql 语句获取列表
     *
     * @param sqlSessionTemplate
     * @param sql                sql语句
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findList(SqlSessionTemplate sqlSessionTemplate, String sql) throws Exception {
        List<Map<String,Object>> list = sqlSessionTemplate.selectList(sqlMapping+"select",sql);
        return list == null? new ArrayList<>():list;
    }

    /**
     * 分页数据
     *
     * @param sqlSessionTemplate
     * @param table              表名
     * @param pageNo             页码
     * @param pageSize           每页显示数据
     * @param exact_search       精确查询
     * @param search             模糊匹配
     * @param in_search          in子句查询
     * @param rang_search
     * @param order              排序
     * @param fields             显示字段数组
     * @return 返回数据
     * @throws Exception 异常信息
     */
    @Override
    public Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,
                                    String table,
                                    int pageNo,
                                    int pageSize,
                                    Map<String, Object> exact_search,
                                    Map<String, String> search,
                                    Map<String, List<String>> in_search,
                                    Map<String, Map<String, Object>> rang_search,
                                    Map<String, String> order,
                                    String[] fields) throws Exception {
        // 返回数据
        Map<String, Object> result = new HashMap<>();
        // 分页数据
        Page page = PageHelper.startPage(pageNo, pageSize, true);
        // 获取数据
        List<Map<String, Object>> list = findList(sqlSessionTemplate,table,exact_search,search,in_search,rang_search,order,fields,0);
        SelectSql.pageResult(result,page,list,pageNo,pageSize);
        return result;
    }

    /**
     * 分页数据
     *
     * @param sqlSessionTemplate
     * @param table              表名
     * @param map                组合参数
     * @param fields             显示字段数组
     * @return 返回数据
     * @throws Exception 异常信息
     */
    @Override
    public Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,
                                    String table,
                                    Map<String, Object> map,
                                    String[] fields) throws Exception {
        int pageSize = 0;
        try {
            pageSize = Integer.parseInt(map.get("pageSize") == null || "".equals(map.get("pageSize").toString()) ? "10" : map.get("pageSize").toString());
        } catch (Exception e) {
            throw new Exception("pageSize 参数类型错误");
        }
        int pageNo = 10;
        try {
            pageNo = Integer.parseInt(map.get("pageNo") == null || "".equals(map.get("pageNo").toString()) ? "1" : map.get("pageNo").toString());
        } catch (Exception e) {
            throw new Exception("pageSize 参数类型错误");
        }
        Map<String, Object> exact_search = QueryUtil.getMapObject(map, "exact_search");
        Map<String, String> search = QueryUtil.getMap(map, "search");
        Map<String, List<String>> in_search = QueryUtil.getList(map, "in_search");
        Map<String, String> order = QueryUtil.getMap(map, "order");
        Map<String, Map<String, Object>> rang_search = QueryUtil.getRangSearch(map, "rang_search");
        return page(sqlSessionTemplate,
                table,
                pageNo,
                pageSize,
                exact_search,
                search,
                in_search,
                rang_search,
                order,
                fields);
    }

    /**
     * 分页数据（通过sql）
     *
     * @param sqlSessionTemplate
     * @param map
     * @param sql
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,
                                    Map<String, Object> map,
                                    String sql) throws Exception {
        return null;
    }

    /**
     * 分页数据（通过sql）
     *
     * @param sqlSessionTemplate
     * @param map
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate,
                                    Map<String, Object> map,
                                    String sql,
                                    int pageNo,
                                    int pageSize) throws Exception {
        return null;
    }
    @Value("${database-name}")
    private String databaseName;
    /**
     * 通过表名获取其字段注释
     *
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param table_schema      数据库名
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getCommentsByTableName(SqlSessionTemplate sqlSessionTemplate, String table,String table_schema) throws Exception {
        if(StringUtils.isEmpty(table_schema)){
            table_schema = databaseName;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",table);
        map.put("tableSchema",table_schema);
        Map<String,Object> result = new HashMap<>();
        result.put("table_comment",new HashMap<>());
        List<Map<String,Object>> list_cols = sqlSessionTemplate.selectList(sqlMapping+"getColCommentsByTableName",map);
        List<Map<String,Object>> list_table = sqlSessionTemplate.selectList(sqlMapping+"getTableCommentByTableName",map);
        if(list_table!=null && list_table.size()>0){
            result.put("table_comment",list_table.get(0));
        }
        result.put("col_comment",list_cols);
        return result;
    }

    /**
     * 根据字段 和字段集合获取数据
     *
     * @param sqlSessionTemplate mysql 实例
     * @param table              表名
     * @param field              字段名
     * @param values             字段集合  List<Object> Object 是int 或者 String
     * @return 返回集合
     * @throws Exception 异常信息
     */
    @Override
    public List<Map<String, Object>> findListByIns(SqlSessionTemplate sqlSessionTemplate,
                                                   String table,
                                                   String field,
                                                   List<String> values,
                                                   Map<String, String> order,
                                                   String[] fields) throws Exception {
        Map<String,List<String>> in_search = new HashMap<>();
        in_search.put(field,values);
        return findListIn(sqlSessionTemplate,table,in_search,order,fields,0);
    }

    /**
     * 根据单个字段 和 单个字段值获取数据
     *
     * @param sqlSessionTemplate
     * @param table
     * @param field
     * @param value
     * @param order
     * @param fields
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findListByOneFieldAndValue(SqlSessionTemplate sqlSessionTemplate,
                                                                String table,
                                                                String field,
                                                                Object value,
                                                                Map<String, String> order,
                                                                String[] fields) throws Exception {
        return null;
    }

}
