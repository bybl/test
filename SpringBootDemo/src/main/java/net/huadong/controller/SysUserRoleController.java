package net.huadong.controller;

import net.huadong.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wj
 * @Date: 2019/1/31 15:51
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/SysUserRole")
public class SysUserRoleController {
    @Resource
    private SysUserRoleService tsRoleUserService;
    Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);

    /**
     * 管理角色用户
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping(value = "/updateRoleUsers")
    public String updateRoleUsers(HttpServletRequest request) {
        String rolekey = request.getParameter("roleId");
        String userKeys = request.getParameter("userId");
        String oldUserKeys=request.getParameter("oldUserId");
        return tsRoleUserService.updateRoleUsers(rolekey, userKeys,oldUserKeys);
    }

    /**
     * 返回权限控制页面
     * @return
     */
    @RequestMapping(value = "/sysRolePower")
    public String sysRolePower(@RequestParam("roleId") String roleId , Model model){
        model.addAttribute("roleId",roleId);
        return "admin/sys_role_power";

    }


}
