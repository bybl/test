package net.huadong.service;

import net.huadong.entity.SysResource;
import net.huadong.entity.SysUser;
import net.huadong.mapper.SysResourceMapper;
import net.huadong.util.HdUtil;
import net.huadong.util.ResourceTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wj
 * @Date: 2019/1/26 21:54
 * @Version 1.0
 */
@Service
@Transactional
public class SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    public List<ResourceTree> getUserResourceTreeListByUserId(String userId) {
        //返回结果
        List<ResourceTree> result = new ArrayList<ResourceTree>();
        List<ResourceTree> userResourceTreeList = sysResourceMapper.getUserResourceByUserId(userId, "MENU");
        // 先找到所有的一级菜单
        for (int i = 0; i < userResourceTreeList.size(); i++) {
            // 一级菜单没有parentId  空
            if (StringUtils.isEmpty(userResourceTreeList.get(i).getParentId())) {
                result.add(userResourceTreeList.get(i));
            }
        }
        // 为一级菜单设置子菜单，getResourceChildren递归调用
        for (ResourceTree menu : result) {
            menu.setChildren(getResourceChildren(menu.getId(), userResourceTreeList));
        }
        return result;
    }

    public static List<ResourceTree> getResourceChildren(String id, List<ResourceTree> rootMenu) {
        //存储孩子节点
        List<ResourceTree> childList = new ArrayList<ResourceTree>();
        for (ResourceTree resourceTree : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (null != resourceTree.getParentId()) {
                if (resourceTree.getParentId().equals(id)) {
                    childList.add(resourceTree);
                }
            }
        }
        //循环子菜单
        for (ResourceTree resourceTree : childList) {
            //判断非叶子节点
            if (!StringUtils.isEmpty(resourceTree.getUrl())) {
                // 递归
                resourceTree.setChildren(getResourceChildren(resourceTree.getId(), rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }



    /**
     * 获取完整的菜单树
     *
     * @param
     * @return
     * @author wuph
     * @date 2016-12-28
     */
    public List<ResourceTree> getAllMenu() {
        //获取完整的菜单树
        return sysResourceMapper.getAllMenu();
    }



    /**
     * 删除菜单，同时删除子菜单和权限
     *
     * @param resourceId 菜单ID
     * @return java.lang.String
     * @throw
     * @author wuph
     * @date 2017/1/4
     */
    public String deleteResourceAndChinldren(String resourceId) {
        sysResourceMapper.deletePermissionByResourceId(resourceId);
        int i = sysResourceMapper.deleteByPrimaryKey(resourceId);
        if (i != 0) {
            return "success";
        } else {
            return "删除失败";
        }
    }





    /**
     * 保存权限
     *
     * @param resource
     * @return java.lang.String
     * @throw
     * @author wuph
     * @date 2017/1/9
     */
    public String savePermission(SysResource resource) {
        SysUser cEmployee = HdUtil.getLoginEmp();
        System.out.println(cEmployee);
        //当前时间，用于更新create_time,update_time字段
        Date now = new Date();
        int i;
        //id为空说明是新增，否则为更新
        if (StringUtils.isEmpty(resource.getResourceId())) {
            //判断权限代码是否重复
            if (sysResourceMapper.getPermissionCount(resource.getPermissionCode(), null) == 0) {
                resource.setResourceId(null);
                resource.setType("PERMISSION");
                resource.setCreateTime(now);
                resource.setCreateUser(cEmployee.getNickName());
                resource.setUpdateTime(now);
                resource.setUpdateUser(cEmployee.getNickName());
                i = sysResourceMapper.insertSelective(resource);
            } else {
                return "权限代码已存在";
            }
        } else {
            //判断权限代码是否重复
            if (sysResourceMapper.getPermissionCount(resource.getPermissionCode(), resource.getResourceId()) == 0) {
                SysResource oldResource = sysResourceMapper.selectByPrimaryKey(resource.getResourceId());
                oldResource.setUpdateTime(now);
                oldResource.setUpdateUser(cEmployee.getNickName());
                oldResource.setPermissionName(resource.getPermissionName());
                oldResource.setPermissionCode(resource.getPermissionCode());
                i = sysResourceMapper.updateByPrimaryKeySelective(oldResource);
            } else {
                return "权限代码已存在";
            }
        }
        if (i == 1) {
            return "success";
        } else {
            return "保存失败";
        }
    }




    /**
     * 保存菜单
     *
     * @param tree
     * @return java.lang.String
     * @author wuph
     * @date 2016-12-29
     */
    public String save(ResourceTree tree, SysUser cEmployee) {
        Date now = new Date();
        SysResource resource = null;
        if (StringUtils.isNotEmpty(tree.getId())) {
            resource = sysResourceMapper.selectByPrimaryKey(tree.getId());
        } else {
            resource = new SysResource();
            resource.setCreateTime(now);
            resource.setCreateUser(cEmployee.getNickName());
        }
        if (StringUtils.isEmpty(tree.getParentId())) {
            tree.setParentId(null);
        }
        resource.setUpdateTime(now);
        resource.setUpdateUser(cEmployee.getNickName());
        resource.setMenuName(tree.getText());
        resource.setMenuUrl(tree.getUrl());
        resource.setType("MENU");
        resource.setSeqNum(tree.getSeqNum());
        resource.setIcon(tree.getIcon());
        if ("on".equals(tree.getDisabled())) {
            resource.setDisabled(1);
        } else {
            resource.setDisabled(0);
        }
       resource.setRemark(tree.getRemark());
        resource.setParentId(tree.getParentId());
        int result;
        if (StringUtils.isNotEmpty(tree.getId())) {
            result = sysResourceMapper.updateByPrimaryKeySelective(resource);
        } else {
            result = sysResourceMapper.insertSelective(resource);
        }
        if (result == 1) {
            return "success";
        } else {
            return "保存失败";
        }
    }
}
