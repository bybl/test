package net.huadong.controller;


import net.huadong.config.ShiroSessionListener;
import net.huadong.entity.SysUser;
import net.huadong.service.SysUserService;
import net.huadong.shiro.ShiroRealm;
import net.huadong.util.HdUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Deque;

/**
 * 登录控制器
 *
 * @author wuph
 * @create 2018-11-19 10:11
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
   private ShiroSessionListener shiroSessionListener;
    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String userName, RedirectAttributes redirectAttributes, String password, Boolean rememberMe, ModelMap model, HttpServletRequest request) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            redirectAttributes.addFlashAttribute("errorInfo", "用户名或密码为空");
            return "redirect:/login.jsp";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, HdUtil.md5(password));

        if (rememberMe!=null&&"true".equals(rememberMe)) {
            token.setRememberMe(true);
        }


        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);

        } catch (UnknownAccountException accountException) {
            logger.error("用户名：" + userName + "登录失败");
            model.addAttribute("errorInfo", "用户名：" + userName + "登录失败");

        } catch (IncorrectCredentialsException credentialsException) {
            logger.error("用户名：" + userName + "登录失败");
            model.addAttribute("errorInfo", "用户名密码错误");

        } catch (AuthenticationException ex) {
            logger.error("用户名：" + userName + "登录失败");
            model.addAttribute("errorInfo", "用户名密码错误");

        }
        //验证是否登录成功
        if (subject.isAuthenticated()) {
            logger.info(userName + "登录成功");
            //把当前用户放入session
            Session session = subject.getSession();
            SysUser employee = sysUserService.findByUserName(userName);
            employee.setLastLoginTime(new Date());
            sysUserService.saveLoginTim(employee);
            session.setAttribute("loginUser", employee);
            return "redirect:admin/main";
        } else {
            token.clear();
            return "login";
        }
    }

    @GetMapping("/admin/main")
    public String main(HttpServletRequest request, Model model) {

        model.addAttribute("count",shiroSessionListener.getSessionCount());
        return "admin/main";
    }



    @GetMapping("/admin/welcome")
    public String welcome(HttpServletRequest request, Model model) {

        model.addAttribute("count",shiroSessionListener.getSessionCount());
        return "admin/welcome";
    }


    @Autowired
    private ShiroRealm shiroRealm;

    @RequestMapping("/logout")
    public String logout( Model model) {//处理session
        Subject subject = SecurityUtils.getSubject();
        Serializable sessionId =subject.getSession().getId();
        Cache<String, Deque<Serializable>> cache =  shiroRealm.getCacheManager().getCache("shiro-kickout-session");
        SysUser user = (SysUser) subject.getPrincipal();
        Cache<Object, AuthenticationInfo> authenticationInfoCache = shiroRealm.getAuthenticationCache();
        if(authenticationInfoCache!=null){

          System.out.println(authenticationInfoCache.keys());
            authenticationInfoCache.remove(user.getUserName());
        }
        System.out.println(authenticationInfoCache.keys());

        //System.out.println(authenticationInfoCache.getClass());
        String username = user.getUserName();
        Deque<Serializable> deque = cache.get(username);
        deque.remove(sessionId);
        subject.logout();
        return "login";
    }

    @RequestMapping("/test")
    public String test(  String oldPwd,String newPwd,Model model) {

        System.out.println(oldPwd);
        System.out.println(newPwd);
        return sysUserService.changePassword(oldPwd, newPwd);
    }

}
