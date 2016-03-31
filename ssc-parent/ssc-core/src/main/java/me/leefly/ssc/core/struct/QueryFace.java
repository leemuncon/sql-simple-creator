package me.leefly.ssc.core.struct;

import me.leefly.ssc.core.sql.QueryType;

/**
 * Created by Administrator on 2015/11/12.
 * 订单查询表的查询条件
 *
 * @author leefly
 * @version 1.0
 */
public interface QueryFace extends java.io.Serializable {

    boolean isMore();

    /**
     * 获取表
     * @return
     */
    TableStruct[] tables();

    /**
     * 获取片段sql
     *
     * @param alias 指定别名
     * @return 片段sql
     */
    String getQuerySQL(Alias alias, QueryType type);

}
