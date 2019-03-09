package net.huadong.log;

import com.alibaba.fastjson.JSONArray;
import net.huadong.entity.SysLog;
import net.huadong.entity.SysUser;
import net.huadong.service.SysLogService;
import net.huadong.util.Constant;
import net.huadong.util.HdUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author qyx
 * @date 2017-06-06
 */
@Aspect
@Component
public class ActLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(net.huadong.log.ActLog)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        sysLog.setId(null);
        ActLog syslog = method.getAnnotation(ActLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperRemark(syslog.value());
            sysLog.setOperType(syslog.type());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setClassName(className);
        sysLog.setMethodName(methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        if (args != null && args.length > 0) {
            JSONArray jsonArray = new JSONArray();
            if (args[0] != null && args[0].getClass().equals(ShiroHttpServletRequest.class)) {
                jsonArray.add(((HttpServletRequest) args[0]).getParameterMap());
            } else {
                jsonArray.add(args[0]);
            }
            params = jsonArray.toString();
        }
        //请求参数
        sysLog.setChangeTxt(params);

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        //设置IP地址
        sysLog.setIp(HdUtil.getRemortIP(request));
        SysUser employee = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constant.LOGIN_USER);
        //用户
        String userName = employee.getNickName();
        String id = employee.getUserId();
        sysLog.setUserId(id);
        sysLog.setUserName(userName);

        sysLog.setCreateTime(new Date());
        //保存系统日志
        sysLogService.saveLog(sysLog);
    }

}
