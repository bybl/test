package net.huadong.mapper;

import net.huadong.entity.SysResource;
import net.huadong.util.ResourceTree;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {

    /**
     * 根据用户ID获取用户拥有的权限
     *
     * @param userId
     * @return List<String>
     * @throw
     * @author wuph
     * @date 2017/8/2
     */
    List<String> getUserPermissionByUserId(String userId);

    /**
     * 根据用户ID获取用户资源权限
     *
     * @param userId       用户ID
     * @param resourceType 资源类型(MENU or PERMISSION)
     * @return List<ResourceTree> tree格式的资源list
     * @throw
     * @author wuph
     * @date 2017/8/2
     */
    List<ResourceTree> getUserResourceByUserId(@Param("userId") String userId, @Param("resourceType") String resourceType);

    /**
     * 删除菜单，同时删除子菜单和权限
     *
     * @param resourceId
     * @return int
     * @throw
     * @author wuph
     * @date 2017/1/4
     */
    int deletePermissionByResourceId(@Param("resourceId") String resourceId);
    /**
     * 获取所有菜单树
     *
     * @param:
     * @return:
     * @author: wuph
     * @date: 2016-12-28
     */
    List<ResourceTree> getAllMenu();
    /**
     * 获取菜单绑定的权限
     *
     * @param resourceId 菜单ID
     * @return List<HashMap> 权限列表
     * @throw
     * @author wuph
     * @date 2017/1/5
     */
    List<HashMap> getPermissionByResourceId(@Param("resourceId") String resourceId);
    /**
     * 判断权限是否重复，更新状态时要排除掉自身
     *
     * @param permissionCode 权限代码
     * @param resourceId 资源ID
     * @return int 总数
     * @throw
     * @author wuph
     * @date 2017/1/9
     */
    int getPermissionCount(@Param("permissionCode") String permissionCode, @Param("resourceId") String resourceId);

}