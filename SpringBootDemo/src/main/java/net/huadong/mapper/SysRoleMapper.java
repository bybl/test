package net.huadong.mapper;

import net.huadong.entity.SysRole;
import net.huadong.util.ResourceTree;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {
    List<HashMap> getRole(HashMap<String, Object> params);

    /**
     * 查询全部权限信息
     * @return
     */
    List<ResourceTree> getAllPower();

    /**
     * 根据用户id查询授权信息
     * @param roleId
     * @return
     */
    List selectByRoleId(String roleId);

    /**
     * 删除用户权限，以便重新赋予权限
     *
     * @param roleId
     * @return
     * @author wenq
     * @date 2017-06-07
     */
    Integer deleteByRoleId(Integer roleId);

    /**
     * 获得船公司箱属
     *
     * @param shipCorpCod
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    List<String> getShipCorpCntrList(String shipCorpCod);

    /**
     * 获得船公司授权代理
     *
     * @param shipCorpCod
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    List<String> getShipCorpAgentList(String shipCorpCod);
}