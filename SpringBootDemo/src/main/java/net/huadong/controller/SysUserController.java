package net.huadong.controller;

import net.huadong.entity.SysUser;
import net.huadong.log.ActLog;
import net.huadong.service.SysUserService;
import net.huadong.util.HdUtil;
import net.huadong.util.PageParam;
import net.huadong.util.PageResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/SysUser")
public class SysUserController {
    @Autowired
    private SysUserService systemUserService;
    Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @ResponseBody
    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request){
        request.getSession().setAttribute("test","aaaaaa");
        return "ok";
    }
    @RequestMapping(value = "/test1")
    public String test1(HttpServletRequest request){
        System.out.println(request.getSession().getAttribute("test"));
        return "1";
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(String username, String password, String rememberMe, Model model, HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        //获取登录失败次数
        Object loginFailNum = context.getAttribute(username);
        //登录失败时间
        long loginFailTim = (long) (context.getAttribute(username + "Tim") == null ? 0l : context.getAttribute(username + "Tim"));
        long now = new Date().getTime();
        if (loginFailNum != null && (Integer) loginFailNum >= 5 && now - loginFailTim < 600000) {
            return ("登录失败次数过多，10分钟后再试");
        } else {
            UsernamePasswordToken token = new UsernamePasswordToken(username, HdUtil.md5(password));
            if ("true".equals(rememberMe)) {
                token.setRememberMe(true);
            }
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                logger.info("用户名：" + username + "登录成功");
                context.removeAttribute(username);
                context.removeAttribute(username + "Tim");
                return "ok";
            } catch (UnknownAccountException accountException) {
                logger.error("用户名：" + username + "登录失败");
                return ("用户名不存在");
            } catch (IncorrectCredentialsException credentialsException) {
                logger.error("用户名：" + username + "登录失败");
                if (null == loginFailNum) {
                    //第一次登录失败
                    loginFailNum = 1;
                } else if (now - loginFailTim < 600000) {
                    //上次登录失败记录是10分钟以内，失败次数+1，否则重置为1
                    loginFailNum = (Integer) loginFailNum + 1;
                } else {
                    //已有失败记录，但超过10分钟，次数重置为1
                    loginFailNum = 1;
                }
                context.setAttribute(username, loginFailNum);
                context.setAttribute(username + "Tim", new Date().getTime());
                return ("用户名密码错误，当前错误" + loginFailNum + "次,密码错误超过5次将锁定账号10分钟");
            } catch (AuthenticationException ex) {
                logger.error("用户名：" + username + "登录失败");
                return ("用户名密码错误");
            }
        }
    }


    /**
     * 获取用户列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSysUserRecord")
    @ActLog(value = "查询用户列表",type = "查询")
    public PageResult getSysUserRecord(String nickName,HttpServletRequest request, PageParam pageParam){
        HashMap params = HdUtil.getRequestParams(request);
     return    systemUserService.getSysUserRecord(params, pageParam);

    }


    /**
     * 保存用户表单
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/saveSysUserRecord")
    @ActLog(value = "保存用户添加纪录", type = "新增/修改")
    public String saveSysUserRecord(SysUser sysUser) {
        return systemUserService.saveSysUserRecord(sysUser);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/updatePassword")
    @ActLog(value = "密码修改", type = "密码修改")
    public String updatePassword(SysUser sysUser) {

      //  System.out.println(sysUser);
        sysUser.setPassword(HdUtil.md5(sysUser.getPasswordInput()));
       return systemUserService.saveSysUserRecord(sysUser);

    }

    /**
     * 删除
     *
     * @param userId
     * @return java.lang.String
     * @throw
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/deleteUser")
    @ActLog(value = "删除用户",type = "删除用户")
    public String delete(String userId) {
        int i = systemUserService.deleteByPrimaryKey(userId);
        if (i > 0) {
            return "success";
        } else {
            return "删除失败";
        }
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/changepassword")
    @ActLog(value = "密码修改", type = "密码修改")
    public String save(SysUser sysUser){

        SysUser sysUser1 =HdUtil.getLoginEmp();
        sysUser.setUserId(sysUser1.getUserId());
        sysUser.setUserName(sysUser1.getUserName());
        sysUser.setPassword(HdUtil.md5(sysUser.getPasswordInput()));
        return systemUserService.saveSysUserRecord(sysUser);
    }
}
