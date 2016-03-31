package me.leefly.ssc.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 *
 * @author leefly
 * @version 1.0
 */
public class ColumnModel extends ColumnClass {

    private static final long serialVersionUID = 5992740591695537134L;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 字段函数
     */
    private String func;
    /**
     * 字段约束
     */
    private List<RestrainClass> restrains;
    /**
     * 该字段是否要分组
     */
    private boolean groupBy;
    /**
     * 关联字段
     */
    private ColumnClass related;

    public ColumnModel(String tableName, String tableAlias, boolean primary, int index,
                       String column, String showName, String func, List<RestrainClass> restrains,
                       boolean groupBy, ColumnClass related) {
        super(tableName, tableAlias, primary, index, column);
        setShowName(showName);
        setFunc(func);
        setRestrains(restrains);
        setGroupBy(groupBy);
        setRelated(related);
    }

    public ColumnModel() {
        // nothing
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public List<RestrainClass> getRestrains() {
        return restrains;
    }

    public void setRestrains(List<RestrainClass> restrains) {
        this.restrains = restrains;
    }

    public boolean isGroupBy() {
        return groupBy;
    }

    public void setGroupBy(boolean groupBy) {
        this.groupBy = groupBy;
    }

    public ColumnClass getRelated() {
        return related;
    }

    public void setRelated(ColumnClass related) {
        this.related = related;
    }
}
