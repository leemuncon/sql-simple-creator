package me.leefly.ssc.model.condition;

/**
 * Created by leefly on 2016/3/31.
 */
public abstract class AbstractCondition {

    /**
     * Լ������
     */
    private String condition;
    /**
     * �������ͬ���ʾ��
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
