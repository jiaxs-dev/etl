package com.dongao.dio.etl.module.kafka.producer.service.impl;

import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerService;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @Title: KafkaProducerImpl
 * @Package: com.dongao.dio.etl.module.kafka.produce.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 10:09
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
@Component
@DependsOn("kafkaProducer")
public class KafkaProducerImpl implements KafkaProducerService {

    @Autowired
    KafkaProducer kafkaProducer;

    @Value("${kafka.topics}")
    String topic;

    public void sendMsg(String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
        kafkaProducer.send(record);
    }

    public void sendMsgSync(String msg) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
        RecordMetadata result = (RecordMetadata) kafkaProducer.send(record).get();
        System.out.println("时间戳，主题，分区，位移: " + result.timestamp() + "," + record.topic() + "," + result.partition() + "," + result.offset());

    }

    public void sendMsgSyncCallback(String msg) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
        kafkaProducer.send(record,new MyProducerCallBack());
    }

    private static class MyProducerCallBack implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (null != e) {
                e.printStackTrace();
                return;
            }
            System.out.println("时间戳，主题，分区，位移: " + recordMetadata.timestamp() + ", " + recordMetadata.topic() + "," + recordMetadata.partition() + " " + recordMetadata.offset());
        }
    }
}
