package com.wuyan.bms.mapper;

import com.sun.jmx.snmp.SnmpInt;
import com.wuyan.bms.common.dto.RoleDto;
import com.wuyan.bms.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * RoleDao
 *
 * @author {yuanwei}
 * @date 2020/2/13 0:04
 */
@Mapper
public interface RoleDao {
    @Select("SELECT * from sys_role r")
    List<SysRole> getAllRoles();

    @Select("select count(*) from sys_role r ")
    Long countAllRoles();

    @Select("select * from sys_role r order  by r.id limit #{offset} , #{limit} ")
    List<SysRole> getAllRolesByPage(Integer offset, Integer limit);

    @Insert("insert into sys_role(name, description, createTime, updateTime) values(#{name}, #{description}, now(), now())")
    int save(SysRole role);

    void saveDto(RoleDto role);

    @Select("SELECT * from sys_role r where r.id=#{id}")
    SysRole getRoleById(Integer id);

    int update(RoleDto role);

    @Delete("delete from sys_role where id=#{roleId}")
    int deleteRole(Integer roleId);


    @Select("select count(*) from sys_role where name like '%${name}%'")
    Long getRoleCountByFuzzyRoleName(@Param("name") String name);

    @Select("select * from sys_role  where name like '%${name}%' limit #{offset},#{limit}")
    List<SysRole> findRoleByFuzzyRoleName(@Param("name") String name, Integer offset, Integer limit);
}
