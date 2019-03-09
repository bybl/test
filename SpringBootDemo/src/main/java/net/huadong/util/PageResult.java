package net.huadong.util;

import java.util.List;

/**
 * Created by wuph on 2017-2-6.
 * Description
 */
public class PageResult {
    private long total;
    private List rows;
    private List footer;

    public List getFooter() {
        return footer;
    }

    public void setFooter(List footer) {
        this.footer = footer;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
