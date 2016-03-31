package me.leefly.ssc.core.sql;

import me.leefly.ssc.core.struct.*;

import java.util.*;

/**
 * Created by Administrator on 2016/3/11.
 *
 * @author leefly
 * @version 1.0
 */
public class LeftJoinSQLCreator {

    private static Alias MAIN = new Alias("MAIN");

    private static Alias SUB = new Alias("SUB");

    // =================================================================================================================


    /**
     * LEFT JOIN 关联
     *
     * @param metadata 表
     * @param queries  关联
     * @return 部分sql
     */
    public static String selectWithJoinSQL(Collection<MetadataStruct> metadata,
                                           Collection<QueryFace> queries,
                                           Collection<SortStruct> sorts) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("no metadata to query");

        Set<MetadataStruct> relates = new HashSet<MetadataStruct>();
        Map<TableStruct, Collection<MetadataStruct>> meta_cache = new HashMap<TableStruct, Collection<MetadataStruct>>();
        StringBuilder sql = new StringBuilder("SELECT ").append(buildColumnSQL(metadata, meta_cache, relates, MAIN, QueryType.USE_NAME));

        Map<TableStruct, Collection<QueryFace>> query_cache = new HashMap<TableStruct, Collection<QueryFace>>();
        for (QueryFace qf : queries) {
            if (qf == null || qf.tables() == null || !meta_cache.keySet().containsAll(Arrays.asList(qf.tables())))
                continue;
            TableStruct key = null;
            if (!qf.isMore())
                key = qf.tables()[0];
            Collection<QueryFace> faces = query_cache.get(key);
            if (faces == null) {
                faces = new ArrayList<QueryFace>();
                query_cache.put(key, faces);
            }
            faces.add(qf);
        }
        return sql.append(buildFromJoinSQL(meta_cache, relates, MAIN, QueryType.USE_NAME))
                .append(buildQuerySQL(query_cache, MAIN, QueryType.USE_NAME))
                .append(buildSortSQL(meta_cache, sorts, MAIN, QueryType.USE_NAME)).toString();
    }

    /**
     * 通过原数据创建数量查询sql
     *
     * @param metadata 原数据
     * @param queries  查询条件
     * @return sql
     * @throws NullPointerException
     */
    public static String countWithJoinSQL(Collection<MetadataStruct> metadata,
                                          Collection<QueryFace> queries) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("metadata is null or metadata is empty");

        Set<MetadataStruct> relates = new HashSet<MetadataStruct>();
        Map<TableStruct, Collection<MetadataStruct>> meta_cache = new HashMap<TableStruct, Collection<MetadataStruct>>();
        buildColumnSQL(metadata, meta_cache, relates, MAIN, QueryType.USE_NAME);

        Map<TableStruct, Collection<QueryFace>> query_cache = new HashMap<TableStruct, Collection<QueryFace>>();
        for (QueryFace qf : queries) {
            if (qf == null || qf.tables() == null || !meta_cache.keySet().containsAll(Arrays.asList(qf.tables())))
                continue;
            TableStruct key = null;
            if (!qf.isMore())
                key = qf.tables()[0];
            Collection<QueryFace> faces = query_cache.get(key);
            if (faces == null) {
                faces = new ArrayList<QueryFace>();
                query_cache.put(key, faces);
            }
            faces.add(qf);
        }
        return "SELECT COUNT(0) " + buildFromJoinSQL(meta_cache, relates, MAIN, QueryType.USE_NAME) +
                buildQuerySQL(query_cache, MAIN, QueryType.USE_NAME);
    }


    //==================================================================================================================


    /**
     * 设置查询要显示的字段
     *
     * @param metadata 原数据
     * @param cache    表缓存
     * @param relates  关联缓存
     * @return 部分sql
     */
    public static String buildColumnSQL(Collection<MetadataStruct> metadata,
                                        Map<TableStruct, Collection<MetadataStruct>> cache,
                                        Collection<MetadataStruct> relates,
                                        Alias alias,
                                        QueryType type) {
        StringBuilder column = new StringBuilder();// sql
        Iterator<MetadataStruct> mit = metadata.iterator();
        boolean comma = false;
        while (mit.hasNext()) {
            MetadataStruct ms = mit.next();
            if (ms == null || ms.getColumn() == null || ms.getTable() == null)
                continue;
            Collection<MetadataStruct> _metadata = cache.get(ms.getTable());
            if (_metadata == null) {
                _metadata = new ArrayList<MetadataStruct>();
                cache.put(ms.getTable(), _metadata);
            }
            _metadata.add(ms);
            if (ms.getRelated() == null) {
                if (ms.getName() == null)
                    continue;
                if (comma)
                    column.append(", ");
                column.append(alias.alias(type.alias(ms.getTable()))).append(".").append(ms.getColumn())
                        .append(" AS ").append(ms.getName());
                comma = mit.hasNext();
            } else {
                relates.add(ms);
            }
        }
        return column.toString();
    }

    /**
     * 子查询
     *
     * @param table    表
     * @param metadata 原数据
     * @param type     是否使用子查询
     * @param queries  查询条件
     * @return sql
     */
    private static String buildSubSelectSQL(Collection<MetadataStruct> metadata,
                                            TableStruct table, Collection<QueryFace> queries, QueryType type) {
        if (table == null)
            throw new NullPointerException("no table to query");
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("no metadata to query");

        Set<QueryFace> _queries = new HashSet<QueryFace>(queries == null ? new ArrayList<QueryFace>() : queries);// 临时保存查询条件
        Set<BaseColumn> _group = new HashSet<BaseColumn>();// 分组条件
        StringBuilder sub = new StringBuilder(" (SELECT ");
        boolean comma = false;
        Iterator<MetadataStruct> mit = metadata.iterator();
        while (mit.hasNext()) {
            MetadataStruct ms = mit.next();
            if (ms == null || ms.getColumn() == null || !table.equals(ms.getTable()))
                continue;
            if (ms.getQuery() != null && !ms.getQuery().isEmpty())
                _queries.addAll(ms.getQuery());// 若自带条件
            if (ms.isGroup())
                _group.add(ms);
            if (comma)
                sub.append(", ");
            if (ms.getFunc() == null) {
                sub.append(SUB.alias(table.getTableName())).append(".").append(ms.getColumn());
            } else {
                sub.append(ms.getFunc().function()).append("(")
                        .append(SUB.alias(table.getTableName())).append(".").append(ms.getColumn())
                        .append(")");
            }
            sub.append(" AS ").append(ms.getColumn());
            comma = mit.hasNext();
        }
        return sub.append(" FROM ").append(table.getTableName()).append(" AS ").append(SUB.alias(table.getTableName()))
                .append(QuerySQLCreator.buildQuerySQL(table, _queries, SUB, type)).append(QuerySQLCreator.buildGroupSQL(table, _group, SUB))
                .append(")").toString();
    }


    /**
     * 设置查询条件
     *
     * @param metadata 查询条件
     * @param relates  别名
     * @param type     查询别名类型
     * @return 部分sql
     */
    public static String buildFromJoinSQL(Map<TableStruct, Collection<MetadataStruct>> metadata,
                                          Collection<MetadataStruct> relates, Alias alias, QueryType type) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("no metadata to query");

        TableStruct primary = null;
        for (TableStruct table : metadata.keySet()) {
            if (table == null)
                continue;
            if (table.isPrimary())
                primary = table;
        }
        if (primary == null)
            throw new NullPointerException("no primary");
        StringBuilder from = new StringBuilder(" FROM ").append(primary.getTableName()).append(" AS ").append(alias.alias(type.alias(primary))).append(" ");
        for (TableStruct table : metadata.keySet()) {
            if (table == null || table.isPrimary())
                continue;
            for (MetadataStruct rs : relates) {
                if (rs == null)
                    continue;
                if (table.equals(rs.getTable())) {
                    BaseColumn rms = rs.getRelated();
                    if (rms == null)
                        continue;
                    from.append("LEFT JOIN ").append(buildSubSelectSQL(metadata.get(table), table, null, type))
                            .append(" AS ").append(alias.alias(type.alias(table))).append(" ON ")
                            .append(alias.alias(type.alias(rs.getTable()))).append(".")
                            .append(rs.getColumn())
                            .append("=").append(alias.alias(type.alias(rms.getTable()))).append(".")
                            .append(rms.getColumn()).append(" ");
                    break;
                }
            }
        }
        return from.toString();
    }

    /**
     * 设置查询条件
     *
     * @param queries 查询条件
     * @param alias   别名
     * @param type    查询别名类型
     * @return 部分sql
     */
    public static String buildQuerySQL(Map<TableStruct, Collection<QueryFace>> queries,
                                       Alias alias, QueryType type) {
        if (queries == null || queries.isEmpty())
            return "";
        int count = 0;
        boolean comma = false;
        StringBuilder where = new StringBuilder(" WHERE ");
        final Iterator<TableStruct> kit = queries.keySet().iterator();
        while (kit.hasNext()) {
            Collection<QueryFace> faces = queries.get(kit.next());
            if (faces == null || faces.isEmpty())
                continue;
            Iterator<QueryFace> rit = faces.iterator();
            while (rit.hasNext()) {
                QueryFace qs = rit.next();
                if (qs == null || qs.getQuerySQL(alias, type) == null)
                    continue;
                if (comma)
                    where.append(" AND ");
                where.append(qs.getQuerySQL(alias, type));
                comma = rit.hasNext() || kit.hasNext();
                count++;
            }
        }
        if (count > 0)
            return where.toString();
        return "";
    }

    /**
     * 设置查询条件
     *
     * @param metadata 元数据
     * @param alias    别名
     * @param type     查询别名类型
     * @return 部分sql
     */
    public static String buildSortSQL(Map<TableStruct, Collection<MetadataStruct>> metadata,
                                      Collection<SortStruct> sorts, Alias alias, QueryType type) {
        if (metadata == null || metadata.isEmpty())
            throw new NullPointerException("no metadata to query");

        if (sorts == null || sorts.isEmpty())
            return "";
        int count = 0;
        StringBuilder sort = new StringBuilder(" ORDER BY ");
        boolean comma = false;
        Iterator<SortStruct> rit = sorts.iterator();
        while (rit.hasNext()) {
            SortStruct ss = rit.next();
            if (ss == null || !metadata.keySet().contains(ss.getTable()))
                continue;
            if (comma)
                sort.append(", ");
            sort.append(alias.alias(type.alias(ss.getTable()))).append(".")
                    .append(ss.getColumn()).append(" ").append(ss.getSort());
            comma = rit.hasNext();
            count++;
        }
        if (count > 0)
            return sort.toString();
        return "";
    }


}
