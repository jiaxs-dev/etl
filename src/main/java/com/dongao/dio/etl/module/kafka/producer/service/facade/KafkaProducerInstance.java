package com.dongao.dio.etl.module.kafka.producer.service.facade;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @Title: KafkaProducerInstance
 * @Package: com.dongao.dio.etl.module.kafka.producer.service.facade
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 13:52
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
public interface KafkaProducerInstance {

    /**
     * @Author: jiaxiansun@dongao.com
     * @Date: 2020/3/3 10:15
     * @Description: 初始化kafka生产者客户端
     * @params
     * @return
     */
    KafkaProducer<String,String> initProducer();
}
