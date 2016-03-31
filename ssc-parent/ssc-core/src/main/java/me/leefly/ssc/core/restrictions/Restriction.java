package me.leefly.ssc.core.restrictions;

/**
 * Created by Administrator on 2015/11/13.
 *
 * @author leefly
 * @version 1.0
 */
public interface Restriction extends java.io.Serializable {

    /**
     * 获取片段sql
     *
     * @param tableAlias 表别名
     * @param column     字段
     * @return 片段sql
     */
    String getRestrictionSQL(String tableAlias, String column);

}
