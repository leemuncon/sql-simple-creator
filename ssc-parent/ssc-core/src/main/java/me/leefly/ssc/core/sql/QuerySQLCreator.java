package me.leefly.ssc.core.sql;

import me.leefly.ssc.core.struct.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/3/11.
 *
 * @author leefly
 * @version 1.0
 */
public class QuerySQLCreator {


    /**
     * 获取查询条件SQL 即 WHERE xxx
     *
     * @param table   要查询的表
     * @param queries 查询条件
     * @param alias   别名
     * @param type     是否子条件
     * @return 部分SQL
     */
    public static String buildQuerySQL(TableStruct table,
                                       Collection<QueryFace> queries,
                                       Alias alias, QueryType type) {
        if (table == null || queries == null || queries.isEmpty())
            return "";
        int count = 0;
        boolean comma = false;
        StringBuilder where = new StringBuilder(" WHERE ");
        Iterator<QueryFace> rit = queries.iterator();
        while (rit.hasNext()) {
            QueryFace qs = rit.next();
            if (qs == null || qs.isMore() || !Arrays.asList(qs.tables()).contains(table)
                    || qs.getQuerySQL(alias, type) == null)
                continue;
            if (comma)
                where.append(" AND ");
            where.append(qs.getQuerySQL(alias, type));
            comma = rit.hasNext();
            count++;
        }
        if (count > 0)
            return where.toString();
        return "";
    }


    /**
     * 设置分组
     *
     * @param table 表
     * @param group 分组
     * @param alias 别名
     * @return 部分sql
     */
    public static String buildGroupSQL(TableStruct table,
                                       Collection<BaseColumn> group,
                                       Alias alias) {
        if (table == null || group == null || group.isEmpty())
            return "";
        int count = 0;
        boolean comma = false;
        StringBuilder grp = new StringBuilder(" GROUP BY ");
        Iterator<BaseColumn> git = group.iterator();
        while (git.hasNext()) {
            BaseColumn gs = git.next();
            if (gs == null || !table.equals(gs.getTable()))
                continue;
            if (comma)
                grp.append(", ");
            grp.append(alias.alias(table.getTableName())).append(".").append(gs.getColumn());
            comma = git.hasNext();
            count++;
        }
        if (count > 0)
            return grp.toString();
        return "";
    }

    /**
     * 设置排序
     *
     * @param table 表
     * @param sorts 排序
     * @param alias 别名
     * @return 部分sql
     */
    public static String buildSortSQL(TableStruct table,
                                      Collection<SortStruct> sorts,
                                      Alias alias) {
        if (sorts == null || sorts.isEmpty())
            return "";
        StringBuilder sort = new StringBuilder("ORDER BY ");
        int count = 0;
        boolean comma = false;
        Iterator<SortStruct> sit = sorts.iterator();
        while (sit.hasNext()) {
            SortStruct ss = sit.next();
            if (ss == null || !table.equals(ss.getTable()))
                continue;
            if (comma)
                sort.append(", ");
            sort.append(alias.alias(table.getTableName())).append(".")
                    .append(ss.getColumn()).append(" ").append(ss.getSort());
            comma = sit.hasNext();
            count++;
        }
        if (count > 0)
            return sort.toString();
        return "";
    }

    /**
     * 设置排序
     *
     * @param tables 表
     * @param sorts  排序
     * @param alias  别名
     * @return 部分sql
     */
    public static String buildSortSQL(Collection<TableStruct> tables,
                                      Collection<SortStruct> sorts,
                                      Alias alias) {
        if (sorts == null || sorts.isEmpty())
            return "";
        StringBuilder sort = new StringBuilder("ORDER BY ");
        int count = 0;
        boolean comma = false;
        Iterator<SortStruct> sit = sorts.iterator();
        while (sit.hasNext()) {
            SortStruct ss = sit.next();
            if (ss == null || !tables.contains(ss.getTable()))
                continue;
            if (comma)
                sort.append(", ");
            sort.append(alias.alias(ss.getTable().getTableName())).append(".")
                    .append(ss.getColumn()).append(" ").append(ss.getSort());
            comma = sit.hasNext();
            count++;
        }
        if (count > 0)
            return sort.toString();
        return "";
    }


}
