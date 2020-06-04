package com.dongao.dio.etl.module.kafka.producer.service.impl;

import com.dongao.dio.etl.common.log.LogOutputPropertyConst;
import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerService;
import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Title: KafkaProducerThreadImpl
 * @Package: com.dongao.dio.etl.module.kafka.produce.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 10:34
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
@Component
public class KafkaProducerThreadImpl implements KafkaProducerThread {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void sendMsg() {
        kafkaProducerService.sendMsg(System.currentTimeMillis()+","+ LogOutputPropertyConst.IPADDRESS+","+LogOutputPropertyConst.HOSTNAME);
    }

    @Override
    public void sendMsg(String msg) {
        threadPoolTaskExecutor.execute(new KafkaProducerWorkSendMsg(msg));
    }

    @Override
    public void sendMsgSync(String msg) throws Exception {
        threadPoolTaskExecutor.execute(new KafkaProducerWorkSendMsgSync(msg));
    }

    @Override
    public void sendMsgSyncCallback(String msg) throws Exception {
        threadPoolTaskExecutor.execute(new KafkaProducerWorkSendMsgSyncCallback(msg));
    }

    private class KafkaProducerWorkSendMsg extends Thread{
        String msg;
        public KafkaProducerWorkSendMsg(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            KafkaProducerThreadImpl.this.kafkaProducerService.sendMsg(msg);
        }
    }

    private class KafkaProducerWorkSendMsgSync extends Thread{
        String msg;
        public KafkaProducerWorkSendMsgSync(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            try{
                KafkaProducerThreadImpl.this.kafkaProducerService.sendMsgSync(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class KafkaProducerWorkSendMsgSyncCallback extends Thread{
        String msg;
        public KafkaProducerWorkSendMsgSyncCallback(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            try{
                KafkaProducerThreadImpl.this.kafkaProducerService.sendMsgSyncCallback(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
