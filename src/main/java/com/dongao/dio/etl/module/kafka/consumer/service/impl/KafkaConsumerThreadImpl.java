package com.dongao.dio.etl.module.kafka.consumer.service.impl;

import com.dongao.dio.etl.module.kafka.consumer.service.facade.IKafkaConsumerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Title: KafkaConsumerThreadImpl
 * @Package: com.dongao.dio.etl.module.kafak.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/28
 * @Time: 17:23
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
@Component
public class KafkaConsumerThreadImpl implements IKafkaConsumerThread {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Value("${kafka.topics}")
    String topic;
    @Value("${kafka.group}")
    String group;
    @Value("${kafka.bootstrap.servers}")
    String bootstrapServers;

    @Override
    public boolean start() {
        threadPoolTaskExecutor.execute(new KafkaConsumerImpl(topic, group, bootstrapServers, new MemberChannelConsumer()));
        return true;
    }
}
