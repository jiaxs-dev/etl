package com.dongao.dio.etl.common.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/11 10:46
 * @Description: jenkins校验接口controller
 */

@RestController
public class BaseController {

    @RequestMapping("/hello")
    public boolean hello() {
        return true;
    }
}
