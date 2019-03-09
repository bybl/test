package net.huadong.service;

import net.huadong.entity.SysRoleResource;
import net.huadong.mapper.SysRoleResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
@Service
@Transactional
public class SysRoleResourceService extends BaseService<SysRoleResource> {
    Logger logger = LoggerFactory.getLogger(SysRoleResourceService.class);

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    /**
     * 获取用户拥有的权限
     *
     * @param userId
     * @return List<String> 权限集合
     * @throw
     * @author wuph
     * @date 2018/11/19
     */
    public List<String> getUserPermissionByUserId(String userId) {
        return sysRoleResourceMapper.getUserPermissionByUserId(userId);
    }
}
