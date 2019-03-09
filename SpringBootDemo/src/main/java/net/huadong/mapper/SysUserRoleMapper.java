package net.huadong.mapper;

import net.huadong.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {
    int deleteByRoleUserId(@Param("delkeys") List<String> delkeys, @Param("rolerkey") String rolerkey);

    List getRolesByUserId(String userId);
}