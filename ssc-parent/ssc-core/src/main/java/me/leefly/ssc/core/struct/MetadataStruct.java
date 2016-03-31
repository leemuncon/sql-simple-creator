package me.leefly.ssc.core.struct;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 * 订单查询表的原数据
 *
 * @author leefly
 * @version 1.0
 */
public class MetadataStruct extends BaseColumn {

    private static final long serialVersionUID = 6688730258146973722L;

    /**
     * 带关联的原数据
     *
     * @param table   表
     * @param column  字段
     * @param name    名称
     * @param func    查询函数
     * @param related 关联
     * @param query 查询条件
     * @param group   分组
     */
    public MetadataStruct(TableStruct table,
                          String column,
                          String name,
                          FunctionType func,
                          BaseColumn related,
                          List<QueryFace> query,
                          boolean group) {
        super(table, column);
        setName(name);
        setFunc(func);
        setRelated(related);
        setQuery(query);
        setGroup(group);
    }

    public MetadataStruct() {
        // nothing
    }

    /**
     * 名称
     */
    private String name;
    /**
     * 字段函数
     */
    private FunctionType func;
    /**
     * 关联字段
     */
    private BaseColumn related;
    /**
     * 查询条件
     */
    private List<QueryFace> query;
    /**
     * 是否要分组
     */
    private boolean group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FunctionType getFunc() {// 不验证是否为null
        return func;
    }

    public void setFunc(FunctionType func) {
        this.func = func;
    }

    public BaseColumn getRelated() {
        return related;
    }

    public void setRelated(BaseColumn related) {
        this.related = related;
    }

    public List<QueryFace> getQuery() {
        return query;
    }

    public void setQuery(List<QueryFace> query) {
        this.query = query;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (obj == this)
            return true;
        else if (obj instanceof MetadataStruct)
            return this.hashCode() == obj.hashCode();
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + (name == null ? 0 : name.hashCode());
        result = 37 * result + (func == null ? 0 : func.hashCode());
        result = 37 * result + (related == null ? 0 : related.hashCode());
        result = 37 * result + (query == null ? 0 : query.hashCode());
        result = 37 * result + (group ? 1 : 0);
        return result;
    }

}
