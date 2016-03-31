package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2015/11/13.
 * 订单排序条件
 *
 * @author leefly
 * @version 1.0
 */
public class SortStruct extends BaseColumn {

    private static final long serialVersionUID = 1246325869046041002L;

    private String sort;

    private SortStruct(TableStruct table, String column, String sort) {
        super(table, column);
        setSort(sort);
    }

    public static SortStruct asc(TableStruct table, String column) {
        return new SortStruct(table, column, "ASC");
    }

    public static SortStruct desc(TableStruct table, String column) {
        return new SortStruct(table, column, "DESC");
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        if (sort == null || !("DESC".equalsIgnoreCase(sort) || "ASC".equalsIgnoreCase(sort)))
            throw new IllegalArgumentException("sort illegal");
        this.sort = sort;
    }
}
