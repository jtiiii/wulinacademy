package com.funeral.wulincore.dao.departMent;

import com.funeral.wulinapi.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject: wulin
 * @BelongsPackage: com.funeral.wulincore.dao
 * @Author: 15568  注解的方式使用Mybatis
 * @CreateTime: 2019-01-09 22:13
 * @Description: ${Description}
 */
public interface DepartmentMapper {
    @Select("select * from department where id=#{id}")
    Department getDepartMentById(Integer id);
}
