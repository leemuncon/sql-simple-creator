package me.leefly.ssc.core.restrictions;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author leefly
 * @version 1.0
 */
public abstract class AbstractRestrictionValue<T> extends AbstractRestriction implements RestrictionValue<T> {

    private static final long serialVersionUID = -4250251478788413585L;
    /**
     * 别名
     */
    private String alias;
    /**
     * 要设置的值
     */
    private T value;

    public AbstractRestrictionValue(String restriction, String alias, T value) {
        super(restriction);
        setAlias(alias + "_" + System.nanoTime());
        setValue(value);
    }

    public AbstractRestrictionValue() {
        super();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
