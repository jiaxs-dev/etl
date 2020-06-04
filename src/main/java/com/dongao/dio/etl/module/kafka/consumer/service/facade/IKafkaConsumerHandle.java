package com.dongao.dio.etl.module.kafka.consumer.service.facade;

import java.util.List;

/**
 * @Title: IKafkaConsumerHandle
 * @Package: com.dongao.dio.etl.module.kafak.service.facade
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/28
 * @Time: 17:20
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
public interface IKafkaConsumerHandle {

    void handle(List<String> message);
}
