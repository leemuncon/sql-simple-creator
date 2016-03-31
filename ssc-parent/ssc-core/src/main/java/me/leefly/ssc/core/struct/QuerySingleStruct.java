package me.leefly.ssc.core.struct;

import me.leefly.ssc.core.restrictions.Restriction;
import me.leefly.ssc.core.sql.QueryType;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Administrator on 2015/11/12.
 * 订单查询表的查询条件
 *
 * @author leefly
 * @version 1.0
 */
public class QuerySingleStruct extends BaseColumn implements QueryFace {

    private static final long serialVersionUID = -3139164069402620360L;
    /**
     * 查询条件
     */
    private Restriction restriction;
    /**
     * 查询的值
     */
    private Object value;

    public QuerySingleStruct(TableStruct table,
                             String column,
                             Restriction restriction,
                             Object value) {
        super(table, column);
        setRestriction(restriction);
        setValue(value);
    }

    public QuerySingleStruct() {
        super();
    }

    /**
     * 返回查询条件，若无条件返回空字符串
     *
     * @return 查询条件
     */
    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public boolean isMore() {
        return false;
    }

    @Override
    public TableStruct[] tables() {
        return new TableStruct[]{getTable()};
    }

    @Override
    public String getQuerySQL(Alias alias, QueryType type) {
        if (!check())
            return null;
        return getRestriction().getRestrictionSQL(alias.alias(type.alias(getTable())), getColumn());
    }

    private boolean check() {
        if (getRestriction() == null || getTable() == null
                || getTable().getTableName() == null || getColumn() == null)
            return false;
        return true;
    }

}
