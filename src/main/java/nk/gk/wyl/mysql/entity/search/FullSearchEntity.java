package nk.gk.wyl.mysql.entity.search;

import java.util.Map;

/**
 * 复合检索实体类
 */
public class FullSearchEntity extends SearchEntity{

    private Map<String, Integer> order;

    private int pageNo;

    private int pageSize;

    public Map<String, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<String, Integer> order) {
        this.order = order;
    }

    public int getPageNo() {
        return pageNo==0?1:pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize==0?10:pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
