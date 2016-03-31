package me.leefly.ssc.model.condition;

import java.io.Serializable;

/**
 * Created by leefly on 2016/3/31.
 */
public class SimpleCondition implements Condition<Serializable> {

    @Override
    public String condition() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Serializable value() {
        return null;
    }
}
