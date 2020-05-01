package life.recruit.community.dto;


//封装评论和用户信息

import life.recruit.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parent_id;
    private Integer commentator;
    private Integer type;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;

    private User user;
}
