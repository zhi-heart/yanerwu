package com.yanerwu.common.es;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 17:26
 */
public class ElasticSearchHelper {

    private Logger log = LogManager.getLogger(this.getClass());
    private TransportClient client;

    /**
     * 连接集群并提供链接的集群名称
     *
     * @param clusterName
     * @param clusterNodes
     * @throws UnknownHostException
     */
    public ElasticSearchHelper(String clusterName, String clusterNodes) throws UnknownHostException {
        synchronized (TransportClient.class) {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", false)
                    .build();
            if (client == null && clusterNodes != null) {
                String[] nodes = clusterNodes.split(",");
                ArrayList<InetSocketTransportAddress> list = new ArrayList();
                for (String node : nodes) {
                    String[] info = node.split(":");
                    if (info.length == 2) {
                        list.add(new InetSocketTransportAddress(InetAddress.getByName(info[0]), Integer.parseInt(info[1])));
                    }
                }

                client = new PreBuiltTransportClient(settings)
                        .addTransportAddresses(list.toArray(new InetSocketTransportAddress[0]));
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {

        ElasticSearchHelper helper = new ElasticSearchHelper("zuozhi", "192.168.10.1:9300");
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("status", 1);
        map.put("type", 1);
        map.put("title", "主站新闻标题");
        map.put("content", "主站新闻内容");
        helper.index(Keys.INDEX_FULL, Keys.TYPE_SELECT_BASE, "1", map);
    }

    /**
     * 创建Mapping
     *
     * @param index
     * @param type
     * @param mapping
     */
    public void mapping(String index, String type, String mapping) {
        try {
            if (client.admin().indices().prepareExists(index).get().isExists()) {
                client.admin().indices().preparePutMapping(index).setType(type).setSource(mapping).get();
            } else {
                client.admin().indices().prepareCreate(index).addMapping(type, mapping).get();
            }
        } catch (Exception e) {
            log.error("mapping error", e);
        }
    }

    /**
     * 批量保存数据
     *
     * @param index
     * @param type
     * @param beans
     */
    public void bulkIndex(String index, String type, Map<String, ?> beans) {
        BulkRequestBuilder bulkBuilder = client.prepareBulk();
        IndexRequestBuilder builder;
        if (beans != null && !beans.isEmpty()) {
            for (Serializable id : beans.keySet()) {
                builder = client.prepareIndex(index, type, id.toString());
                setSource(builder, beans.get(id));
                bulkBuilder.add(builder);
            }
        }
        BulkResponse response = bulkBuilder.get();
        if (response.hasFailures()) {
            log.info("responses hasFailures" + response.buildFailureMessage());
        }
    }

    /**
     * 保存单个数据
     *
     * @param index
     * @param type
     * @param id
     * @param bean
     */
    public boolean index(String index, String type, String id, Object bean) {
        IndexRequestBuilder builder = client.prepareIndex(index, type, id);
        setSource(builder, bean);
        builder.get();
        return true;
    }

    /**
     * 自动选择Source
     *
     * @param builder
     * @param bean
     * @return
     */
    private void setSource(IndexRequestBuilder builder, Object bean) {
        if (bean instanceof String) {
            builder.setSource(String.valueOf(bean));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                builder.setSource(mapper.writeValueAsBytes(bean));
            } catch (JsonProcessingException e) {
                log.error("", e);
            }
        }
    }

    /**
     * 更新数据
     *
     * @param index
     * @param type
     * @param id
     * @param bean
     */
    public void update(String index, String type, String id, Object bean) {
        UpdateRequestBuilder builder = client.prepareUpdate(index, type, id);
        try {
            byte[] data = JSON.toJSONBytes(bean);
            builder.setDoc(data);
        } catch (Exception e) {
            log.error("update error", e);
        }
        builder.get();
    }

    /**
     * 批量更新数据
     *
     * @param index
     * @param type
     * @param beans
     */
    public void bulkUpdate(String index, String type, Map<String, ?> beans) {
        BulkRequestBuilder bulkBuilder = client.prepareBulk();
        UpdateRequestBuilder builder;
        if (beans != null && !beans.isEmpty()) {
            for (Serializable id : beans.keySet()) {
                builder = client.prepareUpdate(index, type, id.toString());
                try {
                    byte[] data = JSON.toJSONBytes(beans.get(id));
                    builder.setDoc(data);
                } catch (Exception e) {
                    log.error("update error", e);
                }
                bulkBuilder.add(builder);
            }
        }
        bulkBuilder.get();
    }

    /**
     * 精确删除
     *
     * @param index
     * @param type
     * @param id
     */
    public void delete(String index, String type, String id) {
        client.prepareDelete(index, type, id).get();
    }

    public void deleteByQuery(String index, String type, BoolQueryBuilder builder) {
        long rowCount = -1;
        int size = 10000;
        do {
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            SearchResponse response = client.prepareSearch(index).setTypes(type)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(builder)
                    .setFrom(0)
                    .setSize(size)
                    .execute()
                    .actionGet();

            rowCount = response.getHits().getTotalHits();
            log.info(String.format("还剩[%s]未删除", rowCount));

            if (rowCount > 0) {
                for (SearchHit hit : response.getHits()) {
                    String id = hit.getId();
                    bulkRequest.add(client.prepareDelete(index, type, id).request());
                }
                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    for (BulkItemResponse item : bulkResponse.getItems()) {
                        log.info(item.getFailureMessage());
                    }
                } else {
                    log.info("delete ok");
                }
            }
        } while (rowCount - size > 0);


    }

    /**
     * 查询单条记录
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    public Map<String, Object> query(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id).get();
        return response.getSource();
    }

    /**
     * 检索符合条件的所有数据
     *
     * @param indexes
     * @param types
     * @param queryHelper
     * @return
     */
    public List<Map<String, Object>> queryList(String[] indexes, String[] types, QueryHelper queryHelper) {
        return queryPage(indexes, types, 1, Integer.MAX_VALUE, queryHelper).getList();
    }

    /**
     * 检索符合条件的条数
     *
     * @param indexes
     * @param types
     * @param limit
     * @param queryHelper
     * @return
     */
    public List<Map<String, Object>> queryList(String[] indexes, String[] types, int limit, QueryHelper queryHelper) {
        return queryPage(indexes, types, 1, limit, queryHelper).getList();
    }

    public long queryCount(String[] indexes, String[] types, QueryHelper queryHelper) {
        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        queryHelper.query(builder, 0);
        return builder.get().getHits().getTotalHits();
    }

    public long queryCount(String[] indexes, String[] types, QueryBuilder queryBuilder) {
        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSize(0);
        return builder.setQuery(queryBuilder).get().getHits().getTotalHits();
    }

    /**
     * 根据条件分页查询
     *
     * @param indexes
     * @param types
     * @param queryHelper
     * @return
     */
    public Pagination<Map<String, Object>> queryPage(String[] indexes, String[] types, int page, int pagesize, QueryHelper queryHelper) {
        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        queryHelper.query(builder, 0);
        //API限定最高10000个
        if (pagesize > 10000) {
            pagesize = 10000;
        }
        builder.setFrom((page - 1) * pagesize);
        builder.setSize(pagesize);
        SearchResponse response = builder.get();


        Pagination pagination = queryHelper.doResult(response);
        pagination.setCurrentPage(page);
        pagination.setPageSize(pagesize);

        return pagination;
    }

    /**
     * 聚合查询
     *
     * @param indexes
     * @param types
     * @param queryHelper
     * @return
     */
    public Pagination queryAggs(String[] indexes, String[] types, int groupSize, QueryHelper queryHelper) {
        SearchRequestBuilder builder = client.prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        queryHelper.query(builder, groupSize);

        SearchResponse response = builder.get();
        System.out.println(builder);
        Pagination pagination = queryHelper.doResult(response);
        pagination.setCurrentPage(1);
        pagination.setPageSize(20);
        return pagination;
    }

    public void setClient(TransportClient client) {
        this.client = client;
    }
}
