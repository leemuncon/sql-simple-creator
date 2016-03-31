package me.leefly.ssc.core.restrictions;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author leefly
 * @version 1.0
 */
public abstract class AbstractRestriction implements Restriction {

    private static final long serialVersionUID = 6760598535996189802L;
    /**
     * 约束条件
     */
    private String restriction;

    public AbstractRestriction(String restriction) {
        setRestriction(restriction);
    }

    public AbstractRestriction() {
        // nothing...
    }

    public String getRestriction() {
        return restriction;
    }

    /**
     * 若约束条件需解析，重写该方法
     *
     * @param restriction 约束条件
     */
    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

}
