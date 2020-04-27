package life.recruit.community.controller;


import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.mapper.UserMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//允许这个类接收前端http的请求
@Controller
public class IndexController {

    //@RequestParam 接收参数

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name")String name, Model model) {
//        model.addAttribute("name", name);
//        return "index";
//    }

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    /**
     * 主页
     * response是请求服务器端，返回浏览器的
     * request是向浏览器发出请求
     * @return
     */
    @GetMapping("/")  //一到主页就会执行下面的方法
    public String index(HttpServletRequest request,
                        Model model){
        //如果之前登录过 则token已经被保存到数据库中，此时需要拿到cookie来查询此时的token是否在数据库中
        //若已经在数据库中则直接显示登录
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userService.findBytoken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<ArticleDTO> articleList = articleService.List();
        model.addAttribute("articles", articleList);
        //在跳转主页之前把列表信息放入model， 然后在model中渲染html
        return "index";
    }
}
