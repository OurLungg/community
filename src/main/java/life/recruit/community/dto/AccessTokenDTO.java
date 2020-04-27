package life.recruit.community.dto;

import lombok.Data;

/**
 * dto : 数据传输对象  (网络之间 类和类之间传输用Dto)
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
