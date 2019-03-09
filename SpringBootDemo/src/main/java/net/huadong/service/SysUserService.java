package net.huadong.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.huadong.entity.SysUser;
import net.huadong.mapper.SysUserMapper;
import net.huadong.util.Constant;
import net.huadong.util.HdUtil;
import net.huadong.util.PageParam;
import net.huadong.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import tk.mybatis.mapper.entity.Example;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

@Service
@Transactional
public class SysUserService extends BaseService<SysUser> {

    @Autowired
    private SysUserMapper sysUserMapper;
    Logger logger = LoggerFactory.getLogger(SysUserService.class);

    public SysUser findByUserName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("userName", userName);
        return sysUserMapper.selectOneByExample(example);
    }
    public void login(){

    }


    /**
     * 主页修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     * @author wj
     * @date 2018-05-09
     */
    public String changePassword(String oldPwd, String newPwd) {
        //处理session
     //   System.out.println(oldPwd);
        try {
            SysUser loginUser = HdUtil.getLoginEmp();
            String Md5OldPwd = HdUtil.md5(oldPwd);
            String Md5NewPwd = HdUtil.md5(newPwd);
            if (Md5OldPwd.equals(loginUser.getPassword())) {
                Example example = new Example(SysUser.class);
//                example.createCriteria().andEqualTo("id", loginUser.getId());
                SysUser user = new SysUser();
                user.setPassword(Md5NewPwd);
                int result = sysUserMapper.updateByExampleSelective(user, example);
                if (result == 1) {
                    loginUser.setPassword(Md5NewPwd);
                    HdUtil.setLoginEmployee(loginUser);
                    return "success";
                } else {
                    return Constant.UPDATE_FAIL_INFO;
                }
            } else {
                return "旧密码输入不正确";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.UPDATE_FAIL_INFO;
        }
    }



    public PageResult getSysUserRecord(HashMap params, PageParam pageParam) {
        Page p = PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List userList = sysUserMapper.selectSysUserRecord(params);
        PageResult result = new PageResult();
        result.setRows(userList);
        result.setTotal(p.getTotal());
        return result;
    }


    /**
     * @param sysUser
     *
     * 保存用户表单
     * @return
     */

    public String saveSysUserRecord(SysUser sysUser){
        SysUser loginUser = HdUtil.getLoginEmp();

//        sysUsers.forEach(each->{
//            if(each.getUserName().equals(sysUser.getUserName())){
//                return "该账户已存在";
//            }else{
//
//            }
//        });





        int result;
        if (sysUser.getUserId() != null && !sysUser.getUserId().equals("")) {
            sysUser.setUpdateTime(new Date());
            sysUser.setUpdateUser(loginUser.getNickName());

            result = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        } else {
            Example example = new Example(SysUser.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userName", sysUser.getUserName());
            List<SysUser> list = sysUserMapper.selectByExample(example);
            if(list.size()>0){
                return "该账户已存在";
            }
            sysUser.setPassword(HdUtil.md5(sysUser.getPasswordInput()));
            sysUser.setUserId(null);
            sysUser.setIsEnable("0");
            sysUser.setUpdateUser(loginUser.getNickName());
            sysUser.setCreateUser(loginUser.getNickName());
            sysUser.setUpdateTime(new Date());
            sysUser.setCreateTime(new Date());
            result = sysUserMapper.insertSelective(sysUser);

        }
        if (result == 1) {
            return "success";
        } else {
            return Constant.SAVE_FAIL_INFO;
        }
    }



    public void saveLoginTim(SysUser sysUser){
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }



    public void test(){
        SysUser sysUser = new SysUser();
        sysUser.setPassword("123");
        sysUser.setUserId("123");
        sysUser.setUserName("2s");
        sysUserMapper.insertUser(sysUser);
        String a = null;
        a.split(".");
    }
}
