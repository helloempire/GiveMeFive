package com.example.givemefive.app.tools;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by sony on 13-12-2.
 * 通过提交post请求来得到json数据，并用String方式存起来，然后返回
 */
public class PostGetJson {
    //json数据地址
    private String url;
    //得到的json用Sring存起来
    private String jsonDate;
    //提交上去的post参数
    private List<NameValuePair> params;

    public PostGetJson(String str,List<NameValuePair> p){
        url=str;
        params=p;
    }

    //返回json
    public String getJsonDate() throws IOException {
        HttpPost httpPost=new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        //超时10s
        defaultHttpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT,10000);
        HttpResponse httpResponse = null;
        try{
            httpResponse=defaultHttpClient.execute(httpPost);
            int res=httpResponse.getStatusLine().getStatusCode();
            if(res==200){
                jsonDate= EntityUtils.toString(httpResponse.getEntity());
            }else{
                jsonDate="{0}";
            }
        }catch (Exception e){
            jsonDate="{1}";
        }
        Log.i("ljj", "PostGetJson:"+jsonDate);
        return jsonDate;
    }

    //需要用到登录状态的
    /*public String getJsonDateSession() throws IOException {
        HttpPost httpPost=new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        myApp mySessionId = new myApp();
        String sessionId = mySessionId.getSessionid().toString();
        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        httpPost.setHeader("Cookie", "PHPSESSID=" + sessionId);

        //超时10s
        defaultHttpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT,10000);
        HttpResponse httpResponse=defaultHttpClient.execute(httpPost);
        int res=httpResponse.getStatusLine().getStatusCode();
        if(res==200){
            jsonDate= EntityUtils.toString(httpResponse.getEntity());
        }else{
            jsonDate="{}";
        }
        Log.i("ljj", "PostGetJson:"+jsonDate);
        return jsonDate;
    }*/

    //重新设置url
    public void setUrl(String tmpurl){
        url=tmpurl;
    }
    //重新设置提交的参数
    public void setParams(List<NameValuePair> p){
        params=p;
    }
}
