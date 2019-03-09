package net.huadong.service;

import net.huadong.entity.Demo;
import net.huadong.mapper.DemoMapper;
import net.huadong.mapper.SysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wj
 * @Date: 2019/2/3 11:23
 * @Version 1.0
 */
@Service
@Transactional
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;


    public void test(){
        Demo demo = new Demo();

        demo.setId("23");
        demo.setName("222");

        demoMapper.insertUser(demo);
        String a = null;
        a.split("/");
    }




    public void searchDemo(){
        System.out.println(demoMapper.searchDemo());
    }
}
