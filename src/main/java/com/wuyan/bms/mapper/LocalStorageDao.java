package com.wuyan.bms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wuyan.bms.model.LocalStorage;

@Mapper
public interface LocalStorageDao {

    @Select("select * from local_storage t where t.id = #{id}")
    LocalStorage getById(Integer id);

    @Delete("delete from local_storage where id = #{id}")
    int delete(LocalStorage localStorage);

    int update(LocalStorage localStorage);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into local_storage(real_name, name, suffix, path, type, size, create_by, create_time, update_time, update_by, delete_by) values(#{realName}, #{name}, #{suffix}, #{path}, #{type}, #{size}, #{createBy}, #{createTime}, #{updateTime}, #{updateBy}, #{deleteBy})")
    int save(LocalStorage localStorage);

    int count(@Param("params") Map<String, Object> params);

    List<LocalStorage> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
