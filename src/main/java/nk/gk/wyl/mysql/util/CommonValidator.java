package nk.gk.wyl.mysql.util;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Description: 参数校验
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/29 9:45
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/29 9:45
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class CommonValidator {
    /**
     * 校验是否是null 或者空
     *
     * @param map 参数
     * @throws Exception
     */
    public static void checkMapStr(Map<String, String> map, String type) throws Exception {
        if (map == null || map.isEmpty()) {
            throw new Exception("参数" + type + " 不能为null或空");
        }
    }

    /**
     * 校验是否是null 或者空
     *
     * @param map 参数
     * @throws Exception
     */
    public static void checkMap(Map<String, Object> map, String type) throws Exception {
        if (map == null || map.isEmpty()) {
            throw new Exception("参数" + type + " 不能为null或空");
        }
    }

    /**
     * 获取参数中的字符串值
     *
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static String getValue(Map params, String key) throws Exception {
        if (params == null) {
            throw new Exception("map 参数为空！");
        }
        if (StringUtils.isEmpty(params.get(key))) {
            throw new Exception("参数 " + key + " 为空！");
        }
        return params.get(key).toString();
    }

    /**
     * 获取参数中的字符串值
     *
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static Object getObjValue(Map params, String key) throws Exception {
        if (params == null) {
            throw new Exception("map 参数为空！");
        }
        if (StringUtils.isEmpty(params.get(key))) {
            throw new Exception("参数 " + key + " 为空！");
        }
        return params.get(key);
    }

    /**
     * 获取参数中的字符串值
     *
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static String getStrValue(Map params, String key) throws Exception {
        if (params == null) {
            return "";
        }
        if (StringUtils.isEmpty(params.get(key))) {
            return "";
        }
        return params.get(key).toString();
    }

    /**
     * 获取指定类型的值
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static Map<String,Integer> getMapInteger(Map params,String key) throws Exception{
        Map<String,Integer> map = null;
        try {
            map = (Map<String, Integer>) getObjValue(params,key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("参数 "+key+" 数据类型异常！");
        }
        return map;
    }

    /**
     * 校验字符串是否是数字.
     * @param str 字符串
     * @return
     */
    public static boolean checkNumber(String str){
        if(str != null && !"".equals(str.trim())){
            return str.matches("^[0-9]*$");
        }
        return false;
    }

}
