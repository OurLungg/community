package life.recruit.community.controller;


import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


//此发布页面不是前后端分离  需要改进
@Controller
public class PublishController {

    //如果是get请求 就渲染页面
    //如果是Post请求  就执行请求

    //ArticleService
    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/publish")
    public String publish() {

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){

        System.out.println(title);
        //出问题时可以回写内容  内容回显示
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //验证文章是否符合标准 应该放在前端 此处应该改正 没有前后端分离
        if(title == null || title.equals("")){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || description.equals("")){
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if(tag == null || tag.equals("")){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }


        //获取当前用户登录状态
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            //model加入之后 直接在前端th:text="${error} 取就可以
            model.addAttribute("error", "用户未登录");
            return "publish";
        }


        Article article = new Article();
        article.setTitle(title);
        article.setDescription(description);
        article.setTag(tag);
        article.setCreator(user.getId());
        article.setGmt_create(System.currentTimeMillis());
        article.setGmt_modified(article.getGmt_create());
        articleMapper.create(article);
        return "redirect:/";
    }
}
