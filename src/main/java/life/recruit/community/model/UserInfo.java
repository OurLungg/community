package life.recruit.community.model;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private int user_id;
    private String school;
    private String subject;
    private String tel;
    private String skill;
    private String project;
    private String info;
}
