package com.wuyan.bms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wuyan.bms.common.result.ResponseCode;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.common.utils.TreeUtils;
import com.wuyan.bms.mapper.PermissionDao;
import com.wuyan.bms.mapper.RolePermissionDao;
import com.wuyan.bms.model.RolePermission;
import com.wuyan.bms.model.SysPermission;
import com.wuyan.bms.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PermissionServiceImpl
 *
 * @author {yuanwei}
 * @date 2020/2/14 21:19
 */
@Service
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
        log.info(getClass().getName()+"listAllPermission()");
        List<SysPermission> permissionList = permissionDao.listAllPermission();
        JSONArray array = new JSONArray();
        log.info(getClass().getName()+"setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0,permissionList,array);
        return Results.success(array);
    }

    @Override
    public Results<SysPermission> listAllPermissionByRoleId(Integer id) {
        List<SysPermission> datas = permissionDao.listByRoleId(id);
        return Results.success(datas.size(), datas);
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        List<SysPermission> datas = permissionDao.listAllPermission();
        return Results.success(datas.size(), datas);
    }

    @Override
    public Results save(SysPermission sysPermission) {
        return (permissionDao.save(sysPermission) > 0) ? Results.success() : Results.failure();
    }

    @Override
    public SysPermission getSysPermissionById(Integer id) {
        return permissionDao.getSysPermissionById(id);
    }

    @Override
    public Results updateSysPermission(SysPermission sysPermission) {
        return (permissionDao.update(sysPermission) > 0) ? Results.success() : Results.failure();
    }

    @Override
    public Results delete(Integer id) {
        List<RolePermission> rolePermissions =rolePermissionDao.getRolePermission(id);
        if(!rolePermissions.isEmpty()){
            return Results.failure(ResponseCode.ROLE_PERMISSION_NO_CLEAR);
        }
        permissionDao.deleteById(id);
        permissionDao.deleteByParentId(id);
        return null;
    }

    @Override
    public Results getMenu(Long userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        datas = datas.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        log.info(getClass().getName() + ".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }
}
