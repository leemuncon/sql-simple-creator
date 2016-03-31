package me.leefly.ssc.core;

import me.leefly.ssc.core.sql.LeftJoinSQLCreator;
import me.leefly.ssc.core.sql.QueryType;
import me.leefly.ssc.core.sql.SimpleSQLCreator;
import me.leefly.ssc.core.struct.*;

import java.util.*;

/**
 * Created by Administrator on 2015/11/12.
 *
 * @author leefly
 * @version 1.0
 */
public class SQLCreator {

    /**
     * 通过原数据创建sql, 包含group by
     *
     * @param metadata 原数据
     * @param queries  查询条件
     * @return sql
     * @throws NullPointerException
     */
    public static String selectJoinSQL(Collection<MetadataStruct> metadata,
                                       Collection<QueryFace> queries) {
        return LeftJoinSQLCreator.selectWithJoinSQL(metadata, queries, null);
    }

    /**
     * 通过原数据创建sql
     *
     * @param metadata 原数据
     * @param sorts    排序条件
     * @param queries  查询条件
     * @return sql
     */
    public static String selectJoinSQLWithSort(Collection<MetadataStruct> metadata,
                                               Collection<QueryFace> queries,
                                               Collection<SortStruct> sorts) {
        return LeftJoinSQLCreator.selectWithJoinSQL(metadata, queries, sorts);
    }

    /**
     * 通过原数据创建数量查询sql
     *
     * @param metadata 原数据
     * @param queries  查询条件
     * @return sql
     * @throws NullPointerException
     */
    public static String countJoinSQL(Collection<MetadataStruct> metadata,
                                      Collection<QueryFace> queries) {
        return LeftJoinSQLCreator.countWithJoinSQL(metadata, queries);
    }

    /**
     * 获取插入sql
     *
     * @param metadata 元数据
     * @param table    表
     * @return sql
     */
    public static String insertSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table) {
        return SimpleSQLCreator.insertSQL(metadata, table);
    }

    /**
     * 获取插入sql
     *
     * @param metadata 元数据
     * @param table    表
     * @param queries  查询条件
     * @return sql
     */
    public static String updateSQL(Collection<MetadataStruct> metadata,
                                   TableStruct table,
                                   Collection<QueryFace> queries) {
        return SimpleSQLCreator.updateSQL(metadata, table, queries);
    }

    /**
     * 初始化查询条件
     *
     * @param queries 附加表缓存
     * @return 部分sql
     */
    public static Map<TableStruct, Collection<QueryFace>> buildQueries(Collection<QueryFace> queries) {
        Map<TableStruct, Collection<QueryFace>> map = new HashMap<TableStruct, Collection<QueryFace>>();
        for (QueryFace qf : queries) {
            if (qf == null || qf.table() == null)
                continue;
            Collection<QueryFace> faces = map.get(qf.table());
            if (faces == null) {
                faces = new ArrayList<QueryFace>();
                map.put(qf.table(), faces);
            }
            faces.add(qf);
        }
        return map;
    }

    /**
     * 设置查询字段，并添加表及关联
     *
     * @param metadata 原数据
     * @param tables   表缓存
     * @param relates  关联
     * @param alias    别名
     * @return 部分sql
     */
    public static String buildColumns(Collection<MetadataStruct> metadata,
                                      Map<TableStruct, Collection<MetadataStruct>> tables,
                                      Collection<MetadataStruct> relates,
                                      Alias alias) {
        return LeftJoinSQLCreator.buildColumnSQL(metadata, tables, relates, alias, QueryType.USE_NAME);
    }

    /**
     * LEFT JOIN 关联
     *
     * @param tables  表
     * @param relates 关联
     * @return 部分sql
     */
    public static String buildFromJoin(Map<TableStruct, Collection<MetadataStruct>> tables,
                                       List<MetadataStruct> relates,
                                       Alias alias) {
        return LeftJoinSQLCreator.buildFromJoinSQL(tables, relates, alias, QueryType.USE_NAME);
    }

    /**
     * 设置查询条件
     *
     * @param queries 查询条件
     * @param alias   别名
     * @return 部分sql
     */
    public static String buildRestriction(Map<TableStruct, Collection<QueryFace>> queries,
                                          Alias alias) {
        return LeftJoinSQLCreator.buildQuerySQL(queries, alias, QueryType.USE_NAME);
    }

}
