//package com.yanerwu.service;
//
//import com.alibaba.fastjson.JSON;
//import com.yanerwu.common.DbUtilsTemplate;
//import com.yanerwu.common.Page;
//import com.yanerwu.common.es.ElasticSearchHelper;
//import com.yanerwu.common.es.Pagination;
//import com.yanerwu.common.es.QueryHelper;
//import com.yanerwu.entity.MvList;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.functionscore.ExponentialDecayFunctionBuilder;
//import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
//
///**
// * @author Zuz
// * @version 1.0
// * @since 1.0
// */
//@Service
//public class MovieService {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private DbUtilsTemplate yanerwuTemplate;
//
//    @Autowired
//    private ElasticSearchHelper elasticSearchHelper;
//
//
//    public Page findPage(Page page, String searchKey) {
//        String result = "";
//        int errorCode = 0;
//        String errorMessage = "成功";
//
//        try {
//            Pagination<Map<String, Object>> pagination = elasticSearchHelper.queryPage(new String[]{"movie"}, new String[]{"base"}, (int) page.getCurrentPage(), (int) page.getNumPerPage(), new SimpleQueryHelper(page, searchKey));
//
//            List<MvList> mvLists = JSON.parseObject(JSON.toJSONString(pagination.getList()), new TypeReference<List<MvList>>() {
//            });
//
//            page.setResult(mvLists);
//        } catch (Exception e) {
//            errorCode = 500;
//            errorMessage = "内部错误!";
//            logger.error("", e);
//        }
//        return page;
//
//    }
//
//}
//
//class SimpleQueryHelper extends QueryHelper {
//
//    private Page page;
//    private String searchKey;
//
//    public SimpleQueryHelper(Page page, String searchKey) {
//        this.page = page;
//        this.searchKey = searchKey;
//    }
//
//    @Override
//    public void query(SearchRequestBuilder builder, int size) {
//
//        BoolQueryBuilder query = boolQuery();
//
//        query.should(QueryBuilders.multiMatchQuery(searchKey, "name", "actor", "summary"));
//        query.should(QueryBuilders.wildcardQuery("name", String.format("*%s*", searchKey)).boost(1.2f));
//        query.should(QueryBuilders.multiMatchQuery(searchKey, "name.raw")).boost(9999);
//
//
//        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
//                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
//                        new ExponentialDecayFunctionBuilder("pubTime", System.currentTimeMillis(), "5d", null, 0.8)
//                )
//        };
//
//        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(query, functions);
//
//        HighlightBuilder hiBuilder = new HighlightBuilder();
//        hiBuilder.preTags("<font color='#FF0000'>");
//        hiBuilder.postTags("</font>");
//        hiBuilder.field("name", 10);
//        hiBuilder.field("actor");
//        hiBuilder.field("summary", 100);
//
//        builder.setQuery(functionScoreQuery)
//                .highlighter(hiBuilder)
//        ;
//    }
//}
