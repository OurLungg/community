package life.recruit.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//允许这个类接收前端的请求
@Controller
public class IndexController {

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name")String name, Model model) {
//        model.addAttribute("name", name);
//        return "index";
//    }

    /**
     * 主页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
