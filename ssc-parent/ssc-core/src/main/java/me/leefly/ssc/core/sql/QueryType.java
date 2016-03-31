package me.leefly.ssc.core.sql;

import me.leefly.ssc.core.struct.TableStruct;

/**
 * Created by Administrator on 2016/3/11.
 *
 * @author leefly
 * @version 1.0
 */
public enum QueryType {

    /**
     * 使用表名
     */
    USE_NAME,
    /**
     * 使用别名
     */
    USE_ALIAS;

    public String alias(TableStruct table) {
        if (QueryType.USE_ALIAS.equals(this))
            return table.getTableAlias();
        else if (QueryType.USE_NAME.equals(this))
            return table.getTableName();
        else
            return table.getTableName();
    }

}
