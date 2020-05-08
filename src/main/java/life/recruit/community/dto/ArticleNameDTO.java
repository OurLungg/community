package life.recruit.community.dto;

import life.recruit.community.model.User;
import lombok.Data;

@Data
public class ArticleNameDTO {
    private Integer id;
    private String title;
    private String tag;
    private String gmt_create;
    private String username;
}
