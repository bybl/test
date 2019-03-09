package net.huadong.bean;

/**
 * @Author: wj
 * @Date: 2019/1/3 16:51
 * @Version 1.0
 */
public class CarBean {

   private String  columnFamily;

   private String rowKey;

   private RowCols rowCols;


    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public RowCols getRowCols() {
        return rowCols;
    }

    public void setRowCols(RowCols rowCols) {
        this.rowCols = rowCols;
    }

    @Override
    public String toString() {
        return "CarBean{" +
                "columnFamily='" + columnFamily + '\'' +
                ", rowKey='" + rowKey + '\'' +
                ", rowCols=" + rowCols +
                '}';
    }
}
