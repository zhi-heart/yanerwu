//package com.yanerwu.common.es;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHitField;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
///**
// * es查询处理
// */
//public abstract class QueryHelper {
//    protected int maxHits;
//    private boolean levelQuery = false;
//    public abstract void query(SearchRequestBuilder builder, int size);
//
//    public Pagination doResult(SearchResponse response) {
//        SearchHits hits = response.getHits();
//        List<JSONObject> pglist = new ArrayList();
//        List<JSONObject> toplist = new ArrayList();
//
//        List<JSONObject> list;
//        for(SearchHit hit : hits){
//            list = pglist;
//            if (levelQuery && hit.getHighlightFields().get("keyword") != null){
//                list = toplist;
//            }
//            list.add(getRecord(hit));
//        }
//        Pagination pagination = new Pagination((int)hits.getTotalHits());
//
//        if (toplist.size()>1){
//            pagination.setMaxElements(toplist.size());
//            pglist = toplist;
//            toplist = null;
//
//        }
//        pagination.setList(pglist);
//
//        return pagination;
//    }
//
//    public JSONObject getRecord(SearchHit hit){
//
//        JSONObject obj ;
//        Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
//
//        String sourceAsString = hit.getSourceAsString();
//        if (sourceAsString != null) {
//            obj = JSON.parseObject(sourceAsString);
//        }else{
//            obj = new JSONObject();
//            for (SearchHitField field : hit.fields().values()) {
//                obj.put(field.name(), field.value());
//            }
//        }
//        Collection<HighlightField> values = highlightFieldMap.values();
//        if (highlightFieldMap.size() > 0){
//            for (HighlightField field : highlightFieldMap.values()) {
//                obj.put(field.name(), StringUtils.join(field.getFragments()));
//            }
//        }
//
//        return obj;
//    }
//
//    public int getMaxHits() {
//        return maxHits;
//    }
//
//    public void setMaxHits(int maxHits) {
//        this.maxHits = maxHits;
//    }
//
//    public boolean isLevelQuery() {
//        return levelQuery;
//    }
//
//    public void setLevelQuery(boolean levelQuery) {
//        this.levelQuery = levelQuery;
//    }
//}
