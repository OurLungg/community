package life.recruit.community.model;


import lombok.Data;

/**
 * model 模型  数据库之间传输用model
 */
@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String avatar_url;
}
