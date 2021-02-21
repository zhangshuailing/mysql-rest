package nk.gk.wyl.mysql.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.util
 * @ClassName: Util
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/17 21:37
 * @Version: 1.0
 */
public class Util {
    /**
     * title 获取唯一流水号
     *
     * @return string
     */
    public static String getResourceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * title 获取当前时间
     *
     * @return 时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取字符串时间
     *
     * @return 返回字符串时间
     */
    public static String getStrDate() {
        return DateFormatUtils.format(getDate(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取字符串时间
     *
     * @return 返回字符串时间
     */
    public static String getStrDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

}
