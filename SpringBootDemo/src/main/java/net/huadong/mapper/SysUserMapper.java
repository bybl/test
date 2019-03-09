package net.huadong.mapper;

import net.huadong.entity.SysUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 获取全部的用户
     * wenq
     * 2017/6/12
     *
     * @return
     */
    List selectAllUser(HashMap params);



    /**
     * 根据RoleId获取用户
     * wenq
     *
     * @param params
     * @return
     */
    List selectUserByRoleId(HashMap params);




    List selectSysUserRecord(HashMap params);


    public void insertUser (SysUser user);
}