package life.recruit.community.controller;


import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


//此发布页面不是前后端分离  需要改进
@Controller
public class PublishController {

    //如果是get请求 就渲染页面
    //如果是Post请求  就执行请求

    @Autowired
    private ArticleService articleService;

    @GetMapping("/publish")
    public String publish() {

        return "publish";
    }

    //编辑信息
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model) {
        ArticleDTO article = articleService.getById(id);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("description", article.getDescription());
        model.addAttribute("tag", article.getTag());
        model.addAttribute("id", article.getId());
        return "publish";
    }

    //发布信息
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model){
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
        //增添了文章id 用于区分发布文章还是编辑文章
        //id可以为null 因为id自动增长 之前也没有插入
        article.setId(id);
        articleService.createOrUpdate(article);
        return "redirect:/";
    }
}
