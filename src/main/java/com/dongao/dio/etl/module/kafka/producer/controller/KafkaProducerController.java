package com.dongao.dio.etl.module.kafka.producer.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongao.dio.etl.module.kafka.producer.service.facade.KafkaProducerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: KafkaProducerController
 * @Package: com.dongao.dio.etl.module.kafka.producer.controller
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/3/3
 * @Time: 10:45
 * @Description: todo
 * @Copyright: www.dongao.com@2020
 */
@RestController
public class KafkaProducerController {
    @Autowired
    KafkaProducerThread kafkaProducerThread;

    @RequestMapping("/send")
    public boolean send(@RequestParam String message) throws Exception {
        JSONObject object = JSONObject.parseObject("{\n" +
                "  \"member_id\": \"35660515\",\n" +
                "  \"deviceInfo_update_date\": \"2020-05-03T16:00:00.000Z\",\n" +
                "  \"mark\": \"deviceInfo\",\n" +
                "  \"deviceInfo\": {\n" +
                "    \"deviceList\": [\n" +
                "      {\n" +
                "        \"app_name\": null,\n" +
                "        \"is_effective\": 0,\n" +
                "        \"unique_id\": \"c6f08fc09c60946b51c2a816442d8ef6508d1dc7\",\n" +
                "        \"app_version\": null,\n" +
                "        \"last_login_ip\": \"223.104.146.14\",\n" +
                "        \"app_version_split\": {\n" +
                "          \"small\": null,\n" +
                "          \"big\": null,\n" +
                "          \"middle\": null\n" +
                "        },\n" +
                "        \"os_type\": null,\n" +
                "        \"os_version\": null,\n" +
                "        \"model\": \"iPhone8Plus\",\n" +
                "        \"device_type\": 0,\n" +
                "        \"last_login_area\": \"中国 江苏  \",\n" +
                "        \"last_login_date\": \"2020-01-22T16:54:47.000Z\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"app_name\": null,\n" +
                "        \"is_effective\": 0,\n" +
                "        \"unique_id\": \"30258fa05bead15a9c680d1f21fff7694a3876f8\",\n" +
                "        \"app_version\": null,\n" +
                "        \"last_login_ip\": \"183.211.193.157\",\n" +
                "        \"app_version_split\": {\n" +
                "          \"small\": null,\n" +
                "          \"big\": null,\n" +
                "          \"middle\": null\n" +
                "        },\n" +
                "        \"os_type\": null,\n" +
                "        \"os_version\": null,\n" +
                "        \"model\": \"iPad11,3\",\n" +
                "        \"device_type\": 0,\n" +
                "        \"last_login_area\": \"中国 江苏 苏州 \",\n" +
                "        \"last_login_date\": \"2020-01-22T16:57:28.000Z\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"app_name\": \"da-cloudclass-app\",\n" +
                "        \"is_effective\": 1,\n" +
                "        \"unique_id\": \"b97647a40f23bfa54439669497280c8f40d9000c\",\n" +
                "        \"app_version\": \"1.1.3\",\n" +
                "        \"last_login_ip\": \"183.211.192.92\",\n" +
                "        \"app_version_split\": {\n" +
                "          \"small\": 3,\n" +
                "          \"big\": 1,\n" +
                "          \"middle\": 1\n" +
                "        },\n" +
                "        \"os_type\": \"ios\",\n" +
                "        \"os_version\": \"13.4\",\n" +
                "        \"model\": \"iPad11,3\",\n" +
                "        \"device_type\": 2,\n" +
                "        \"last_login_area\": \"中国 江苏 苏州 \",\n" +
                "        \"last_login_date\": \"2020-04-01T06:36:13.000Z\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"app_name\": null,\n" +
                "        \"is_effective\": 1,\n" +
                "        \"unique_id\": \"cfc5aa827616ca8be1edf164a261d49986cd16ca\",\n" +
                "        \"app_version\": null,\n" +
                "        \"last_login_ip\": \"117.136.67.56\",\n" +
                "        \"app_version_split\": {\n" +
                "          \"small\": null,\n" +
                "          \"big\": null,\n" +
                "          \"middle\": null\n" +
                "        },\n" +
                "        \"os_type\": null,\n" +
                "        \"os_version\": null,\n" +
                "        \"model\": \"iPhone8Plus\",\n" +
                "        \"device_type\": 0,\n" +
                "        \"last_login_area\": \"中国 江苏  \",\n" +
                "        \"last_login_date\": \"2020-02-10T14:10:43.000Z\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"device_num\": 4,\n" +
                "    \"active_device_num\": 2\n" +
                "  }\n" +
                "}");

        System.out.println(object);
        kafkaProducerThread.sendMsg(object.toJSONString());
        //kafkaProducerThread.sendMsgSync("[ip],[hostname],[java version],[message] ===>> "+ "["+LogOutputPropertyConst.IPADDRESS+"],["+LogOutputPropertyConst.HOSTNAME+"],["+LogOutputPropertyConst.JAVAVERSION+"],["+message+"]");
        return true;
    }
}
