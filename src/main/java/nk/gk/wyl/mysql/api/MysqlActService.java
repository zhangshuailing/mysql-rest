package nk.gk.wyl.mysql.api;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.impl
 * @ClassName: MysqlServiceImpl
 * @Author: zsl
 * @Description: 操作接口类
 * @Date: 2021/2/17 21:06
 * @Version: 1.0
 */
public interface MysqlActService {

    /**
     * 单条插入
     * @param obj 对象
     * @param sqlSessionTemplate mysql 实例
     * @param type 1 表示主键是int类型 0 表示主键是String类型
     * @param table 表名
     * @param uid 用户名
     * @return 返回 object 默认是 主键字段值
     * @throws Exception 异常信息
     */
    Object save(Map<String,Object> obj,SqlSessionTemplate sqlSessionTemplate,int type,String table,String uid) throws Exception;

    /**
     * 单条更新（根据主键编号）
     * @param obj 对象
     * @param sqlSessionTemplate mysql 实例
     * @param id 主键id
     * @param table 表名
     * @param uid 用户名
     * @return 返回 true/false
     * @throws Exception 异常信息
     */
    boolean update(Map<String,Object> obj,SqlSessionTemplate sqlSessionTemplate,Object id,String table,String uid) throws Exception;

}
