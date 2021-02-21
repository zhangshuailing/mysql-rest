package nk.gk.wyl.mysql.util;


import java.util.Date;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.util
 * @ClassName: DataType
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/19 14:49
 * @Version: 1.0
 */
public class DataType {
    /**
     * 判断对象类型
     * @param value 值
     * @return
     */
    public static String getDataType(Object value){
        String type = "";
        if (value instanceof Integer) {
            type = "Integer";
        } else if (value instanceof Long) {
            type = "Long";
        } else if (value instanceof Short) {
            type = "Short";
        } else if (value instanceof Boolean) {
            type = "Boolean";
        } else if (value instanceof Byte) {
            type = "byte";
        } else if (value instanceof Character) {
            type = "Character";
        } else if (value instanceof Double) {
            type = "Double";
        } else if (value instanceof Float) {
            type = "Float";
        } else if (value instanceof String) {
            type = "String";
        } else if (value instanceof Date) {
            type = "Date";
        }else {
            //System.out.println("unknown type, or yourself type");
        }
        return type;
    }
}
