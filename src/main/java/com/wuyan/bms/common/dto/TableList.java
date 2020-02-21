package com.wuyan.bms.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * TableList
 *
 * @author {yuanwei}
 * @date 2020/2/19 15:57
 */
@Data
public class TableList implements Serializable {
    private String tableName;
    private String className;
}
