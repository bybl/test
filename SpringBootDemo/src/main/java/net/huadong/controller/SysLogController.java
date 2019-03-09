package net.huadong.controller;

import net.huadong.log.ActLog;
import net.huadong.service.SysLogService;
import net.huadong.util.HdUtil;
import net.huadong.util.PageParam;
import net.huadong.util.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author: wj
 * @Date: 2019/2/3 15:22
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/SysLog")
public class SysLogController {
    @Resource
    private SysLogService sysLogService;
    /**
     * 获取日志列表
     *
     * @param pageParam 分页参数
     * @author wenq
     * @date 2018-03-13
     */
    @ResponseBody
    @RequestMapping("getLog")
    @ActLog(value = "查询日志", type = "查询")
    public PageResult getLog(HttpServletRequest request, PageParam pageParam) throws Exception {
        HashMap params = HdUtil.getRequestParams(request);
        return sysLogService.getLog(params, pageParam);
    }
}
