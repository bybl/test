package net.huadong.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import net.huadong.bean.CarBean;
import net.huadong.service.DemoService;
import net.huadong.service.SysUserService;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.huadong.util.BeanUtil.sendPost;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdemoApplicationTests {



    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DemoService demoService;

    @Test
    public void contextLoads() {
    }





    @Test
    public void contenxTest(){

        Map<String,Object> map=new HashMap<>();
        Map<String,Object> dataMap2=new HashMap<>();
        Map<String,Object> dataMap3=new HashMap<>();

        dataMap3.put("rowMach","Q104");
        dataMap3.put("rowDtmStart","20190104083000");
        dataMap3.put("rowDtmEnd",  "20190104090000");

        map.put("method","getRowsByPage");
        dataMap2.put("pageSize","99");
        dataMap2.put("tableName","lj_gps");

        dataMap2.put("lastRow","");
        dataMap2.put("filter",dataMap3 );
        map.put("data",dataMap2);
//        map.put("paramJsonString",dataMap);
        DefaultHttpClient httpclient = new DefaultHttpClient(); //实例化http客户端
        String url="http://128.128.1.36:8020/hbaseapi/";
        // String url = "http://128.128.1.36:8020/hbaseapi/?paramJsonString= { \"method":"getRow", data: { "tableName":"lj_gps", "rowKey":"U91$20180503145611" } }"
        String qwe = JSONObject.toJSONString(map);
        try {
            String name = sendPost(qwe,url);
//            System.out.println("1111111111111111111111"+name);
            Map mapTypes = JSON.parseObject(name);

            String data =  mapTypes.get("data").toString();
            System.out.println(mapTypes);
            List<CarBean> carMaps = JSON.parseObject(data, new TypeReference<List<CarBean>>() {});

            System.out.println(data);
            carMaps.forEach(items->{
                System.out.println(items);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    @Test
    public void test(){
        demoService.test();
    }



    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void log(){

        logger.trace("----->trace");
        logger.debug("----->debug");
        logger.info("----->info");
        logger.warn("----->warn");
        logger.error("----->error");

    }


    @Test
    public void searchDemo(){
        demoService.searchDemo();
    }
}
