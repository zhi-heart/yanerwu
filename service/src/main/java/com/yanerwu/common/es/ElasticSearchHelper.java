//package com.yanerwu.common.es;
//
//import com.alibaba.fastjson.JSON;
//import com.yanerwu.utils.Tools;
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.bulk.BulkItemResponse;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequestBuilder;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.action.update.UpdateRequestBuilder;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import java.io.Serializable;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: Zuz
// * @Description:
// * @Date: 2017/4/27 17:26
// */
//public class ElasticSearchHelper {
//
//    private Logger log = Logger.getLogger(this.getClass());
//    private TransportClient client;
//
//    /**
//     * 连接集群并提供链接的集群名称
//     *
//     * @param clusterName
//     * @param clusterNodes
//     * @throws UnknownHostException
//     */
//    public ElasticSearchHelper(String clusterName, String clusterNodes) throws UnknownHostException {
//        synchronized (TransportClient.class) {
//            Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
//            if (client == null && clusterNodes != null) {
//                String[] nodes = clusterNodes.split(",");
//                ArrayList<InetSocketTransportAddress> list = new ArrayList();
//                for (String node : nodes) {
//                    String[] info = node.split(":");
//                    if (info.length == 2) {
//                        list.add(new InetSocketTransportAddress(InetAddress.getByName(info[0]), Integer.parseInt(info[1])));
//                    }
//                }
//
//                client = new PreBuiltTransportClient(settings)
//                        .addTransportAddresses(list.toArray(new InetSocketTransportAddress[0]));
//            }
//        }
//    }
//
//    /**
//     * 创建Mapping
//     *
//     * @param index
//     * @param type
//     * @param mapping
//     */
//    public void mapping(String index, String type, String mapping) {
//        try {
//            if (client.admin().indices().prepareExists(index).get().isExists()) {
//                client.admin().indices().preparePutMapping(index).setType(type).setSource(mapping).get();
//            } else {
//                client.admin().indices().prepareCreate(index).addMapping(type, mapping).get();
//            }
//        } catch (Exception e) {
//            log.error("mapping error", e);
//        }
//    }
//
//    /**
//     * 批量保存数据
//     *
//     * @param index
//     * @param type
//     * @param beans
//     */
//    public void bulkIndex(String index, String type, Map<String, ?> beans) {
//        BulkRequestBuilder bulkBuilder = client.prepareBulk();
//        IndexRequestBuilder builder;
//        if (beans != null && !beans.isEmpty()) {
//            for (Serializable id : beans.keySet()) {
//                builder = client.prepareIndex(index, type, id.toString());
//                setSource(builder, beans.get(id));
//                bulkBuilder.add(builder);
//            }
//        }
//        BulkResponse response = bulkBuilder.get();
//        if (response.hasFailures()) {
//            log.info("responses hasFailures" + response.buildFailureMessage());
//        }
//    }
//
//    /**
//     * 保存单个数据
//     *
//     * @param index
//     * @param type
//     * @param id
//     * @param bean
//     */
//    public boolean index(String index, String type, String id, Object bean) {
//        IndexRequestBuilder builder = client.prepareIndex(index, type, id);
//        setSource(builder, bean);
//        builder.get();
//        return true;
//    }
//
//    /**
//     * 自动选择Source
//     *
//     * @param builder
//     * @param bean
//     * @return
//     */
//    private void setSource(IndexRequestBuilder builder, Object bean) {
//        byte[] data = JSON.toJSONBytes(bean);
//        builder.setSource(data);
//    }
//
//    /**
//     * 更新数据
//     *
//     * @param index
//     * @param type
//     * @param id
//     * @param bean
//     */
//    public void update(String index, String type, String id, Object bean) {
//        UpdateRequestBuilder builder = client.prepareUpdate(index, type, id);
//        try {
//            byte[] data = JSON.toJSONBytes(bean);
//            builder.setDoc(data);
//        } catch (Exception e) {
//            log.error("update error", e);
//        }
//        builder.get();
//    }
//
//    /**
//     * 批量更新数据
//     *
//     * @param index
//     * @param type
//     * @param beans
//     */
//    public void bulkUpdate(String index, String type, Map<String, ?> beans) {
//        BulkRequestBuilder bulkBuilder = client.prepareBulk();
//        UpdateRequestBuilder builder;
//        if (beans != null && !beans.isEmpty()) {
//            for (Serializable id : beans.keySet()) {
//                builder = client.prepareUpdate(index, type, id.toString());
//                try {
//                    byte[] data = JSON.toJSONBytes(beans.get(id));
//                    builder.setDoc(data);
//                } catch (Exception e) {
//                    log.error("update error", e);
//                }
//                bulkBuilder.add(builder);
//            }
//        }
//        bulkBuilder.get();
//    }
//
//    /**
//     * 精确删除
//     *
//     * @param index
//     * @param type
//     * @param id
//     */
//    public void delete(String index, String type, String id) {
//        client.prepareDelete(index, type, id).get();
//    }
//
//    public void deleteByQuery(String index, String type, BoolQueryBuilder builder) {
//        long rowCount = -1;
//        int size = 10000;
//        do {
//            BulkRequestBuilder bulkRequest = client.prepareBulk();
//
//            SearchResponse response = client.prepareSearch(index).setTypes(type)
//                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                    .setQuery(builder)
//                    .setFrom(0)
//                    .setSize(size)
//                    .execute()
//                    .actionGet();
//
//            rowCount = response.getHits().getTotalHits();
//            log.info(String.format("还剩[%s]未删除", rowCount));
//
//            if (rowCount > 0) {
//                for (SearchHit hit : response.getHits()) {
//                    String id = hit.getId();
//                    bulkRequest.add(client.prepareDelete(index, type, id).request());
//                }
//                BulkResponse bulkResponse = bulkRequest.get();
//                if (bulkResponse.hasFailures()) {
//                    for (BulkItemResponse item : bulkResponse.getItems()) {
//                        log.info(item.getFailureMessage());
//                    }
//                } else {
//                    log.info("delete ok");
//                }
//            }
//        } while (rowCount - size > 0);
//
//
//    }
//
//    /**
//     * 查询单条记录
//     *
//     * @param index
//     * @param type
//     * @param id
//     * @return
//     */
//    public Map<String, Object> query(String index, String type, String id) {
//        GetResponse response = client.prepareGet(index, type, id).get();
//        return response.getSource();
//    }
//
//    /**
//     * 检索符合条件的所有数据
//     *
//     * @param indexes
//     * @param types
//     * @param queryHelper
//     * @return
//     */
//    public List<Map<String, Object>> queryList(String[] indexes, String[] types, QueryHelper queryHelper) {
//        return queryPage(indexes, types, 1, Integer.MAX_VALUE, queryHelper).getList();
//    }
//
//    /**
//     * 检索符合条件的条数
//     *
//     * @param indexes
//     * @param types
//     * @param limit
//     * @param queryHelper
//     * @return
//     */
//    public List<Map<String, Object>> queryList(String[] indexes, String[] types, int limit, QueryHelper queryHelper) {
//        return queryPage(indexes, types, 1, limit, queryHelper).getList();
//    }
//
//    public long queryCount(String[] indexes, String[] types, QueryHelper queryHelper) {
//        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
//        queryHelper.query(builder, 0);
//        return builder.get().getHits().getTotalHits();
//    }
//
//    public long queryCount(String[] indexes, String[] types, QueryBuilder queryBuilder) {
//        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSize(0);
//        return builder.setQuery(queryBuilder).get().getHits().getTotalHits();
//    }
//
//    /**
//     * 根据条件分页查询
//     *
//     * @param indexes
//     * @param types
//     * @param queryHelper
//     * @return
//     */
//    public Pagination<Map<String, Object>> queryPage(String[] indexes, String[] types, int page, int pagesize, QueryHelper queryHelper) {
//        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
//        queryHelper.query(builder, 0);
//        //API限定最高10000个
//        if (pagesize > 10000) {
//            pagesize = 10000;
//        }
//        builder.setFrom((page - 1) * pagesize);
//        builder.setSize(pagesize);
//        SearchResponse response = builder.get();
//        System.out.println(builder);
//        Pagination pagination = queryHelper.doResult(response);
//        pagination.setCurrentPage(page);
//        pagination.setPageSize(pagesize);
//
//        return pagination;
//    }
//
//    /**
//     * 聚合查询
//     *
//     * @param indexes
//     * @param types
//     * @param queryHelper
//     * @return
//     */
//    public Pagination queryAggs(String[] indexes, String[] types, int groupSize, QueryHelper queryHelper) {
//        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
//        queryHelper.query(builder, groupSize);
//
//        SearchResponse response = builder.get();
//        System.out.println(builder);
//        Pagination pagination = queryHelper.doResult(response);
//        pagination.setCurrentPage(1);
//        pagination.setPageSize(20);
//        return pagination;
//    }
//
//    public void setClient(TransportClient client) {
//        this.client = client;
//    }
//
//    public static void main(String[] args) throws UnknownHostException {
////        ElasticSearchHelper helper = new ElasticSearchHelper("yanerwu", "106.14.171.91:9300");
//
////        Map<String, Object> map = new HashMap<String, Object>();
////        map.put("id", 1);
////        map.put("status", 1);
////        map.put("type", 1);
////        map.put("title", "主站新闻标题");
////        map.put("content", "主站新闻内容");
////        helper.index(Keys.INDEX_TAGS, Keys.TYPE_SELECT_TAGS, Tools.getUUID(), map);
////
////        map.put("id", 2);
////        map.put("title", "BBS帖子标题");
////        map.put("content", "BBS帖子内容");
////        map.put("type", 2);
////        helper.index(Keys.INDEX_TAGS, Keys.TYPE_SELECT_TAGS, Tools.getUUID(), map);
//
//    	/*helper.delete(Keys.INDEX_TAGS,Keys.TYPE_SELECT_TAGS,"9ac772ba5b924affa4751d3e58c7b4f0");
//        helper.delete(Keys.INDEX_TAGS,Keys.TYPE_SELECT_TAGS,"f803de332a1c4a3cb79b5847eeda3bd7");
//    	helper.delete(Keys.INDEX_TAGS,Keys.TYPE_SELECT_TAGS,"8c43e165c69c4359a243f57b23fe907e");
//    	helper.delete(Keys.INDEX_TAGS,Keys.TYPE_SELECT_TAGS,"1bec60e3d39a485d8d4921041e5b57f1");*/
//
//
//        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("106.14.171.91"), 9300));
//        client.admin().cluster();
//
//    }
//}
