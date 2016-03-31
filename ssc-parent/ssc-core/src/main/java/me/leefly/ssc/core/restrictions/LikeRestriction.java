package me.leefly.ssc.core.restrictions;

import me.leefly.ssc.model.condition.LikeFix;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author leefly
 * @version 1.0
 */
public class LikeRestriction extends AbstractRestrictionValue<String> {

    private static final long serialVersionUID = -9092662410492728286L;

    private String prefix = "";

    private String suffix = "";

    public LikeRestriction(String alias, LikeFix fix, String value) {
        super("LIKE", alias, value);
        if (LikeFix.PREFIX.equals(fix))
            prefix = "%";
        else if (LikeFix.SUFFIX.equals(fix))
            suffix = "%";
        else if (LikeFix.FULL.equals(fix)) {
            prefix = "%";
            suffix = "%";
        }
    }

    @Override
    public String getRestrictionSQL(String tableAlias, String column) {
        return tableAlias + "." + column + " " +
                getRestriction() + " " + suffix + ":" + getAlias() + " " + prefix;
    }

}
