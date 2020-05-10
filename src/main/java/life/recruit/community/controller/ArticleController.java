package life.recruit.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.dto.ArticleNameDTO;
import life.recruit.community.dto.CommentCreateDTO;
import life.recruit.community.dto.CommentDTO;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import life.recruit.community.result.Result;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.CommentService;
import life.recruit.community.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;

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


//    pageHelper 后台系统分页
    @GetMapping@RequestMapping("/findAllArticles")
    @ResponseBody
    public Map<String,Object> findAllArticles(@RequestParam Map condition){

// 接受前端传过来的，起始页，每页记录数这两个参数，将其转换为整数
        int startPage= Integer.parseInt((String)condition.get("page"));
        int pageSize= Integer.parseInt((String)condition.get("limit"));

//  创建Page对象，将page，limit参数传入，必须位于从数据库查询数据的语句之前，否则不生效
        Page page= PageHelper.startPage(startPage, pageSize);
//  ASC是根据id 正向排序，DESC是反向排序
        PageHelper.orderBy("id ASC");
// 从数据库查询，这里返回的的article就已经分页成功了
        List<Article> articleList = articleService.AllArticle();

        List<ArticleNameDTO> articleNameDTOList = new ArrayList<>();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Article article : articleList) {
            User user = userService.findById(article.getCreator());
            ArticleNameDTO articleNameDTO = new ArticleNameDTO();
            articleNameDTO.setId(article.getId());
            articleNameDTO.setTitle(article.getTitle());
            articleNameDTO.setTag(article.getTag());
            articleNameDTO.setUsername(user.getUsername());
            articleNameDTO.setGmt_create(dateformat.format(article.getGmt_create()));
            articleNameDTOList.add(articleNameDTO);
        }

// 获取查询记录总数，必须位于从数据库查询数据的语句之后，否则不生效
        long total = page.getTotal();

// 一下是layui的分页要求的信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("data",articleNameDTOList);
        map.put("count",total);

        return  map;
    }


//    删除文章
    @DeleteMapping("/deleteArticle")
    @ResponseBody
    public Result deleteUser(@RequestParam("id") Integer id){
//        System.out.println("服务器收到的id是：" +id);
        articleService.deleteById(id);
        return Result.success();
    }
}
