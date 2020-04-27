package life.recruit.community.service;

import life.recruit.community.dto.ArticleDTO;
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


    public List<ArticleDTO> List() {
        List<Article> articleList = articleMapper.List();
        List<ArticleDTO> ArticleDTOList = new ArrayList<>();
        for (Article article : articleList) {
           User user = userService.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            //工具类对象 快速的把第一个对象的属性拷贝到第二个对象的属性上
            BeanUtils.copyProperties(article,articleDTO);
            articleDTO.setUser(user);
            ArticleDTOList.add(articleDTO);
        }
        return ArticleDTOList;
    }


}
