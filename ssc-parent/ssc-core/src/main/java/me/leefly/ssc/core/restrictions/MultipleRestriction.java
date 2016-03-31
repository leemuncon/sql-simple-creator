package me.leefly.ssc.core.restrictions;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Administrator on 2015/11/13.
 *
 * @author leefly
 * @version 1.0
 */
public class MultipleRestriction extends AbstractRestrictionValue<Collection<? extends Serializable>> {

    private static final long serialVersionUID = -5577951189841208075L;

    public MultipleRestriction(String restriction, String alias, Collection<? extends Serializable> values) {
        super(restriction, alias, values);
    }

    @Override
    public String getRestrictionSQL(String tableAlias, String column) {
        return tableAlias + "." + column + " " + getRestriction() + " (:" + getAlias() + ")";
    }

}
