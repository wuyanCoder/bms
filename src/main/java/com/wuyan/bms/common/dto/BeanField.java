package com.wuyan.bms.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * BeanField
 *
 * @author {yuanwei}
 * @date 2020/2/19 14:50
 */
@Data
public class BeanField implements Serializable {

    private static final long serialVersionUID = 4279960350136806659L;

    private String columnName;

    private String columnType;

    private String columnComment;

    private String columnDefault;

    private String name;

    private String type;

}
