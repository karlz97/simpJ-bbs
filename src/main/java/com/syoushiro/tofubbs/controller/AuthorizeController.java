package com.syoushiro.tofubbs.controller;

import com.syoushiro.tofubbs.dto.AccessTokenDTO;
import com.syoushiro.tofubbs.dto.GithubUserInforms;
import com.syoushiro.tofubbs.networker.GithubNetworker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubNetworker githubNetworker;

    @GetMapping("/callback")  //http://localhost:8080/callback
    public String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("a4ad1d7966a233ad712c");
        accessTokenDTO.setClient_secret("b72c3229c50bc94b1e925635e225c9131da6dd63");
        String accessToken = githubNetworker.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUserInforms userInforms = githubNetworker.getUserInforms(accessToken);
        System.out.println(userInforms.getName());
        return "index";
    }
}
