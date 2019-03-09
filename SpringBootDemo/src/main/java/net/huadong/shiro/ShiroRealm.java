package net.huadong.shiro;

import net.huadong.entity.SysUser;
import net.huadong.entity.SysUserRole;
import net.huadong.service.SysRoleResourceService;
import net.huadong.service.SysUserRoleService;
import net.huadong.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuph
 * @create 2018-11-14 9:54
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @throw
     * @author wuph
     * @date 2018/11/19
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从session中拿出用户对象
        SysUser user = (SysUser) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        // 获取用户角色
        Set<String> roleNameSet = new HashSet<>();

        List<SysUserRole> roleList = sysUserRoleService.getUserRoleByUserId(user.getUserId());
        if (!CollectionUtils.isEmpty(roleList)) {
            for (SysUserRole role : roleList) {
                roleNameSet.add(role.getRoleName());

            }
        }
        //获取用户角色
        List<String> permissionList = sysRoleResourceService.getUserPermissionByUserId(user.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        info.addStringPermission("user:edit");
        info.addStringPermissions(permissionList);
        info.setRoles(roleNameSet);
        return info;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @throw
     * @author wuph
     * @date 2018/11/19
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws
            AuthenticationException {
        this.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 获取登录的用户名(前台表单)
        String userName = usernamePasswordToken.getUsername();

        // 从数据库中查询用户（数据库）
        SysUser user = sysUserService.findByUserName(userName);
        if (null != user) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;
        } else {
            // 认证没有通过
            throw new UnknownAccountException();// 没帐号
        }
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
//        super.clearCachedAuthenticationInfo(principals);
        Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();

    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
