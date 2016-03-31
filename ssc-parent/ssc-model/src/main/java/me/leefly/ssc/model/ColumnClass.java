package me.leefly.ssc.model;

/**
 * Created by Administrator on 2016/1/29.
 *
 * @author leefly
 * @version 1.0
 */
public class ColumnClass implements java.io.Serializable {

    private static final long serialVersionUID = -7234604841375094803L;
    /**
     * 表名
     */
    protected String tableName;
    /**
     * 表别名，若组合表则别名应该相同
     */
    protected String tableAlias;
    /**
     * 是否主表字段
     */
    protected boolean primary;
    /**
     * 查询顺序
     */
    protected int index;
    /**
     * 字段名
     */
    protected String column;

    public ColumnClass(String tableName, String tableAlias,
                       boolean primary, int index, String column) {
        setTableName(tableName);
        setTableAlias(tableAlias);
        setPrimary(primary);
        setIndex(index);
        setColumn(column);
    }

    public ColumnClass() {
        // nothing
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
