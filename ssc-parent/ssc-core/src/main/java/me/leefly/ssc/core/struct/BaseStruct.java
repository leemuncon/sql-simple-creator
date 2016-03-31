package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2016/3/2.
 *
 * @author leefly
 * @version 1.0
 */
public class BaseStruct implements java.io.Serializable {

    private static final long serialVersionUID = -5165226915960872556L;

    private TableStruct table;

    public BaseStruct(TableStruct table) {
        setTable(table);
    }

    public BaseStruct() {
        // nothing...
    }

    public TableStruct getTable() {
        return table;
    }

    public void setTable(TableStruct table) {
        this.table = table;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (obj == this)
            return true;
        else if (obj instanceof BaseStruct)
            return this.hashCode() == obj.hashCode();
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (table == null ? 0 : table.hashCode());
        return result;
    }
}
