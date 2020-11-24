package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * @author songjian
 * @date 2020/11/13 10:34
 */
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

//        Request request = new Request.Builder()
//                .url("https://github.com/login/oauth/access_token?client_id="+
//                        accessTokenDTO.getClient_id()+"&client_secret="+
//                        accessTokenDTO.getClient_secret()+"&code="+
//                        accessTokenDTO.getCode()+"&redirect_uri="+
//                        accessTokenDTO.getRedirect_uri()+"&state="+
//                        accessTokenDTO.getState())
//                .post(body)
//                .build();

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
//            System.out.println(string);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(1000, TimeUnit.SECONDS).
                build();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=" + accessToken)
//                .build();


//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
//                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
//                .build();
//        Request.Builder builder = new Request.Builder().url("http://10.7.5.144/oos");


//        OkHttpClient client = new OkHttpClient().newBuilder().
//                connectTimeout(60000, TimeUnit.MILLISECONDS)
//                .readTimeout(60000, TimeUnit.MILLISECONDS)
//                .build();

        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
