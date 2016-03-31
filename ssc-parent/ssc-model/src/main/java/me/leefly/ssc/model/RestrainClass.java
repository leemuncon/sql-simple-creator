package me.leefly.ssc.model;

/**
 * Created by Administrator on 2016/2/29.
 * 用于结构约束
 *
 * @author leefly
 * @version 1.0
 */
public class RestrainClass implements java.io.Serializable {

    private static final long serialVersionUID = 7684310330079921593L;
    /**
     * 约束条件
     */
    private String restrain;
    /**
     * 若标记相同则表示或
     */
    private String mark;

    public RestrainClass(String restrain, String mark) {
        setRestrain(restrain);
        setMark(mark);
    }

    public RestrainClass() {
        // nothing...
    }

    public String getRestrain() {
        return restrain;
    }

    public void setRestrain(String restrain) {
        this.restrain = restrain;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
