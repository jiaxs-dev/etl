package com.dongao.dio.etl.module.kafka.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dongao.dio.etl.common.elastic.ElasticHttpClientConfiguration;
import com.dongao.dio.etl.module.kafka.consumer.service.facade.IKafkaConsumerHandle;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.FileWriter;
import java.util.List;

/**
 * @Title: KafkaConsumerHandleImpl
 * @Package: com.dongao.dio.etl.module.kafak.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/28
 * @Time: 17:26
 * @Description: kafka中message处理逻辑
 * @Copyright: www.dongao.com@2020
 */
public class KafkaConsumerHandleImpl implements IKafkaConsumerHandle {

    int i = 0;
    private static RestHighLevelClient restHighLevelClient;
    public static FileWriter fileWriter;

    static {
        /*//fileWriter = new FileWriter(new File("/usr/local/elastic/data/member_info_json/data.json"));
        try {
            //fileWriter = new FileWriter(new File("/usr/local/elastic/data/member_info_json/data.json"));
            //fileWriter = new FileWriter(new File("G:\\data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        restHighLevelClient = ElasticHttpClientConfiguration.initBasicSecurityClient("esn4.dabig.com:9200", "elastic", "elastic");
    }

    @Override
    public void handle(List<String> messages) {
        try {
            BulkRequest request = new BulkRequest();
            for (String message : messages) {
                JSONObject object = JSON.parseObject(message);
                JSONArray plan = object.getJSONArray("plan_credentials");
                if (null != plan && plan.size() == 1 && plan.get(0).equals("")) {
                    object.put("plan_credentials", new String[]{});
                }

                JSONArray credentials = object.getJSONArray("credentials");
                if (null != credentials && credentials.size() == 1 && credentials.get(0).equals("")) {
                    object.put("credentials", new String[]{});
                }

                JSONArray kj_level = object.getJSONArray("kj_level");
                if (null != kj_level && kj_level.size() == 1 && kj_level.get(0).equals("")) {
                    object.put("kj_level", new String[]{});
                }

                JSONObject obj = new JSONObject();
                String member_id = object.getString("member_id");
                obj.put("member_id", member_id);
                object.remove("member_id");
                object.remove("@version");
                obj.put("basicInfo_update_date", object.getString("@timestamp"));
                object.remove("@timestamp");
                obj.put("basicInfo", object);
                request.add(new IndexRequest("biz_member_info","_doc",member_id).source(obj));
                //request.add(new IndexRequest("biz_member_info_copy","_doc",member_id).source(obj));
            }
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
