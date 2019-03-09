package net.huadong.service;

import net.huadong.entity.SysUserRole;
import net.huadong.mapper.SysUserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuph on 2018/11/19.
 */
@Service
@Transactional
public class SysUserRoleService extends BaseService<SysUserRole> {
    Logger logger = LoggerFactory.getLogger(SysUserRoleService.class);
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 获取用户拥有的角色列表
     *
     * @param userId
     * @return java.util.List<net.huadong.entity.SysUserRole>
     * @throw
     * @author wuph
     * @date 2018/11/19
     */
    public List<SysUserRole> getUserRoleByUserId(String userId) {
        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId", userId);
        return sysUserRoleMapper.selectByExample(example);
    }


    private HashMap<String, String> fromArray2Map(String[] Strings) {
        HashMap<String, String> map = new HashMap<>();
        for (String s : Strings) {
            if (!s.equals(""))
                map.put(s, "0");
        }
        return map;
    }

    /**
     * 管理角色用户
     *
     * @return
     * @author wenq
     * @date 2018-03-13
     */
    public String updateRoleUsers(String rgkey, String userkeys, String oldUserKeys) {
        try {
            String[] userkeyArray = userkeys.split(",");
            HashMap<String, String> olduserkeymap = fromArray2Map(oldUserKeys.split(","));
            List<String> newkeys = new ArrayList();
            List<String> delkeys = new ArrayList();
            for (String key : userkeyArray) {
                //如果在旧人员中存在，从旧的中将这个存在的key标记
                if (key != null && !key.equals("")) {
                    if (olduserkeymap.containsKey(key)) {
                        olduserkeymap.put(key, "1");
                    } else {
                        //如果在旧的不存在，则是新的
                        newkeys.add(key);
                    }
                }
            }
            //遍历旧map，没有打标记的，就是已经删除了
            for (Map.Entry<String, String> entry : olduserkeymap.entrySet()) {
                if (entry != null && !entry.equals("")) {
                    if (entry.getValue().equals("0"))
                        delkeys.add("'" + entry.getKey() + "'");
                }
            }
            //执行删除语句
            if (delkeys.size() > 0)
                sysUserRoleMapper.deleteByRoleUserId(delkeys, rgkey);
            //执行增加语句
            for (String userKey : newkeys) {
                SysUserRole roleUser = new SysUserRole();
                roleUser.setRoleId(rgkey);
                roleUser.setUserId(userKey);
                roleUser.setId(null);
                sysUserRoleMapper.insertSelective(roleUser);
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }



}
