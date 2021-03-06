package life.recruit.community.mapper;

import life.recruit.community.dto.ArticleQueryDTO;
import life.recruit.community.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ArticleMapper {

    @Insert("insert into article(type,title,description,gmt_create,gmt_modified,creator,tag) " +
            "values (#{type},#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Article article);

    //显示数据并且添加分页
    //添加非对象的数据类型时 需要自己添加映射 上面的article是对象 不用添加映射
//    limit n,m  从n号开始，一页查m个
    @Select("select * from article where type = #{type} order by gmt_modified desc limit #{offset} , #{size}")
    List<Article> list(@Param("offset") Integer offset,
                       @Param(("size")) Integer size,
                       @Param("type") Integer type);

    //搜索所有文章
    @Select("select * from article")
    List<Article> AllArticle();

    //统计文章总数
    @Select("select count(id) from article where type = #{type}")
    Integer countByType (@Param("type") Integer type);

    //统计有关键字的文章数量
    @Select("select count(*) from article where title regexp #{search}")
    Integer countBySearch(ArticleQueryDTO articleQueryDTO);

    //根据关键字搜索文章来分页
    @Select("select * from article where title regexp #{search} order by gmt_modified desc limit #{page} , #{size} ")
    List<Article> selectBySearch(ArticleQueryDTO articleQueryDTO);


    //根据用户id来搜索文章
    @Select("select * from article where creator = #{userId}  order by gmt_modified desc limit #{offset} , #{size}")
    List<Article> listByUserId(@Param("userId") Integer userID,
                       @Param("offset") Integer offset,
                       @Param(("size")) Integer size);


    //统计每个用户id下发布的文章总数
    @Select("select count(id) from article where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userID);


    //根据文章id来搜索文章
     @Select("select * from article where id = #{id}")
    Article getById(@Param("id")Integer id);


     //更新文章（编辑）
    @Update("update article set type = #{type} , title = #{title} , description = #{description} , gmt_modified = #{gmt_modified} , tag = #{tag} " +
            "where id = #{id} ")
    void update(Article article);


    //更新文章阅读数
    @Update("update article set  view_count = #{view_count} where id = #{id}")
    void IncViewCount(Article article);

    //更新文章评论数
    @Update("update article set  comment_count = #{comment_count} where id = #{id}")
    void IncCommentCount(Article article);

    //正则表达式来模糊搜索标签 匹配相关内容
    @Select("select * from article where id != #{id} and tag regexp #{tag}")
    List<Article> selectByTag(Article article);

    @Delete("delete from article where id = #{id}")
    void deleteById(@Param("id") Integer id);

    //更新文章帮助状态
    @Update("update article set state = #{state} , helper = #{helper} where id = #{id} ")
    void updateHelpState(@Param("helper") Integer helper,
                         @Param("id") Integer id,
                         @Param("state") Integer state);

    @Select("select * from article where id = #{id}")
    Article findById(@Param("id") Integer id);

}
