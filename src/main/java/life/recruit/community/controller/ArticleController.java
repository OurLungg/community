package life.recruit.community.controller;

import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.dto.CommentCreateDTO;
import life.recruit.community.dto.CommentDTO;
import life.recruit.community.model.Article;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 查看发布的文章详情思路：
 * 编写Controller 实现GetMapping(动态跳转 因为每个文章的id都不一样)
 * 通过service中的功能来实现查找ArticleDTO (文章+用户)
 *      在service中编写根据文章id来查询文章的功能 通过mapper接口
 *      在mapper接口中实现Sql语句
 * 查找得到之后将信息写到html渲染展示
 */

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //拿到评论列表
    @Autowired
    private CommentService commentService;

    //@PathVariable的值可以传到 /{id}里面 实现动态跳转页面
    //跳转之后再查询这个id的文章是否存在，存在则直接从数据库中拿出来展示
    @GetMapping("/article/{id}")
    public String Article(@PathVariable(name = "id") Integer id,
                          Model model) {
        //返回DTO（文章+用户） 是组装后的model
        ArticleDTO articleDTO = articleService.getById(id);
        //获取所有带相同标签的文章
        List<Article> regexpTagArticle = articleService.SelectByTag(articleDTO);
        //返回DTO（评论+用户）
        List<CommentDTO> comments = commentService.listByArticleId(id);

        //增加文章阅读数
        articleService.IncViewCount(id);

        //将信息展示到页面
        model.addAttribute("comments", comments);
        model.addAttribute("article", articleDTO);
        model.addAttribute("regexpTagArticle", regexpTagArticle);
        return "article";
    }
}
