package com.wuyan.bms.service;

import com.wuyan.bms.common.dto.RoleDto;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.model.SysRole;
import com.wuyan.bms.model.SysUser;

/**
 * RoleService
 *
 * @author {yuanwei}
 * @date 2020/2/12 23:59
 */
public interface RoleService {
    Results<SysRole> getAllRoles();

    Results<SysRole> getAllRolesByPage(Integer offset, Integer limit);

    Results save(RoleDto role);

    SysRole getRoleById(Integer id);

    Results update(RoleDto role);

    Results deleteRole(Integer roleId);

    Results<SysRole> findRoleByFuzzyRoleName(String name, Integer offset, Integer limit);
}
