package com.dongao.dio.etl.module.elastic.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongao.dio.etl.common.utils.DateUtils;
import com.dongao.dio.etl.module.elastic.service.facade.ElasticService;
import com.dongao.dio.etl.module.mail.service.facade.MailService;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jiaxiansun@dongao.com
 * @date 2019/10/12 9:58
 * @description
 */
@Service
public class ElasticServiceImpl implements ElasticService {

    public final static Logger logger = LoggerFactory.getLogger(ElasticServiceImpl.class);

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    MailService mailService;
    @Value("${spring.mail.username}")
    String from;
    @Value("${spring.mail.to}")
    String to;


    @Override
    public int bulksave(List<JSONObject> records, String index, String type, String idField) throws Exception {
        int n = 0;
        BulkRequest bulkRequest = new BulkRequest();
        Map<String, JSONObject> re = new HashMap<>();
        for (JSONObject record : records) {
            IndexRequest indexRequest = new IndexRequest(index).source(record).type(type);
            if (null != idField && !"".equals(idField) && null != record.getString(idField) && !"".equals(record.getString(idField))) {
                indexRequest.id(record.getString(idField));
                re.put(record.getString(idField), record);
            }
            indexRequest.timeout(new TimeValue(2, TimeUnit.MINUTES));
            bulkRequest.add(indexRequest);

        }
        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                Iterator<BulkItemResponse> responseIterator = bulkResponse.iterator();
                while (responseIterator.hasNext()) {
                    if (responseIterator.next().isFailed()) {
                        logger.info("id:[{}],提交失败，失败原因如下：{}", responseIterator.next().getId(), responseIterator.next().getFailureMessage());
                        mailService.send(from, to, "es数据写入异常",
                                DateUtils.formatDate2Str(new Date()) +
                                        "id:[" + responseIterator.next().getId() + "]提交失败，" +
                                        "失败原因如下：[" + responseIterator.next().getFailureMessage() + "]，" +
                                        "源数据如下：[" + re.get(responseIterator.next().getId()).toJSONString() + "]");
                        n++;
                    }
                }
            }
        }
        return n;
    }

    @Override
    public JSONObject get(String index, String type, String id) throws Exception {
        GetRequest getRequest = new GetRequest(index, type, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return JSON.parseObject(getResponse.getSourceAsString());
    }

    @Override
    public long count(String index) throws Exception {
        CountRequest countRequest = new CountRequest(index);
        return restHighLevelClient.count(countRequest, RequestOptions.DEFAULT).getCount();
    }

    public double max(String index, String field) throws Exception {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(AggregationBuilders.max(field).field(field));
        searchRequest.source(searchSourceBuilder);
        Max max = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getAggregations().get(field);
        return max.getValue();
    }


    @Override
    public long emptyIndex(String index) throws Exception {
        logger.info("开始清空索引{}的数据", index);
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(index);
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        long deleted = bulkByScrollResponse.getDeleted();
        logger.info("索引{}数据清空完毕,共删除{}条数据", index, deleted);
        logger.info("开始执行索引{}的forcemerge", index);
        ForceMergeRequest forceMergeRequest = new ForceMergeRequest(index);
        ForceMergeResponse forceMergeResponse = restHighLevelClient.indices().forceMerge(forceMergeRequest, RequestOptions.DEFAULT);
        logger.info("索引{}的forcemerge执行完毕，执行状态为{}", index, forceMergeResponse.getStatus().getStatus());
        return deleted;
    }

    @Override
    public int save(JSONObject record, String index, String type, String idField) throws Exception {
        IndexResponse indexResponse = restHighLevelClient.index(new IndexRequest(index, type).source(record).id(record.getString(idField)), RequestOptions.DEFAULT);
        return indexResponse.status().getStatus();
    }
}
