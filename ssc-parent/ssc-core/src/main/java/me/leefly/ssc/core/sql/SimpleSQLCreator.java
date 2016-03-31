package me.leefly.ssc.core.sql;

import me.leefly.ssc.core.struct.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/11.
 *
 * @author leefly
 * @version 1.0
 */
public class SimpleSQLCreator {

    private static Alias SAVE = new Alias("SAVE");

    private static Alias QUERY = new Alias("QUERY");

    /**
     * 获取插入sql
     *
     * @param table    表
     * @param metadata 元数据
     * @return sql
     */
    public static String insertSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("metadata is null or empty");
        if (table == null)
            throw new NullPointerException("table is null");

        StringBuilder column = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Set<MetadataStruct> cache = new HashSet<MetadataStruct>();
        for (MetadataStruct ms : metadata) {
            if (ms == null || ms.getTable() == null || !table.equals(ms.getTable()))
                continue;
            cache.add(ms);
        }
        boolean comma = false;
        Iterator<MetadataStruct> cit = cache.iterator();
        while (cit.hasNext()) {
            MetadataStruct ms = cit.next();
            if (ms == null || ms.getTable() == null || !table.equals(ms.getTable()))
                continue;
            if (comma) {
                column.append(", ");
                values.append(", ");
            }
            column.append(ms.getColumn());
            values.append(":").append(ms.getColumn());
            comma = cit.hasNext();
        }
        return "INSERT " + table.getTableName() + " (" + column.toString() + ")  VALUES (" + values.toString() + ")";
    }


    /**
     * 生成update SQL
     *
     * @param metadata 要跟新的元数据
     * @param table    要跟新的表
     * @param queries  限制条件
     * @return SQL
     */
    public static String updateSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table,
                                   Collection<QueryFace> queries) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("metadata is null or empty");
        if (table == null)
            throw new NullPointerException("table is null");
        StringBuilder update = new StringBuilder();
        Set<MetadataStruct> cache = new HashSet<MetadataStruct>();
        for (MetadataStruct ms : metadata) {
            if (ms == null || ms.getTable() == null)
                continue;
            if (table.equals(ms.getTable()))
                cache.add(ms);
        }
        boolean comma = false;
        Iterator<MetadataStruct> cit = cache.iterator();
        while (cit.hasNext()) {
            MetadataStruct ms = cit.next();
            if (ms == null || ms.getTable() == null || !table.equals(ms.getTable()))
                continue;
            if (comma)
                update.append(", ");
            update.append(SAVE.alias(table.getTableName()))
                    .append(".").append(ms.getColumn()).append("=:").append(ms.getColumn());
            comma = cit.hasNext();
        }
        StringBuilder sql = new StringBuilder("UPDATE ").append(table.getTableName())
                .append(" AS ").append(SAVE.alias(table.getTableName())).append(" SET ")
                .append(update.toString());
        return sql.append(QuerySQLCreator.buildQuerySQL(table, queries, SAVE, QueryType.USE_NAME)).toString();
    }

    /**
     * 通过原数据创建数量查询 SQL
     *
     * @param table   要查询的表
     * @param queries 查询条件
     * @return SQL
     */
    public static String countSQL(TableStruct table,
                                  Collection<QueryFace> queries) {
        if (table == null)
            throw new NullPointerException("table is null");
        return "SELECT COUNT(0) FROM " + table.getTableName() + " AS " + QUERY.alias(table.getTableName()) +
                QuerySQLCreator.buildQuerySQL(table, queries, QUERY, QueryType.USE_NAME);
    }

    /**
     * 通过原数据创建查询 SQL
     *
     * @param metadata 原数据
     * @param table    要查询的表
     * @param queries  查询条件
     * @return SQL
     */
    public static String selectSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table,
                                   Collection<QueryFace> queries) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("metadata is null empty");
        if (table == null)
            throw new NullPointerException("table is null");

        StringBuilder column = new StringBuilder();
        Set<BaseColumn> group = new HashSet<BaseColumn>();// 分组条件
        Iterator<MetadataStruct> mit = metadata.iterator();
        boolean comma = false;
        while (mit.hasNext()) {
            MetadataStruct ms = mit.next();
            if (ms == null || ms.getColumn() == null || !table.equals(ms.getTable()))
                continue;
            if (ms.getName() == null)
                continue;
            if (ms.isGroup())
                group.add(ms);
            if (comma)
                column.append(", ");
            if (ms.getFunc() == null) {
                column.append(QUERY.alias(table.getTableName())).append(".").append(ms.getColumn());
            } else {
                column.append(ms.getFunc().function()).append("(")
                        .append(QUERY.alias(table.getTableName())).append(".").append(ms.getColumn())
                        .append(")");
            }
            column.append(" AS ").append(ms.getName());
            comma = mit.hasNext();
        }
        return "SELECT " + column.toString() + " FROM " + table.getTableName() + " AS " + QUERY.alias(table.getTableName()) +
                QuerySQLCreator.buildQuerySQL(table, queries, QUERY, QueryType.USE_NAME) +
                QuerySQLCreator.buildGroupSQL(table, group, QUERY);
    }

    /**
     * 通过原数据创建查询 SQL
     *
     * @param metadata 原数据
     * @param table    要查询的表
     * @param queries  查询条件
     * @param sorts    排序条件
     * @return SQL
     */
    public static String selectSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table,
                                   Collection<QueryFace> queries,
                                   Collection<SortStruct> sorts) {
        return selectSQL(metadata, table, queries) + QuerySQLCreator.buildSortSQL(table, sorts, QUERY);
    }
}
