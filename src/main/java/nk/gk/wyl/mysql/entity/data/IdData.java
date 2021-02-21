package nk.gk.wyl.mysql.entity.data;

/**
 * @ProjectName: mysql-rest
 * @Package: nk.gk.wyl.mysql.entity.data
 * @ClassName: IdData
 * @Author: zsl
 * @Description: ${description}
 * @Date: 2021/2/21 17:04
 * @Version: 1.0
 */
public class IdData {
    private int id;

    private String sql;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
