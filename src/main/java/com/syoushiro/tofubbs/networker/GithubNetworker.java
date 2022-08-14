package com.syoushiro.tofubbs.networker;

import com.alibaba.fastjson2.JSON;
import com.syoushiro.tofubbs.dto.AccessTokenDTO;
import com.syoushiro.tofubbs.dto.GithubUserInforms;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubNetworker {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String tokenInform = response.body().string();
            /*[DEBUG] System.out.println(tokenInform);*/
            String token = tokenInform.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserInforms getUserInforms(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //.url("https://api.github.com/user?access_token=" + accessToken)
                .header("Authorization", accessToken)
                .url(" https://api.github.com/user")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUserInforms githubUserInforms = JSON.parseObject(string, GithubUserInforms.class);
            return  githubUserInforms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
