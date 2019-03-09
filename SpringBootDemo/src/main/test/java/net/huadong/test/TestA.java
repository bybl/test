package net.huadong.test;

import net.huadong.SpringbootdemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author wuph
 * @create 2018-11-13 9:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={SpringbootdemoApplication.class})
@WebAppConfiguration
public class TestA {
    @Autowired
    private SysUserService sysUserService;
    @Test
    public void test1(){

    }

}