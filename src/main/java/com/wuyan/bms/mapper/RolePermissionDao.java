package com.wuyan.bms.mapper;

import com.wuyan.bms.model.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * RolePermissionDao
 *
 * @author {yuanwei}
 * @date 2020/2/15 12:49
 */
@Mapper
public interface RolePermissionDao {
    int save(@Param("roleId")Integer id, @Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteRolePermission(Integer roleId);

    @Select("select * from sys_role_permission where permissionId = #{id} ")
    List<RolePermission> getRolePermission(Integer id);
}
