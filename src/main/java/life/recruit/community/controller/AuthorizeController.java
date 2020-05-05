package life.recruit.community.controller;


import life.recruit.community.dto.AccessTokenDTO;
import life.recruit.community.dto.GithubUser;
import life.recruit.community.model.User;
import life.recruit.community.provider.GithubProvider;
import life.recruit.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    //为了在不同的环境修改成不同的配置文件 方便部署
    //value 会去配置文件中读取数据并注入到变量中(key,value)
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.sercet}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    //因为 @Mapper 这个注解是 Mybatis 提供的，而 @Autowried 注解是 Spring 提供的，
    // IDEA能理解 Spring 的上下文，但是却和 Mybatis 关联不上。
    // 而且我们可以根据 @Autowried 源码看到，默认情况下，@Autowried 要求依赖对象必须存在，那么此时 IDEA 只能给个红色警告了。
    // 在userMapper中加入@Component 欺骗idea解决问题

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           //session 在 request
                           //cookie 在 response
                           HttpServletResponse response) {
        //java模拟post请求(OkHttp) 传给github来获取access_token
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null && githubUser.getId()!= null){
            //登录成功
            User user = new User();
            //获取并生成用户信息 token唯一标识
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            //对于github用户唯一的标识是accountId，当登录成功后去数据库查询是否有相同的accountId
            //如果有则update最新的token
            //如果没有则create新用户
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setAvatar_url(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            //将token放入cookie中 做持久化登录状态使用
            response.addCookie(new Cookie("token",token));

            //重定向到index 并且去掉网址中的请求参数
            return "redirect:/";
        }else{
            //登录失败 重新登录
            log.error("callback get github error {}",githubUser);
            return "redirect:/";
        }

    }

}
