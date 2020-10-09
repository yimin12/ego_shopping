package yimin.ego.commons.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 23:32
 *   @Description :
 *
 */
public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param){

        // Create httpCLient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try{
            // step 1: create url
            URIBuilder builder = new URIBuilder(url);
            if(param != null){
                for(String key:param.keySet()){
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            try {
                if(response != null){
                    response.close();
                }
                httpClient.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try{
            HttpPost httpPost = new HttpPost(url);
            if(param != null){
                List<NameValuePair> paramList = new ArrayList<>();
                for(String key:param.keySet()){
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // simulate the form
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // execute the http request
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                response.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try{
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                response.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return resultString;
    }
}
