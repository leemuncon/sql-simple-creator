package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2016/1/29.
 * 基础字段
 *
 * @author leefly
 * @version 1.0
 */
public class BaseColumn extends BaseStruct {

    private static final long serialVersionUID = -7007203088872156183L;

    protected String column;

    public BaseColumn(TableStruct table, String column) {
        super(table);
        setColumn(column);
    }

    public BaseColumn() {
        // nothing
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
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
        result = 37 * result + super.hashCode();
        result = 37 * result + (column == null ? 0 : column.hashCode());
        return result;
    }
}
