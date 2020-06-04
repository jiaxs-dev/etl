package com.dongao.dio.etl.module.kafka.producer.service.facade;

import org.apache.kafka.clients.producer.KafkaProducer;

public interface KafkaProducerService {


    void sendMsg(String msg);

    void sendMsgSync(String msg) throws Exception;

    void sendMsgSyncCallback(String msg) throws Exception;
}
