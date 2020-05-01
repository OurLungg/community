package life.recruit.community.dto;

import lombok.Data;


//是前端传递过来的DTO  用于接收
@Data
public class CommentCreateDTO {
    private Integer parent_id;
    private String content;
    private Integer type;
}

