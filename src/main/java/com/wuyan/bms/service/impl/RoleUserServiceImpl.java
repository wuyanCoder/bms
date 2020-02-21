package com.wuyan.bms.service.impl;

import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.mapper.RoleUserDao;
import com.wuyan.bms.model.SysRoleUser;
import com.wuyan.bms.service.RoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * RoleUserServiceImpl
 *
 * @author {yuanwei}
 * @date 2020/2/13 17:00
 */
@Service
@Transactional
public class RoleUserServiceImpl implements RoleUserService {

    @Resource
    private RoleUserDao roleUserDao;

    @Override
    public Results getSysRoleUserByUserId(Integer userId) {
        SysRoleUser sysRoleUser = roleUserDao.getSysRoleUserByUserId(userId);
        if(sysRoleUser != null){
            return Results.success(sysRoleUser);
        }else{
            return Results.success();
        }
    }
}
