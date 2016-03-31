package me.leefly.ssc.model;

/**
 * Created by leefly on 2016/4/1.
 */
public class SortBy {

    private String name;

    private SORT sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SORT getSort() {
        return sort;
    }

    public void setSort(SORT sort) {
        this.sort = sort;
    }

    public enum SORT {
        ASC,
        DESC
    }

}
