package life.recruit.community.mapper;

import life.recruit.community.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Article article);

    //显示数据并且添加分页
    //添加非对象的数据类型时 需要自己添加映射 上面的article是对象 不用添加映射
    @Select("select * from article limit #{offset} , #{size}")
    List<Article> List(@Param("offset") Integer offset,
                       @Param(("size")) Integer size);

    @Select("select count(id) from article")
    Integer count ();
}
