package me.leefly.ssc.core;

import me.leefly.ssc.core.restrictions.*;
import me.leefly.ssc.core.struct.*;
import me.leefly.ssc.model.SortBy;
import me.leefly.ssc.model.condition.Condition;
import me.leefly.ssc.model.condition.LikeCondition;
import me.leefly.ssc.model.condition.MultipleCondition;
import me.leefly.ssc.model.condition.SimpleCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 *
 * @author leefly
 * @version 1.0
 */
public class StructParser {

    /**
     * 解析查询条件
     *
     * @param metadata  原数据
     * @param condition 查询条件
     * @return {@link QuerySingleStruct}
     */
    public static QueryFace parseCondition(List<MetadataStruct> metadata, Condition<?> condition) {
        if (condition == null)
            return null;
        else if (condition.condition() == null || "".equals(condition.condition()))
            return null;
        else if (condition.name() == null || "".equals(condition.name()))
            return null;
        QuerySingleStruct qs = new QuerySingleStruct();
        String name = condition.name().trim();
        String rest = condition.condition().trim();
        for (MetadataStruct ms : metadata) {
            if (ms == null)
                continue;
            else if (ms.getTable() == null)
                continue;
            else if (ms.getColumn() == null)
                continue;
            if (name.equals(ms.getName())) {
                qs.setTable(ms.getTable());
                qs.setColumn(ms.getColumn());
                break;
            }
        }
        if (condition instanceof SimpleCondition) {
            qs.setRestriction(new SimpleRestriction(rest, name, null));
            qs.setValue(((SimpleCondition) condition).value());
        } else if (condition instanceof MultipleCondition) {
            qs.setRestriction(new MultipleRestriction(rest, name, null));
            qs.setValue(((MultipleCondition) condition).value());
        } else if (condition instanceof LikeCondition) {
            qs.setRestriction(new LikeRestriction(name, ((LikeCondition) condition).likeFix(), null));
            qs.setValue(((LikeCondition) condition).value());
        } else {
            return null;
        }
        return qs;
    }

    /**
     * 创建排序查询条件
     *
     * @param metadata 原数据
     * @param sort     排序条件
     * @return 排序条件
     */
    public static SortStruct getSortStruct(List<MetadataStruct> metadata, SortBy sort) {
        if (metadata == null || metadata.isEmpty() ||
                sort == null || sort.getName() == null)
            return null;
        for (MetadataStruct ms : metadata) {
            if (ms == null)
                continue;
            if (sort.getName().equals(ms.getName())) {
                if (SortBy.SORT.ASC.equals(sort.getSort()))
                    return SortStruct.asc(ms.getTable(), ms.getColumn());
                else if (SortBy.SORT.DESC.equals(sort.getSort()))
                    return SortStruct.desc(ms.getTable(), ms.getColumn());
            }
        }
        return null;
    }

}
