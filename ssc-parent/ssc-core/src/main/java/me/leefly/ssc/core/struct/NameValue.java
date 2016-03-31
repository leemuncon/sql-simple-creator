package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2015/11/17.
 * 用于插入表示设置值
 *
 * @author leefly
 * @version 1.0
 */
public class NameValue extends BaseColumn {

    private static final long serialVersionUID = -8426372595654062020L;

    private Object value;// 跟新的值

    public NameValue(TableStruct table, String column, Object value) {
        super(table, column);
        setValue(value);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
