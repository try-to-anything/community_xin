package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author songjian
 * @date 2020/11/13 10:28
 */
@Controller
public class AuthorizeContoller {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setClient_id("Iv1.c587788d22aee594");
        accessTokenDTO.setClient_secret("fb0632b756ef53bdc95d04cb17be50626dca0f62");
        String AccessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(AccessToken);
        if(githubUser != null && githubUser.getId() != null){
           //登录成功，写cookie和seesion
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println(user.toString());
            user.setAvatarUrl(githubUser.getAvator_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user",githubUser);//这里将user设置了一个session，统计在线人数？
            //设置session，将user的属性放到session里面，然后对这个session使用数据。
            return "redirect:/";
        }else{

            return "redirect:index";
        }
    }
}
