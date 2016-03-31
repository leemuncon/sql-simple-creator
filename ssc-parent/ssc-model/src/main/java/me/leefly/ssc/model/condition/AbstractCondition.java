package me.leefly.ssc.model.condition;

/**
 * Created by leefly on 2016/3/31.
 */
public abstract class AbstractCondition {

    /**
     * 约束条件
     */
    private String condition;
    /**
     * 若标记相同则表示或
     */
    private String mark;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
