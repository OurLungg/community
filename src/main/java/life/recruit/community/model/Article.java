package life.recruit.community.model;


import lombok.Data;

@Data
public class Article {
    private Integer id;
    private String title;
    private int type;
    private String description;
    private String tag;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer view_count;
    private Integer comment_count;
    private Integer like_count;
}