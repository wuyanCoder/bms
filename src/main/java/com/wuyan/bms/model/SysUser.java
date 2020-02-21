package com.wuyan.bms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *  SysUser
 *  @author {yuanwei}
 *  @date 2020/2/11 22:51
 */
@Data
public class SysUser extends BaseEntity<Long>{
    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private String telephone;
    private String email;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer sex;
    private Integer status;
    private String intro;

    public interface Status {
        int DISABLED = 0;//作废
        int VALID = 1;
        int LOCKED = 2;//锁定
    }
}