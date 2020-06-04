package com.dongao.dio.etl.module.kafka.producer.service.facade;

/**
 * @Title: KafkaProducerThread
 * @Package: com.dongao.dio.etl.module.kafka.produce.service.facade
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 10:07
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
public interface KafkaProducerThread{

    void sendMsg();

    void sendMsg(String msg);

    void sendMsgSync(String msg) throws Exception;

    void sendMsgSyncCallback(String msg) throws Exception;
}
