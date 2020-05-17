package life.recruit.community.model;

import lombok.Data;

@Data
public class Company {
    private int id;
    private int user_id;
    private String name;
    private String type;
    private String info;
    private String address;
    private String email;
}
