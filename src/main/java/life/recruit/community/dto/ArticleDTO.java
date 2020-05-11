package life.recruit.community.dto;

import life.recruit.community.model.User;
import lombok.Data;

@Data
public class ArticleDTO {
    private Integer id;
    private String title;
    private Integer type;
    private Integer state;
    private String description;
    private String tag;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer view_count;
    private Integer comment_count;
    private Integer like_count;

    private User user;
}
