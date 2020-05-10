package life.recruit.community.controller;


import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CharityController {

    @Autowired
    private ArticleService articleService;

//    RequestMapping 默认 get post都支持
    @RequestMapping("/charity")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

//        设置文章类型 type=2 为慈善信息
        int type = 2;
        //将整个页面信息(慈善信息+用户+分页)装入model模型
        PaginationDTO pagination = articleService.list(page,size,type);
        model.addAttribute("pagination", pagination);
        //在跳转主页之前把列表信息放入model， 然后在model中渲染html
        return "charity";
    }

}
