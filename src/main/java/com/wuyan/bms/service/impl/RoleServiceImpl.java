package com.wuyan.bms.service.impl;


import com.wuyan.bms.common.dto.RoleDto;
import com.wuyan.bms.common.result.ResponseCode;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.mapper.RoleDao;
import com.wuyan.bms.mapper.RolePermissionDao;
import com.wuyan.bms.mapper.RoleUserDao;
import com.wuyan.bms.model.SysRole;
import com.wuyan.bms.model.SysRoleUser;
import com.wuyan.bms.model.SysUser;
import com.wuyan.bms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * RoleServiceImpl
 *
 * @author {yuanwei}
 * @date 2020/2/12 23:59
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results<SysRole> getAllRoles() {
        List<SysRole> roles = roleDao.getAllRoles();
        return Results.success(roles.size(), roles);
    }

    @Override
    public Results<SysRole> getAllRolesByPage(Integer offset, Integer limit) {
        return Results.success(roleDao.countAllRoles().intValue(), roleDao.getAllRolesByPage(offset, limit));
    }

    @Override
    public Results save(RoleDto role) {
        roleDao.saveDto(role);
        if(!CollectionUtils.isEmpty(role.getPermissionIds())){
            List<Long> permissionIds = role.getPermissionIds();
            permissionIds.remove(0L);
            rolePermissionDao.save(role.getId(),permissionIds);
        }

        return Results.success();
    }

    @Override
    public SysRole getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Results update(RoleDto role) {
        //1、更新角色权限之前要删除该角色之前的所有权限
        rolePermissionDao.deleteRolePermission(role.getId());

        //2、判断该角色是否有赋予权限值，有就添加"
        List<Long> permissionIds = role.getPermissionIds();
        permissionIds.remove(0L);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(role.getId(), permissionIds);
        }
        int result =roleDao.update(role);
        if (result>0){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @Override
    public Results deleteRole(Integer roleId) {

        List<SysRoleUser> roleUsers = roleUserDao.getSysRoleUserByRoleId(roleId);

        if(roleUsers!=null||roleUsers.size()>0){
            return Results.failure(ResponseCode.USER_ROLE_NO_CLEAR);
        }
        rolePermissionDao.deleteRolePermission(roleId);
        if(roleDao.deleteRole(roleId)>0){
            return Results.success();
        }
        else {
            return Results.failure();
        }
    }

    @Override
    public Results<SysRole> findRoleByFuzzyRoleName(String name, Integer offset, Integer limit) {
        return Results.success(roleDao.getRoleCountByFuzzyRoleName(name).intValue(),
                roleDao.findRoleByFuzzyRoleName(name,offset,limit));
    }
}
