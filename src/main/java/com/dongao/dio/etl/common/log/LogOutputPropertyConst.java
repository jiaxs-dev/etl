package com.dongao.dio.etl.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * @Title: HostNameConfig
 * @Package: com.dongao.dio.usertags.common.log
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/12
 * @Time: 10:31
 * @Description: logback日志输出公共字段
 * @Copyright: www.dongao.com@2020
 */
public class LogOutputPropertyConst {
    public static String IPADDRESS = "localhost";
    public static String HOSTNAME = "localhost";
    public static String JAVAVERSION = "JDK";
    public static String OS = "Linux";
    public final static Logger logger = LoggerFactory.getLogger(LogOutputPropertyConst.class);

    static {
        try {
            IPADDRESS = InetAddress.getLocalHost().getHostAddress();
            HOSTNAME = InetAddress.getLocalHost().getHostName();
            JAVAVERSION = System.getProperty("java.version");
            OS = System.getProperty("os.name");
        } catch (Exception e) {
            logger.error("日志输出字段初始化失败，将采用默认值输出");
        }
    }
}
