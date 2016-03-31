package me.leefly.ssc.core.struct;

/**
 * Created by Administrator on 2016/3/2.
 *
 * @author leefly
 * @version 1.0
 */
public class Alias implements java.io.Serializable{

    private static final long serialVersionUID = -813579125939333299L;

    private String format;

    public Alias(String format) {
        if (format == null)
            format = "TEMP";
        this.format = format;
    }

    /**
     * 创建表别名
     *
     * @param name 名称
     * @return alias
     */
    public String alias(String name) {
        if (name == null)
            return null;
        return name.toUpperCase() + "_" + format.toUpperCase();
    }

}
