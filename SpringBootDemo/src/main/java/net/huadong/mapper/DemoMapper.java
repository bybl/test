package net.huadong.mapper;

import net.huadong.entity.Demo;
import net.huadong.entity.SysUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DemoMapper extends Mapper<Demo> {

    public void insertUser (Demo demo);

    public List<Demo> searchDemo();
}