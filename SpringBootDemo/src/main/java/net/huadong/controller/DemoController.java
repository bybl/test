package net.huadong.controller;

import net.huadong.entity.Demo;
import net.huadong.mapper.DemoMapper;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: wj
 * @Date: 2019/2/6 17:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    Log log = LogFactory.getLog(getClass());

    @Autowired
    private DemoMapper demoMapper;

    @RequestMapping("/searchDemo")
    public void searchDemo(){
        log.info(demoMapper.searchDemo());
    }

    @RequestMapping("/insertDemo")
    public void insertDemo(){


        Demo demo = new Demo();
        demo.setId("222");
        demo.setName("cache");
        demoMapper.insertUser(demo);

        String a = null;
        a.split("/");
    }
}
