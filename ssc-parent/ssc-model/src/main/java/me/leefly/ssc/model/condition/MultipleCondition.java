package me.leefly.ssc.model.condition;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by leefly on 2016/4/1.
 */
public class MultipleCondition implements Condition<Collection<Serializable>> {
    @Override
    public String condition() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Collection<Serializable> value() {
        return null;
    }
}
