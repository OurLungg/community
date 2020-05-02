package life.recruit.community.controller;


import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.model.User;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
    private ArticleService articleService;

    /**
     * 主页
     * response是请求服务器端，返回浏览器的
     * request是向浏览器发出请求
     * @return
     */
    @GetMapping("/")  //一到主页就会执行下面的方法
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){

        //将整个页面信息(文章+用户+分页)装入model模型
        PaginationDTO pagination = articleService.list(page,size);
        model.addAttribute("pagination", pagination);
        //在跳转主页之前把列表信息放入model， 然后在model中渲染html
        return "index";
    }
}
