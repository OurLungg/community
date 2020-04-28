package life.recruit.community.service;

import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  service存在的意义： 可以同时使用ArticleMapper和UserMapper  起到组装的作用 （中间层）
 */
//交给spring管理
@Service
public class ArticleService {

    //目的是将user和article两张表联用。 文章article信息中要带有user的头像信息
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;


    public PaginationDTO list(Integer page, Integer size) {
        //page 页码 默认 1 ， size 默认 5(每页5个)
        //一页5个 则当前页数 = 2(i-1)  offset为真实偏移量
        Integer offset = size * (page - 1);

        //所有文章的列表
        List<Article> articleList = articleMapper.list(offset,size);
        //所有文章 + 用户信息的列表
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        //整个页面 ： 文章 + 用户信息 + 分页
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Article article : articleList) {
           User user = userService.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            //工具类对象 快速的把第一个对象的属性拷贝到第二个对象的属性上
            BeanUtils.copyProperties(article,articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        //将当前的文章列表信息加入整个页面的列表展示中
        paginationDTO.setArticles(articleDTOList);

        //拿到文章总数
        Integer totalCount = articleMapper.count();
        //算当前页面分页数据
        paginationDTO.setPagination(totalCount,page,size);



        return paginationDTO;
    }


    //根据用户的id来拿到属于该用户的文章并且展示出来 （以首页展示所有文章的方式）
    public PaginationDTO ListByUserId(Integer userID, Integer page, Integer size) {
        //page 页码 默认 1 ， size 默认 5(每页5个)
        //一页5个 则当前页数 = 2(i-1)  offset为真实偏移量
        Integer offset = size * (page - 1);

        //所有文章的列表
        List<Article> articleList = articleMapper.listByUserId(userID,offset,size);
        //所有文章 + 用户信息的列表
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        //整个页面 ： 文章 + 用户信息 + 分页
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Article article : articleList) {
            User user = userService.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            //工具类对象 快速的把第一个对象的属性拷贝到第二个对象的属性上
            BeanUtils.copyProperties(article,articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        //将当前的文章列表信息加入整个页面的列表展示中
        paginationDTO.setArticles(articleDTOList);


        //拿到文章总数
        Integer totalCount = articleMapper.countByUserId(userID);
        //算当前页面分页数据
        paginationDTO.setPagination(totalCount,page,size);



        return paginationDTO;
    }
}
