package net.huadong.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.huadong.entity.SysLog;
import net.huadong.mapper.SysLogMapper;
import net.huadong.util.PageParam;
import net.huadong.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 日志管理接口
 *
 * @author wenq
 * @date 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class SysLogService extends BaseService<SysLog> {
    Logger logger = LoggerFactory.getLogger(SysLogService.class);
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 查询日志
     *
     * @param params
     * @param pageParam
     * @return
     */
    public PageResult getLog(HashMap params, PageParam pageParam) {
        Page p = PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List resultList = sysLogMapper.getLog(params);
        PageResult result = new PageResult();
        result.setRows(resultList);
        result.setTotal(p.getTotal());
        return result;

    }

    /**
     * 保存日志
     *
     * @param sysLog
     * @author wenq
     * @date 2018-03-13
     */
    public void saveLog(SysLog sysLog) {
        try {
            sysLogMapper.insert(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

