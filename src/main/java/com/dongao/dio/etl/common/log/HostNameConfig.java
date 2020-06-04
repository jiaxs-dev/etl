package com.dongao.dio.etl.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @Title: HostNameConfig
 * @Package: com.dongao.dio.usertags.common.log
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/12
 * @Time: 10:31
 * @Description: logback输出主机名称配置
 * @Copyright: www.dongao.com@2020
 */
public class HostNameConfig extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return LogOutputPropertyConst.HOSTNAME;
    }
}
