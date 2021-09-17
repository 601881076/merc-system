package com.xhnj.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class SMSTXUtil {






    private static final String appid = "63374";
    private static final String project = "dSVkR4";
    private static final String signature="751cc8a7d6193d6aa0f6bd2ce2568598";

    /**
     * 短信发送  阿里云
     * @param telphone 发送的手机号
     */
    public static void SendCodeAli(String telphone,String code) {

        HttpPost httpPost = new HttpPost();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            httpPost.setURI(new URI("https://api.submail.cn/message/xsend.json"));
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("appid", "63374"));     //(1)中创建的appid
            params.add(new BasicNameValuePair("to", telphone));  //发送的手机号
            params.add(new BasicNameValuePair("project", "dSVkR4"));  //(2)中创建的短信模板标识
            params.add(new BasicNameValuePair("signature", "751cc8a7d6193d6aa0f6bd2ce2568598"));  //(1)中创建的密钥
            Map<String,String> vars = new HashMap<>();
            vars.put("code",code);
            params.add(new BasicNameValuePair("vars", JSONObject.toJSONString(vars)));  //模板中可设可变参数，这里为传参。 在模板中的设置为@var(code)

            httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
            HttpResponse response = client.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity());
            log.info("短信结果"+s);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
