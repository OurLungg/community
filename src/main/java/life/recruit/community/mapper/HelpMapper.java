package life.recruit.community.mapper;

import life.recruit.community.model.Help;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface HelpMapper {


    @Insert("INSERT INTO help_info VALUES(#{help.article_id}, #{help.article_creator}, #{help.helper}, #{help.accomplish})")
    void insert(@Param("help") Help help);

    @Update("update help_info set accomplish = 1 where  article_id= #{article_id}")
    void updateAccomplish(@Param("article_id") Integer article_id);
}
