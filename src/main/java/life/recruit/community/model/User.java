package life.recruit.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String name;
    private String email;
    private String token;
    private String account_id;
    private String gmt_create;
    private String gmt_modified;
    private String bio;
    private String avatar_url;
}
