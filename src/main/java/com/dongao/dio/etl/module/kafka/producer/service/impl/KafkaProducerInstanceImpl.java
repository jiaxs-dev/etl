package com.dongao.dio.etl.module.kafka.producer.service.impl;

import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerInstance;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Title: KafkaProducerInstanceImpl
 * @Package: com.dongao.dio.etl.module.kafka.producer.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 13:52
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
@Component
public class KafkaProducerInstanceImpl implements KafkaProducerInstance {

    @Value("${kafka.bootstrap.servers}")
    String bootstrapServers;

    @Bean("kafkaProducer")
    @Override
    public KafkaProducer<String, String> initProducer() {
        Properties kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", bootstrapServers);
        kfkProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kfkProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kfkProperties);
        return producer;
    }
}
