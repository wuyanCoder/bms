package com.wuyan.bms.service;

import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.model.SysUser;
import sun.rmi.runtime.Log;

/**
 * SysUserService
 *
 * @author {yuanwei}
 * @date 2020/2/11 22:51
 */
public interface UserService {


    Results<SysUser> getAllUsersByPage(Integer offset, Integer limit);


    Results<SysUser> save(SysUser userDto, Integer roleId);

    SysUser getUserById(Long id);

    Boolean checkPhoneUnique(SysUser user);

    Results<SysUser> updateUser(SysUser sysUser, Integer roleId);

    int deleteUser(Long id);

    Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit);

    int deleteBatch(Integer[] id);

    SysUser getUser(String username);

    Results<SysUser> changePassword(Long username, String oldPassword, String newPassword);
}
