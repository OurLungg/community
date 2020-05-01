package life.recruit.community.mapper;

import life.recruit.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,content,type,commentator,gmt_create,gmt_modified,like_count) " +
            "values (#{parent_id},#{content},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{like_count})")
    void insert(Comment comment);

    @Select("select count(id) from comment where parent_id = #{parent_id}")
    Integer commentCountByParentId(@Param("parent_id") Integer parent_id);

    @Select("select * from comment where parent_id = #{parent_id}  and type = 1")
    List<Comment> listByParentId(@Param("parent_id") Integer parent_id);
}
