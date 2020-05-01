package life.recruit.community.model;

import lombok.Data;

@Data
public class Comment {

    private Integer id;
    private Integer parent_id;
    private String content;
    private Integer type;
    private Integer commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
}
