package me.leefly.ssc.core.restrictions;

/**
 * Created by Administrator on 2015/11/13.
 *
 * @author leefly
 * @version 1.0
 */
public class EasyRestriction extends AbstractRestriction {

    private static final long serialVersionUID = 7319652304919165046L;

    public EasyRestriction(String restrain) {
        super(restrain);
    }

    public EasyRestriction() {
        super();
    }

    @Override
    public String getRestrictionSQL(String tableAlias, String column) {
        return tableAlias + "." + column + " " + getRestriction();
    }

}
