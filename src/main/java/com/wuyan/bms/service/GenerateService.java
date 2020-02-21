package com.wuyan.bms.service;

import com.wuyan.bms.common.dto.BeanField;
import com.wuyan.bms.common.dto.GenerateInput;
import com.wuyan.bms.common.dto.TableList;
import com.wuyan.bms.common.result.Results;

import java.util.List;

/**
 * GenerateService
 *
 * @author {yuanwei}
 * @date 2020/2/19 14:49
 */
public interface GenerateService {

    /**
     * 获取数据库表信息
     *
     * @param tableName
     * @return
     */
    List<BeanField> listBeanField(String tableName);

    /**
     * 转成驼峰并大写第一个字母
     *
     * @param string
     * @return
     */
    String upperFirstChar(String string);

    Results<TableList> getTableList();

    void saveCode(GenerateInput input);
}
