package nk.gk.wyl.mysql.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 根据查询条件生成
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/29 0:09
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/29 0:09
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class QueryUtil {
    /**
     * 查询条件中抽取出来 模糊查询 精确查找 排序
     *
     * @param map          查询条件
     * @param search       模糊查询
     * @param exact_search 精确查找
     * @param in_search    in 查询
     * @param order        排序
     */
    public static void searchMap(Map<String, Object> map,
                                 Map<String, String> search,
                                 Map<String, Object> exact_search,
                                 Map<String, String> order,
                                 Map<String, List<String>> in_search) throws Exception {
        // 模糊查询
        search = getMap(map, "search");
        // 精确查找
        exact_search = getMapObject(map, "exact_search");
        // 排序
        order = getMap(map, "order");
        // in 字句
        in_search = getList(map, "in_search");
    }
    /**
     * 转map
     *
     * @param map   参数
     * @param field 参数中的key
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getMapObject(Map<String, Object> map, String field) throws Exception {
        Map<String, Object> query = null;
        if (map.containsKey(field)) {
            try {
                query = (Map<String, Object>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                try{
                    query = (Map<String, Object>) JSON.parse(map.get(field).toString());
                }catch (Exception e1){
                    throw new Exception("参数 " + field + " 类型错误");
                }
            }
        }
        return query==null?new HashMap<String,Object>():query;
    }

    /**
     * 转map
     *
     * @param map   参数
     * @param field 参数中的key
     * @return
     * @throws Exception
     */
    public static Map<String, String> getMap(Map<String, Object> map, String field) throws Exception {
        Map<String, String> query = null;
        if (map.containsKey(field)) {
            try {
                query = (Map<String, String>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                try{
                    query = (Map<String, String>) JSON.parse(map.get(field).toString());
                }catch (Exception e1){
                    throw new Exception("参数 " + field + " 类型错误");
                }
            }
        }
        return query==null?new HashMap<String,String>():query;
    }

    /**
     * 校验集合list
     * @param map 参数
     * @param field 字段
     * @return 返回集合
     * @throws Exception 异常信息
     */
    public static List<String> checkMapList(Map<String, Object> map,String field) throws Exception {
        if (StringUtils.isEmpty(map.get(field))) {
            throw new Exception(field + " 参数不能为空！");
        }
        return getMapList(map,field);
    }

    /**
     * 数组校验
     * @param map
     * @param field
     * @return
     * @throws Exception
     */
    public static String[] checkArray(Map<String, Object> map, String field) throws Exception {
        if(!map.containsKey(field)){
            throw new Exception("缺少 统计字段集合【keys】参数");
        }
        // 集合
        String[] array = getMapArray(map,field);
        if(array==null || array.length==0){
            throw new Exception("参数 统计字段集合【keys】不能为空");
        }
        return array;
    }

    /**
     * 获取参数中的数组
     *
     * @param map
     * @param field
     * @return
     */
    public static String[] getMapArray(Map<String, Object> map, String field) throws Exception {
        String[] array = null;
        if (!StringUtils.isEmpty(map.get(field))) {
            try {
                array = (String[]) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                try{
                    List<String> list = JSONArray.parseArray(JSON.toJSONString(map.get(field)),String.class);
                    array = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        array[i] = list.get(i);
                    }
                }catch (Exception e1){
                    try{
                        List<String> list = (List<String>) JSON.parse(map.get(field).toString());
                        array = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            array[i] = list.get(i);
                        }
                    }catch (Exception e2){
                        throw new Exception(field + " 参数类型错误！");
                    }

                }

            }
        }
        return array;
    }

    /**
     * 获取参数中的数组
     *
     * @param map
     * @param field
     * @return
     */
    public static List<String> getMapList(Map<String, Object> map, String field) throws Exception {
        List<String> array = null;
        if (!StringUtils.isEmpty(map.get(field))) {
            try {
                array = (List<String>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                throw new Exception(field + " 参数类型错误！");
            }
        }
        return array;
    }

    /**
     * 获取参数中的数组
     *
     * @param map
     * @param field
     * @return
     */
    public static List<Long> getMapLong(Map<String, Object> map, String field) throws Exception {
        List<Long> array = null;
        if (!StringUtils.isEmpty(map.get(field))) {
            try {
                array = (List<Long>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                throw new Exception(field + " 参数类型错误！");
            }
        }
        return array;
    }

    /**
     * 转map
     *
     * @param map   参数
     * @param field 参数中的key
     * @return
     * @throws Exception
     */
    public static Map<String, List<String>> getList(Map<String, Object> map, String field) throws Exception {
        Map<String, List<String>> query = null;
        if (map.containsKey(field)) {
            try {
                query = (Map<String, List<String>>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                throw new Exception("参数 " + field + " 类型错误");
            }
        }
        return query==null?new HashMap<String, List<String>>():query;
    }

    /**
     * 获取 rang_search
     * @param map   参数
     * @param field 参数中的key
     * @return
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> getRangSearch(Map<String, Object> map, String field) throws Exception {
        Map<String, Map<String, Object>> query = null;
        if (map.containsKey(field)) {
            try {
                query = (Map<String, Map<String, Object>>) JSON.toJSON(map.get(field));
            } catch (Exception e) {
                throw new Exception("参数 " + field + " 类型错误");
            }
        }
        return query==null?new HashMap<String, Map<String, Object>>():query;
    }

    /**
     * 校验map 参数
     *
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static Map<String, Object> checkMap(Map<String, Object> map, String name) throws Exception {
        if (StringUtils.isEmpty(map.get(name))) {
            throw new Exception("缺少 " + name + " 参数");
        }
        Map<String, Object> result = (Map<String, Object>) JSON.toJSON(map.get(name));
        return result;
    }

    /**
     * 校验map 参数   布尔
     *
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean checkBoolean(Map<String, Object> map, String name) throws Exception {
        boolean result = false;
        if (StringUtils.isEmpty(map.get(name))) {
            return result;
        }
        if (map.containsKey(name)) {
            try {
                result = Boolean.parseBoolean(map.get(name).toString());
            } catch (Exception e) {
                throw new Exception("参数 " + name + " 类型错误");
            }
        }
        return result;
    }

    /**
     * 校验map 参数
     *
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static String checkValue(Map<String, Object> map, String name) throws Exception {
        if (StringUtils.isEmpty(map.get(name))) {
            throw new Exception("缺少 " + name + " 参数");
        }
        return map.get(name).toString();
    }

    /**
     * 校验map 参数
     *
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static long checkLongValue(Map<String, Object> map, String name) throws Exception {
        if (StringUtils.isEmpty(map.get(name))) {
            throw new Exception("缺少 " + name + " 参数");
        }
        try{
            return Long.parseLong(map.get(name).toString());
        }catch (Exception e){
            throw new Exception("参数【"+name+"】类型是long");
        }
    }

    /**
     * 校验int
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static int checkIntValue(Map<String, Object> map, String name) throws Exception{
        if (StringUtils.isEmpty(map.get(name))) {
            throw new Exception("缺少 " + name + " 参数");
        }
        try{
            return Integer.parseInt(map.get(name).toString());
        }catch (Exception e){
            throw new Exception("参数【"+name+"】类型是 int ");
        }
    }

    /**
     * 校验int
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static int getIntValue(Map<String, Object> map, String name) throws Exception{
        if (StringUtils.isEmpty(map.get(name))) {
            return 0;
        }
        try{
            return Integer.parseInt(map.get(name).toString());
        }catch (Exception e){
            throw new Exception("参数【"+name+"】类型是 int ");
        }
    }
    /**
     * 获取参数值
     *
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public static String getValue(Map<String, Object> map, String name) {
        if (StringUtils.isEmpty(map.get(name))) {
            return "";
        }
        return map.get(name).toString();
    }

    /**
     * 获取用户的uid
     *
     * @param map
     * @return
     */
    public static String getUid(Map<String, Object> map) {
        return getValue(map, "uid");
    }
}
