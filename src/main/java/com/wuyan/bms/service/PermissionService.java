package com.wuyan.bms.service;

import com.alibaba.fastjson.JSONArray;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.model.SysPermission;

import java.util.List;

/**
 * PermissionService
 *
 * @author {yuanwei}
 * @date 2020/2/14 21:18
 */
public interface PermissionService {
    Results<JSONArray> listAllPermission();

    Results<SysPermission> listAllPermissionByRoleId(Integer id);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission permission);

    SysPermission getSysPermissionById(Integer id);

    Results updateSysPermission(SysPermission permission);

    Results delete(Integer id);

    Results<SysPermission> getMenu(Long userId);
}
