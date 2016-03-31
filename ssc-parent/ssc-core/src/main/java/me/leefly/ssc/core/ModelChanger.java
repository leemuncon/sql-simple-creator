package me.leefly.ssc.core;

import me.leefly.ssc.core.restrictions.EasyRestriction;
import me.leefly.ssc.core.struct.*;
import me.leefly.ssc.model.ColumnClass;
import me.leefly.ssc.model.ColumnModel;
import me.leefly.ssc.model.RestrainClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/29.
 *
 * @author leefly
 * @version 1.0
 */
public class ModelChanger {

    /**
     * 转换字段模型为结构模型
     *
     * @param columns 字段
     * @return 结构
     */
    public static List<MetadataStruct> changeColumnModel(List<ColumnModel> columns) {
        if (columns == null || columns.isEmpty())
            return null;
        Map<String, TableStruct> tableCache = new HashMap<String, TableStruct>();// 表缓存
        Map<String, MetadataStruct> metadataCache = new HashMap<String, MetadataStruct>();
        Map<String, String> relateCache = new HashMap<String, String>();
        for (ColumnModel model : columns) {
            if (model == null || model.getTableName() == null || model.getColumn() == null)
                continue;
            TableStruct table = tableCache.get(model.getTableName());
            if (table == null) {
                table = new TableStruct(model.getTableName(), model.getTableAlias(), model.isPrimary(), model.getIndex());
                tableCache.put(model.getTableName(), table);
            }
            MetadataStruct ms = new MetadataStruct();
            ms.setTable(table);
            ms.setColumn(model.getColumn());
            ms.setName(model.getShowName());
            ms.setFunc(model.getFunc() == null ? null : FunctionType.valueOf(model.getFunc()));
            ms.setGroup(model.isGroupBy());
            if (model.getRestrains() != null && !model.getRestrains().isEmpty()) {
                Map<String, List<QuerySingleStruct>> cache = new HashMap<String, List<QuerySingleStruct>>();
                for (RestrainClass restrain : model.getRestrains()) {
                    if (restrain == null)
                        continue;
                    List<QuerySingleStruct> qss = cache.get(restrain.getMark());
                    if (qss == null) {
                        qss = new ArrayList<QuerySingleStruct>();
                        cache.put(restrain.getMark(), qss);
                    }
                    qss.add(new QuerySingleStruct(table, model.getColumn(), new EasyRestriction(restrain.getRestrain()), null));
                }
                List<QueryFace> faces = new ArrayList<QueryFace>();
                for (String mark : cache.keySet()) {
                    List<QuerySingleStruct> qs = cache.get(mark);
                    if (qs == null)
                        continue;
                    if (mark == null)
                        faces.addAll(qs);
                    else
                        faces.add(new QueryOrStruct(qs));
                }
                ms.setQuery(faces);
            }
            ColumnClass rel = model.getRelated();
            if (rel != null && rel.getTableName() != null)
                relateCache.put(table.getTableName() + "_" + ms.getColumn(), rel.getTableName() + "_" + rel.getColumn());
            metadataCache.put(table.getTableName() + "_" + ms.getColumn(), ms);
        }
        for (String key : relateCache.keySet()) {
            MetadataStruct ms = metadataCache.get(key);
            ms.setRelated(metadataCache.get(relateCache.get(key)));
        }
        return new ArrayList<MetadataStruct>(metadataCache.values());
    }

}
