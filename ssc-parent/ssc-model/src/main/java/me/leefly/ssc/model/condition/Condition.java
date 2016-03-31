package me.leefly.ssc.model.condition;

/**
 * Created by leefly on 2016/3/31.
 */
public interface Condition<T> {

    String condition();

    String name();

    T value();

}
