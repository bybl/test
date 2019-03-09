package net.huadong.mapper;

import net.huadong.entity.SysRoleResource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleResourceMapper extends Mapper<SysRoleResource> {
    /**
     * 获取用户拥有的权限
     *
     * @param userId
     * @return List<String> 权限集合
     * @throw
     * @author wuph
     * @date 2018/11/19
     */
    List<String> getUserPermissionByUserId(String userId);
}