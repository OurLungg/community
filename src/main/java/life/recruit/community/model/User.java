package life.recruit.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String name;
    private String token;
    private String account_id;
    private Long gmt_create;
    private Long gmt_modified;
    private String bio;
    private String avatar_url;
}
