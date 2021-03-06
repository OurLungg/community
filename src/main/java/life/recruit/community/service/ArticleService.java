package life.recruit.community.service;

import life.recruit.community.dto.ArticleDTO;
import life.recruit.community.dto.ArticleQueryDTO;
import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.exception.CustomizeErrorCode;
import life.recruit.community.exception.CustomizeException;
import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    public PaginationDTO list(Integer page, Integer size,Integer type) {
        //page 页码 默认 1 ， size 默认 5(每页5个)
        //一页5个 则当前页数 = 2(i-1)  offset为真实偏移量
        Integer offset = size * (page - 1);

        //所有文章的列表
        List<Article> articleList = articleMapper.list(offset,size,type);
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
        Integer totalCount = articleMapper.countByType(type);
        //算当前页面分页数据
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO listBysearch(String search,Integer page, Integer size) {
        if (StringUtils.isBlank(search)) {
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
        articleQueryDTO.setSearch(search);

        //拿到文章总数
        // Integer totalCount = articleMapper.count();
        //带有关键字的文章总数
        Integer totalCount = articleMapper.countBySearch(articleQueryDTO);

        //page 页码 默认 1 ， size 默认 5(每页5个)
        //一页5个 则当前页数 = 2(i-1)  offset为真实偏移量
        Integer offset = size * (page - 1);

        //所有文章的列表
        // List<Article> articleList = articleMapper.list(offset,size);
        articleQueryDTO.setSize(size);
        articleQueryDTO.setPage(offset);
        List<Article> articleList = articleMapper.selectBySearch(articleQueryDTO);

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

    public ArticleDTO getById(Integer id) {
        //将文章对象绑定到文章DTO上
        Article article = articleMapper.getById(id);
        //增加文章为空判断
        if(article == null){
            throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        }
        ArticleDTO articleDTO = new ArticleDTO();
        //工具类对象 快速的把第一个对象的属性拷贝到第二个对象的属性上
        BeanUtils.copyProperties(article,articleDTO);

        //将用户信息添加到DTO中
        User user = userService.findById(article.getCreator());
        articleDTO.setUser(user);

        return articleDTO;
    }

    public void createOrUpdate(Article article) {
        if(article.getId() == null){
            //插入新文章
            article.setGmt_create(System.currentTimeMillis());
            article.setGmt_modified(article.getGmt_create());
            article.setComment_count(0);
            article.setView_count(0);
            article.setLike_count(0);
            articleMapper.create(article);
        }else {
            //编辑后的更新文章
            article.setGmt_modified(System.currentTimeMillis());
            articleMapper.update(article);
        }
    }

    //增加阅读数
    public void IncViewCount(Integer id) {
        Article article = articleMapper.getById(id);
        article.setView_count(article.getView_count() + 1);
        articleMapper.IncViewCount(article);
    }

    //搜索相同内容标签
    public List<Article> SelectByTag(ArticleDTO articleDTO) {
        //如果没有则返回空链
        if (StringUtils.isBlank(articleDTO.getTag())) {
            return new ArrayList<>();
        }
        //将tag以,隔开 并且用|拼接起来完成regexp表达式
        String[] tags = StringUtils.split(articleDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setTag(regexpTag);
        //拿到了相同标签的文章列表
        return articleMapper.selectByTag(article);
    }

    //搜索所有文章
    public List<Article> AllArticle(){
        return articleMapper.AllArticle();
    }

    //根据id删除文章
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    //更新文章的帮助状态a
    public void updateHelpState(Integer helper,Integer id,Integer state){
        articleMapper.updateHelpState(helper,id,state);
    }

    public Article findById(Integer article_id) {
        return articleMapper.findById(article_id);
    }
}
