package net.huadong.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.huadong.entity.SysRole;
import net.huadong.entity.SysRoleResource;
import net.huadong.entity.SysUser;
import net.huadong.entity.SysUserRole;
import net.huadong.mapper.SysRoleMapper;
import net.huadong.mapper.SysRoleResourceMapper;
import net.huadong.mapper.SysUserMapper;
import net.huadong.mapper.SysUserRoleMapper;
import net.huadong.page.PageParam;
import net.huadong.page.PageResult;
import net.huadong.util.Constant;
import net.huadong.util.HdUtil;
import net.huadong.util.ResourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 角色servcie
 *
 * @author wuph
 * @date 2017-08-02
 */
@Service
@Transactional
public class SysRoleService extends BaseService<SysRole> {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Autowired
    private SysUserMapper cEmployeeMapper;
    @Autowired
    private SysUserRoleMapper sysRoleUserMapper;

    /**
     * 获取角色列表
     * wenq
     * 2017/6/5
     */
    public PageResult getRole(HashMap params, PageParam pageParam) {
        //判断数据权限
        Page p = PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<HashMap> l = sysRoleMapper.getRole(params);
        PageResult result = new PageResult();
        result.setRows(l);
        result.setTotal(p.getTotal());
        return result;
    }

    /**
     * 保存角色
     * wenq
     * 2017/6/5
     */
    public String saveRole(SysRole sysRole) {

        SysUser cEmployee = HdUtil.getLoginEmp();
        int result;
        if (sysRole.getRoleId() != null && !sysRole.getRoleId().equals("")) {
            sysRole.setUpdateTime(new Date());
            sysRole.setUpdateUser(cEmployee.getNickName());
            result = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        } else {
            sysRole.setRoleId(null
            );
            sysRole.setUpdateUser(cEmployee.getNickName());
            sysRole.setCreateUser(cEmployee.getNickName());
            sysRole.setUpdateTime(new Date());
            sysRole.setCreateTime(new Date());
            result = sysRoleMapper.insertSelective(sysRole);
        }
        if (result == 1) {
            return "success";
        } else {
            return Constant.SAVE_FAIL_INFO;
        }
    }

    /**
     * 刪除角色
     * wenq
     * 2018/3/12
     */
    @Transactional
    public String deleteRole(HashMap params) {
        String roleId = params.get("roleId").toString();
        //级联删除
        sysRoleMapper.deleteByPrimaryKey(roleId);
        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        sysRoleUserMapper.deleteByExample(example);
        Example example1 = new Example(SysRoleResource.class);
        example1.createCriteria().andEqualTo("roleId", roleId);
        sysRoleResourceMapper.deleteByExample(example1);
        return "success";
    }

    /**
     * 获取权限列表树
     *
     * @return
     * @date 2018-03-13
     */
    public List getRoleTree(String roleId) {
        if (!roleId.equals("")) {
            List<ResourceTree> allMenu = sysRoleMapper.getAllPower();
            //获取用户拥有的菜单列表
            //拼了下，查询结果是  resourceKey+下划线+datafilterType
            List<String> userMenu = sysRoleMapper.selectByRoleId(roleId);
            HashMap<String, String> userMenuMap = new HashMap<>();
            for (String s : userMenu) {
                userMenuMap.put(s, s);
            }
            //删除用户没拥有的菜单
            checkUserHasMenu(allMenu, userMenuMap);
            return allMenu;
        } else {
            return new ArrayList<ResourceTree>();
        }
        //获取完整的菜单树
    }

    /**
     * 授权用户给用户显示已有的权限
     *
     * @param allMenu
     * @param userMenu
     */
    public void checkUserHasMenu(List<ResourceTree> allMenu, HashMap<String, String> userMenu) {

        for (Iterator iter = allMenu.iterator(); iter.hasNext(); ) {
            ResourceTree menu = (ResourceTree) iter.next();
            if (userMenu.containsKey(menu.getId())) {
                //当没有子节点时，显示用户的授权信息
                if (menu.getChildren() != null && menu.getChildren().size() != 0) {
                    checkUserHasMenu(menu.getChildren(), userMenu);
                } else {
                    menu.setChecked(true);
                }
            }
        }

    }

    /**
     * 保存权限列表树
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @Transactional
    public String empower(String roleId, String[] resourceId, String[] datafileterIds) {
        SysUser cEmployee = HdUtil.getLoginEmp();
        int result = 0;
        try {
            if (!sysRoleMapper.selectByRoleId(roleId).isEmpty()) {
                Example example = new Example(SysRoleResource.class);
                example.createCriteria().andEqualTo("roleId", roleId);
                int success = sysRoleResourceMapper.deleteByExample(example);
                if (success == 0) {
                    return "授权失败";
                }
            }
            if (resourceId == null) {
                return "权限为空";
            } else {
                boolean insert = true;
                HashMap<String, String> map = new HashMap<>();
                if (datafileterIds == null) {

                } else {
                    for (String s : datafileterIds) {
                        String[] temp = s.split("_");
                        map.put(temp[0], temp[1]);
                    }
                }
                for (int i = 0; i < resourceId.length; i++) {
                    SysRoleResource sysRoleResource = new SysRoleResource();
                    sysRoleResource.setRoleId(String.valueOf(roleId));
                    sysRoleResource.setResourceId(String.valueOf(resourceId[i]));
                    result = sysRoleResourceMapper.insertSelective(sysRoleResource);
                    if (result == 1) {
                        insert = true;
                    } else {
                        insert = false;
                        break;
                    }
                }
                if (insert) {
                    return "success";
                } else {
                    return Constant.SAVE_FAIL_INFO;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.SAVE_FAIL_INFO;
        }

    }

    /**
     * 获取全部的用户
     *
     * @return
     * @author wenq
     * @date 2017-06-08
     */
    public List selectAllUser(HashMap params) {
        List list = cEmployeeMapper.selectAllUser(params);
        return list;
    }

    /**
     * 根据roleId获取用户名
     *
     * @return
     * @author wenq
     * @date 2017-06-08
     */
    public List selectUserByRoleId(HashMap params) {
        List list = cEmployeeMapper.selectUserByRoleId(params);
        return list;
    }

}
