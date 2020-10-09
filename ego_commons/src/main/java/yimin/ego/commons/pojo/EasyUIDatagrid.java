package yimin.ego.commons.pojo;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:37
 *   @Description :
 *
 */
public class EasyUIDatagrid {

    private List<?> rows;
    private long total;

    public EasyUIDatagrid(){

    }

    public EasyUIDatagrid(List<?> rows, long total){
        this.rows = rows;
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
