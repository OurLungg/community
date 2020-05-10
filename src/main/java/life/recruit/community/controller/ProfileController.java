package life.recruit.community.controller;

import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.model.User;
import life.recruit.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private ArticleService articleService;

    //动态接收跳转路径
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model) {

        //获取当前用户登录状态
        User user = (User) request.getSession().getAttribute("tb_user");
        //未登录 返回首页
        if(user == null){
            return "redirect:/";
        }


        //如果点击 我的文章 则自动跳转至actricles
        if("articles".equals(action)){
            model.addAttribute("section", "articles");
            model.addAttribute("sectionName", "我发布的文章");
        }
        //最新回复 则自动跳转至replies
        else if("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我收到的回复");
        }


        PaginationDTO paginationDTO = articleService.ListByUserId(user.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
