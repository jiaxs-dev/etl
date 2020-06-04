package com.dongao.dio.etl.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongao.dio.etl.module.kafka.consumer.service.facade.IKafkaConsumerThread;
import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerThread;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jiaxiansun@dongao.com
 * @date 2019/10/11 17:17
 * @description
 */
@Component
public class StartInitApplication implements ApplicationRunner {

    public final static Logger logger = LoggerFactory.getLogger(StartInitApplication.class);

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    IKafkaConsumerThread iKafkaConsumerThread;

    @Autowired
    KafkaProducerThread kafkaProducerThread;

    /**
     * 启动确认同步游标最小位置
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        //iKafkaConsumerThread.start();
        /*final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

        SearchRequest searchRequest = new SearchRequest("biz_member_info");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(500);
        searchSourceBuilder.query(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("basicInfo.channel_id")));
        searchSourceBuilder.fetchSource(new String[]{"member_id"}, null);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            for (SearchHit searchHit : searchResponse.getHits()) {
                JSONObject object = new JSONObject();
                object.put("member_id", Long.parseLong(searchHit.getId()));
                object.put("channel_id", -99);
                kafkaProducerThread.sendMsg(JSON.toJSONString(object));
            }
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        restHighLevelClient.close();*/
    }
}
