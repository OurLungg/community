package life.recruit.community.model;

import lombok.Data;

@Data
public class TbUser {
    private Integer id;
    private String username;
    private String password;
    private String token;
}
