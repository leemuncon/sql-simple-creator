package me.leefly.ssc.core.restrictions;

/**
 * Created by Administrator on 2016/3/1.
 *
 * @author leefly
 * @version 1.0
 */
public interface RestrictionValue<T> extends Restriction{

    String getAlias();

    T getValue();
}
