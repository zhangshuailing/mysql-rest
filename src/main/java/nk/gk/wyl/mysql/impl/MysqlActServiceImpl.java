package nk.gk.wyl.mysql.impl;

import nk.gk.wyl.mysql.api.MysqlActService;
import nk.gk.wyl.mysql.entity.data.IdData;
import nk.gk.wyl.mysql.mapper.MysqlMapper;
import nk.gk.wyl.mysql.util.Util;
import nk.gk.wyl.mysql.util.sql.InsertSql;
import nk.gk.wyl.mysql.util.sql.SelectSql;
import nk.gk.wyl.mysql.util.sql.UpdateSql;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.impl
 * @ClassName: MysqlServiceImpl
 * @Author: zsl
 * @Description: 操作接口实现类
 * @Date: 2021/2/17 21:06
 * @Version: 1.0
 */
@Service
public class MysqlActServiceImpl implements MysqlActService {

    // mapper类地址
    private String sqlMapping;

    @Resource
    public void setMapper(MysqlMapper t) {
        sqlMapping = t.getClass().getInterfaces()[0].getName() + ".";
    }


    /**
     * 单条插入
     *
     * @param obj                对象
     * @param sqlSessionTemplate mysql 实例
     * @param type               1 表示主键是int类型 0 表示主键是String类型
     * @param table              表名
     * @param uid                用户名
     * @return 返回 object 默认是 主键字段值
     * @throws Exception 异常信息
     */
    @Override
    public Object save(Map<String, Object> obj,
                       SqlSessionTemplate sqlSessionTemplate,
                       int type,
                       String table,
                       String uid) throws Exception {
        String sql = "";
        Object id = "";
        if(type == 0 ){
            id = Util.getResourceId();
            obj.put("id",id);
        }
        sql = InsertSql.insertSql(table,uid,obj);
        try{
            if(type == 0 ){
                int result = sqlSessionTemplate.insert(sqlMapping+"insert",sql);
            }else{ // 自增主键
                IdData idData = new IdData();
                idData.setSql(sql);
                int result = sqlSessionTemplate.insert(sqlMapping+"insertDefaultId",idData);
                id = idData.getId();
            }
        }catch (Exception e){
            throw new Exception(e.getMessage()+"\n sql="+sql);
        }
        return id;
    }

    /**
     * 单条更新（根据主键编号）
     *
     * @param obj                对象
     * @param sqlSessionTemplate mysql 实例
     * @param id                 主键id
     * @param table              表名
     * @param uid                用户名
     * @return 返回 true/false
     * @throws Exception 异常信息
     */
    @Override
    public boolean update(Map<String, Object> obj,
                          SqlSessionTemplate sqlSessionTemplate,
                          Object id,
                          String table,
                          String uid) throws Exception {
        String sql = UpdateSql.sql(table,id,obj,uid);
        int result = sqlSessionTemplate.insert(sqlMapping+"update",sql);
        return true;
    }

}
