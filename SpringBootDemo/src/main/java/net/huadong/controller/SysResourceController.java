package net.huadong.controller;

import net.huadong.entity.SysResource;
import net.huadong.entity.SysUser;
import net.huadong.log.ActLog;
import net.huadong.service.SysResourceService;
import net.huadong.util.HdUtil;
import net.huadong.util.ResourceTree;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: wj
 * @Date: 2019/1/26 21:54
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/SysResource")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/getUserMenu")
    @ActLog(value = "查询菜单列表",type = "查询")
    public List<ResourceTree> getUserMenu() {
        SysUser loginUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        return sysResourceService.getUserResourceTreeListByUserId(loginUser.getUserId());
    }


    /**
     * 获取所有菜单树
     *
     * @param request
     * @return
     * @author wuph
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/getAllMenu")
    @ActLog(value = "获取所有菜单树",type = "查询")
    public List getAllMenu(HttpServletRequest request) {
        return sysResourceService.getAllMenu();
    }


    /**
     * 删除菜单
     * @param resourceId
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    @ActLog(value = "删除菜单",type = "删除")
    public String delete(String resourceId) {
        return sysResourceService.deleteResourceAndChinldren(resourceId);
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
    @ResponseBody
    @RequestMapping("savePermission")
    @ActLog(value = "保存权限信息",type = "新增/修改")
    public String savePermission(SysResource resource) {
        return sysResourceService.savePermission(resource);
    }



    /**
     * 保存菜单
     *
     * @param tree
     * @return java.lang.String
     * @throw
     * @author wuph
     * @date 2017/1/9
     */
    @ResponseBody
    @RequestMapping("save")
    @ActLog(value = "保存菜单",type = "新增/修改")
    public String save(ResourceTree tree) {
        SysUser cEmployee = HdUtil.getLoginEmp();
        return sysResourceService.save(tree, cEmployee);
    }
}
