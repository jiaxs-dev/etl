package com.dongao.dio.etl.common.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @Title: ElasticSniffer
 * @Package: com.dongao.dio.usertags.common.elastic
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/12
 * @Time: 10:31
 * @Description: elasticsearch客户端嗅探器
 * @Copyright: www.dongao.com@2020
 */
/*@Component
@DependsOn("restHighLevelClient")*/
public class ElasticSniffer {

    /*@Value("${elasticsearch.sniffer.retry.millis}")
    int millis;

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Bean
    public Sniffer initSniffer(){
        Sniffer sniffer = Sniffer.builder(restHighLevelClient.getLowLevelClient())
                .setSniffIntervalMillis(millis)
                .setSniffAfterFailureDelayMillis(millis)
                .build();
        return sniffer;

    }*/
}
