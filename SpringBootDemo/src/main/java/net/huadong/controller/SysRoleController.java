package net.huadong.controller;

import net.huadong.entity.SysRole;
import net.huadong.log.ActLog;
import net.huadong.page.PageParam;
import net.huadong.page.PageResult;
import net.huadong.service.SysRoleService;
import net.huadong.util.HdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: wj
 * @Date: 2019/1/31 10:50
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/SysRole")
public class SysRoleController {

    @Resource
    private SysRoleService tsRoleService;
    Logger logger = LoggerFactory.getLogger(SysRoleController.class);



    /**
     * 获取角色列表
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/getRole")
    @ActLog(value = "查询角色", type = "查询")
    public PageResult getRole(HttpServletRequest request, PageParam pageParam) {
        HashMap params = HdUtil.getRequestParams(request);
        return tsRoleService.getRole(params, pageParam);
    }

    /**
     * 保存角色
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/saveRole")
    @ActLog(value = "保存角色", type = "新增/修改")
    public String saveRole(SysRole sysRole) {
        return tsRoleService.saveRole(sysRole);
    }

    /**
     * 刪除角色
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    @ActLog(value = "删除角色", type = "删除")
    public String deleteRole(HttpServletRequest request) {
        HashMap params = HdUtil.getRequestParams(request);
        return tsRoleService.deleteRole(params);
    }

    /**
     * 获取权限列表树
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllPower")
    @ActLog(value = "获取权限列表树", type = "查询")
    public List getRoleTree(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");

        System.out.println(tsRoleService.getRoleTree(roleId).get(0));
        return tsRoleService.getRoleTree(roleId);
    }

    /**
     * 保存权限树列表
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/empower")
    @ActLog(value = "保存权限树列表", type = "新增/修改")
    public String empower(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        String resourceIds = request.getParameter("resourceIds");
        //存放的样子是 : [resouceId][_][datafiletertype][,]
        String datafileterIdstr = request.getParameter("datafileterIdstr");

        // HashMap params =HdUtil.getRequestParams(request);
        String[] resouceId = null;
        if (StringUtils.isNotEmpty(resourceIds)) {
            resouceId = resourceIds.split(",");
        }
        String[] datafileterIds = null;
        if (StringUtils.isNotEmpty(datafileterIdstr)) {
            datafileterIds = datafileterIdstr.split(",");
        }
        return tsRoleService.empower(roleId, resouceId, datafileterIds);
    }

    /**
     * 获取全部的用户
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllUser")
    @ActLog(value = "获取全部的用户", type = "查询")
    public List selectAllUser(HttpServletRequest request) {
        HashMap params = HdUtil.getRequestParams(request);
        return tsRoleService.selectAllUser(params);
    }

    /**
     * 根据roleId获取用户
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserByRoleId")
    @ActLog(value = "根据roleId获取用户", type = "查询")
    public List selectUserByRoleId(HttpServletRequest request) {
        HashMap params = HdUtil.getRequestParams(request);
        return tsRoleService.selectUserByRoleId(params);
    }

}
