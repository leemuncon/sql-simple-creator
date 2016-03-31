package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2015/11/16.
 * 订单表
 *
 * @author leefly
 * @version 1.0
 */
public class TableStruct implements java.io.Serializable {

    private static final long serialVersionUID = 7835121376823145709L;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表别名，若组合表则别名应该相同，若==null则使用{@link #tableName}
     */
    private String tableAlias;
    /**
     * 是否主表
     */
    private boolean primary;
    /**
     * 查询组别,该值一致为一组查询
     */
    private int index;

    public TableStruct(String tableName, String tableAlias, boolean primary, int index) {
        setTableName(tableName);
        setTableAlias(tableAlias);
        setPrimary(primary);
        setIndex(index);
    }

    public TableStruct() {
        // nothing...
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        if (tableName == null)
            throw new NullPointerException("tableName == null");
        this.tableName = tableName;
    }

    public String getTableAlias() {
        if (tableAlias == null)
            return tableName;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (obj == this)
            return true;
        else if (obj instanceof TableStruct)
            return this.hashCode() == obj.hashCode();
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (tableName == null ? 0 : tableName.hashCode());
        result = 37 * result + (tableAlias == null ? 0 : tableAlias.hashCode());
        result = 37 * result + (primary ? 1 : 0);
        result = 37 * result + index;
        return result;
    }
}
