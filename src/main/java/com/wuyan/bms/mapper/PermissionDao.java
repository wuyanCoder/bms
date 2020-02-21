package com.wuyan.bms.mapper;

import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.model.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * PermissionDao
 *
 * @author {yuanwei}
 * @date 2020/2/14 21:19
 */
@Mapper
public interface PermissionDao {
    @Select("select * from sys_permission p ")
    List<SysPermission> listAllPermission();

    @Select("SELECT p.* from sys_permission p INNER JOIN sys_role_permission rp on p.id=rp.permissionId where rp.roleId=#{id} ORDER BY p.sort")
    List<SysPermission> listByRoleId(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_permission(parentId, name, css, href, type, permission, sort) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})")
    int save(SysPermission sysPermission);

    @Select("select * from sys_permission t where t.id = #{id}")
    SysPermission getSysPermissionById(Integer id);

    int update(SysPermission sysPermission);

    @Delete("delete from sys_permission where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from sys_permission where parentId = #{id}")
    int deleteByParentId(Integer id);

    @Select("SELECT DISTINCT sp.*  " +
            "FROM sys_role_user sru " +
            "INNER JOIN sys_role_permission srp ON srp.roleId = sru.roleId " +
            "LEFT JOIN sys_permission sp ON srp.permissionId = sp.id " +
            "WHERE " +
            "sru.userId = #{userId}")
    List<SysPermission> listByUserId(Long userId);
}
