package com.dongao.dio.etl.module.kafka.consumer.service.impl;

import com.dongao.dio.etl.module.kafka.consumer.service.facade.IKafkaConsumerHandle;
import com.dongao.dio.etl.module.kafka.consumer.service.facade.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Title: KafkaConsumerImpl
 * @Package: com.dongao.dio.etl.module.kafak.service
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/28
 * @Time: 16:56
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
public class KafkaConsumerImpl implements KafkaConsumer {

    String topic;
    String group;
    String bootstrapServers;
    IKafkaConsumerHandle kafkaConsumerHandle;

    public KafkaConsumerImpl(String topic, String group, String bootstrapServers, IKafkaConsumerHandle kafkaConsumerHandle) {
        this.topic = topic;
        this.group = group;
        this.bootstrapServers = bootstrapServers;
        this.kafkaConsumerHandle = kafkaConsumerHandle;
    }

    private org.apache.kafka.clients.consumer.KafkaConsumer createConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", group);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", true);
        properties.put("auto.offset.reset", "earliest");//earliest(从topic的开始位置消费所有消息)  latest(接受接收最大的offset(即最新消息))


        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        return consumer;
    }

    @Override
    public void run() {
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = createConsumer();
        TopicPartition partition = new TopicPartition(topic, 0);
        List<TopicPartition> lists = new ArrayList<TopicPartition>();
        lists.add(partition);
        consumer.assign(lists);
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                List<String> messages = new ArrayList<>();
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    messages.add(message);
                }
                kafkaConsumerHandle.handle(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
