package com.wqq.self.elk.service;

import com.wqq.self.common.utils.SpringBeanUtils;
import com.wqq.self.elk.enums.OperationEnum;
import com.wqq.self.elk.utils.ElasticSearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author wqq
 * @Date 2022/6/14 16:06
 */
@Service
@Slf4j
public class OutLineServiceImpl {

    private static final String index = "pdc_operation";

//    @Autowired
//    private ElasticsearchRestTemplate esRestTemplate;


    /**
     * 根据某个字段聚合，查询此字段所有分类的数量
     */
    public Map<String, Long> aggregateByField(String field) {
        SearchRequest request = new SearchRequest().indices(index);
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .aggregation(AggregationBuilders
                        .terms("count")
                        .field(field)
                );
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse response = ElasticSearchUtils.search(request, RequestOptions.DEFAULT);

        Aggregations aggregations = response.getAggregations();
        Terms terms = (Terms) aggregations.asMap().get("count");
        Map<String, Long> cityCount = new HashMap(16);
        // 遍历取出聚合字段列的值，与对应的数量
        for (Terms.Bucket bucket : terms.getBuckets()) {
            String key = bucket.getKeyAsString(); // 聚合字段列的值
            long val = bucket.getDocCount();// 聚合字段对应的数量
            cityCount.put(key, val);
        }
        return cityCount;
    }

    /**
     * 某个确定的操作，在最近时间段内，每天的操作数量
     */
    public Map<String, Long> aggregateByFieldBetweenDate(OperationEnum operation, LocalDate start, LocalDate end) {
        SearchRequest request = new SearchRequest().indices(index);
        //日期范围内
        RangeQueryBuilder dateRange = QueryBuilders.rangeQuery("date")
                .format("yyyy-MM-dd")
                .gte(start)
                .lte(end)
                .includeLower(true)
                .includeUpper(true);
        //操作名称查询
        TermQueryBuilder nameQuery = QueryBuilders.termQuery("operation", operation.getName());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //根据日期聚合
        sourceBuilder.query(dateRange)
                        .query(nameQuery)
                        .aggregation(AggregationBuilders.terms("count")
                        .field("date")
                );
        request.source(sourceBuilder);
        SearchResponse response = ElasticSearchUtils.search(request, RequestOptions.DEFAULT);

        Aggregations entitiesAggregations = response.getAggregations();
        Terms terms = (Terms) entitiesAggregations.asMap().get("count");

        Map<String, Long> cityCount = new HashMap(16);
        // 遍历取出聚合字段列的值，与对应的数量
        for (Terms.Bucket bucket : terms.getBuckets()) {
            String key = bucket.getKeyAsString().substring(0, 10); // 聚合字段列的值
            long val = bucket.getDocCount();// 聚合字段对应的数量
            cityCount.put(key, val);
        }
        return cityCount;
    }


    public Map<OperationEnum, Long> allTypeCount() {
        ElasticsearchRestTemplate springBean = SpringBeanUtils.getSpringBean(ElasticsearchRestTemplate.class);
        System.out.println(springBean);
        return null;
//        return operationMap;
    }

    public Map<String, Long> mapCount() {
        //根据城市名称聚合查询，目前返回拼音
        Map<String, Long> stringLongMap = this.aggregateByField("geoip.geo.city_name");
        return stringLongMap;
    }

    public Map<String, Long> recentBusinessCount(LocalDate start, LocalDate end) {
        //根据日期聚合查询日期范围内交易总量
        return this.aggregateByFieldBetweenDate(OperationEnum.VISIT, start, end);
    }

    public Map<String, Long> recentDataAssetsCount(LocalDate start, LocalDate end) {
        //根据日期聚合查询日期范围内资产总量
        return this.aggregateByFieldBetweenDate(OperationEnum.ADD_DATA_ASSETS, start, end);
    }

    public Map<String, Long> recentDataTransferCount(LocalDate start, LocalDate end) {
        //根据日期聚合查询日期范围内资产流转总量
        return this.aggregateByFieldBetweenDate(OperationEnum.ADD_DATA_TRANSFER, start, end);
    }

}
