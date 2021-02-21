package nk.gk.wyl.mysql;

import nk.gk.wyl.mysql.api.MysqlSelectService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MysqlRestApplicationTests {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private MysqlSelectService mysqlSelectService;

    @Test
    void contextLoads() {
        try {
            List<String> values = new ArrayList<>();
            /*values.add("1");
            values.add("11");*/
            List<Map<String,Object>> list = mysqlSelectService.findListByIns(sqlSessionTemplate,
                    "demo",
                    "id",
                    values,
                    null,
                    null);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
