package me.leefly.ssc.core.restrictions;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/13.
 *
 * @author leefly
 * @version 1.0
 */
public class SimpleRestriction extends AbstractRestrictionValue<Serializable> {

    private static final long serialVersionUID = 8591578319332219837L;

    /**
     * 需要设置构造器
     *
     * @param restriction 条件
     * @param alias       别名
     */
    public SimpleRestriction(String restriction, String alias, Serializable value) {
        super(restriction, alias, value);
    }

    @Override
    public String getRestrictionSQL(String tableAlias, String column) {
        return tableAlias + "." + column + " " + getRestriction() + " :" + getAlias();
    }

}
