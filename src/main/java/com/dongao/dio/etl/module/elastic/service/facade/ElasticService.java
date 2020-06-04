package com.dongao.dio.etl.module.elastic.service.facade;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author jiaiansun@dongao.com
 * @date 2019/10/12 9:57
 * @description
 */
public interface ElasticService {

    /**
     * 批量保存
     * @param records 记录集合
     * @param index 索引名称
     * @param type 文档type
     * @param idField id字段
     * @return 状态码
     * @throws Exception
     */
    int bulksave(List<JSONObject> records, String index, String type, String idField) throws Exception;

    /**
     * 单条文档保存
     * @param record 文档
     * @param index 索引名称
     * @param type 文档type
     * @param idField id字段
     * @return
     * @throws Exception
     */
    int save(JSONObject record, String index, String type, String idField) throws Exception;

    /**
     * 根据id获取文档
     * @param index 索引名称
     * @param type 文档type
     * @param id id字段
     * @return
     * @throws Exception
     */
    JSONObject get(String index, String type, String id) throws Exception;

    long count(String index) throws Exception;

    double max(String index, String field) throws Exception;

    long emptyIndex(String index) throws Exception;
}
