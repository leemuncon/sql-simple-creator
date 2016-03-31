package me.leefly.ssc.core.struct;

import me.leefly.ssc.core.sql.QueryType;

import java.util.*;

/**
 * Created by Administrator on 2016/3/2.
 *
 * @author leefly
 * @version 1.0
 */
public class QueryOrStruct implements QueryFace {

    private static final long serialVersionUID = 7057406603276532080L;

    private List<QuerySingleStruct> queries;

    public QueryOrStruct(List<QuerySingleStruct> queries) {
        setQueries(queries);
    }

    public QueryOrStruct() {
        // nothing...
    }

    public List<QuerySingleStruct> getQueries() {
        return queries;
    }

    public void setQueries(List<QuerySingleStruct> queries) {
        this.queries = queries;
    }

    @Override
    public boolean isMore() {
        return tables().length > 1;
    }

    @Override
    public TableStruct[] tables() {
        Set<TableStruct> cache = new HashSet<TableStruct>();
        for (QuerySingleStruct qs : queries) {
            if (qs == null)
                continue;
            cache.addAll(Arrays.asList(qs.tables()));
        }
        return cache.toArray(new TableStruct[cache.size()]);
    }

    @Override
    public String getQuerySQL(Alias alias, QueryType type) {
        if (getQueries() == null || getQueries().isEmpty())
            return null;
        StringBuilder _sql = new StringBuilder();
        Iterator<QuerySingleStruct> it = getQueries().iterator();
        boolean or = false;
        int count = 0;
        while (it.hasNext()) {
            QuerySingleStruct qss = it.next();
            if (qss == null)
                continue;
            String sql = qss.getQuerySQL(alias, type);
            if (sql == null)
                continue;
            if (or)
                _sql.append(" OR ");
            _sql.append(sql);
            or = it.hasNext();
            count++;
        }
        if (count > 1)
            return "(" + _sql.toString() + ")";
        return _sql.toString();
    }
}
