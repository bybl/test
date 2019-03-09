package net.huadong.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wj
 * @Date: 2019/1/3 16:30
 * @Version 1.0
 */
public  class BeanUtil {
    public static String sendPost(String json,String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://128.128.1.36:8020/hbaseapi/");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("paramJsonString", json));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };

        System.out.println(nvps.get(0));
        String responseBody = httpClient.execute(httpPost, responseHandler);
//        CloseableHttpResponse responseBody2 = httpClient.execute(httpPost);

//        System.out.println("222222222222"+responseBody);
//        System.out.println("222222222222"+responseBody2);
        httpClient.close();
        return responseBody;

    }
}
