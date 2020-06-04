package com.dongao.dio.etl.common.base;

import com.dongao.dio.etl.module.kafka.consumer.service.impl.KafkaConsumerHandleImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author jiaxiansun@dongao.com
 * @date 2020/02/12
 * @description springboot销毁
 */
@Component
public class EndApplication implements DisposableBean, ExitCodeGenerator {
    Logger logger = LoggerFactory.getLogger(EndApplication.class);

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public void destroy() throws Exception {
        //KafkaConsumerHandleImpl.fileWriter.close();
        threadPoolTaskExecutor.shutdown();
        logger.info("开始执行销毁方法");
        logger.info("开始关闭es客户端");
        restHighLevelClient.close();

    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
