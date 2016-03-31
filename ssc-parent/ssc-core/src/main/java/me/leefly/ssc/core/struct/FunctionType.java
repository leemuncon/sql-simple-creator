package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2016/1/20.
 *
 * @author leefly
 * @version 1.0
 */
public enum FunctionType {

    /**
     * 总行数
     */
    COUNT("COUNT"),
    /**
     * 字段和
     */
    SUM("SUM"),
    /**
     * 字段最大值
     */
    MAX("MAX"),
    /**
     * 字段最小值
     */
    MIN("MIN"),
    /**
     * 字段平均值
     */
    AVG("AVG");

    private final String func;

    FunctionType(String func) {
        this.func = func;
    }

    /**
     * 获取字段函数
     *
     * @return
     */
    public String function() {
        return func;
    }


}
