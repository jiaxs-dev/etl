package com.dongao.dio.etl.module.kafka.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongao.dio.etl.common.elastic.ElasticHttpClientConfiguration;
import com.dongao.dio.etl.module.kafka.consumer.service.facade.IKafkaConsumerHandle;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;

import java.util.List;

public class MemberChannelConsumer implements IKafkaConsumerHandle {
    private static RestHighLevelClient restHighLevelClient;

    static {
        restHighLevelClient = ElasticHttpClientConfiguration.initBasicSecurityClient("esn4.dabig.com:9200", "elastic", "elastic");
    }

    @Override
    public void handle(List<String> messages) {
        try {
            BulkRequest request = new BulkRequest();
            for (String message : messages) {
                JSONObject object = JSON.parseObject(message);
                String member_id = object.getString("member_id");
                request.add(new UpdateRequest("member_test", "_doc", member_id).doc(object).docAsUpsert(true).script(new Script(
                        "ctx._source.gender = \"" + object.getLong("channel_id") + "\""
                )));
            }
            if (request.numberOfActions() > 0) {
                restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
