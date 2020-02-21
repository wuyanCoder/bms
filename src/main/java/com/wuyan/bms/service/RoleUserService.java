package com.wuyan.bms.service;

import com.wuyan.bms.common.result.Results;

/**
 * RoleUserService
 *
 * @author {yuanwei}
 * @date 2020/2/13 16:59
 */
public interface RoleUserService {
    Results getSysRoleUserByUserId(Integer userId);
}
