package life.recruit.community.dto;

import lombok.Data;

@Data
public class UserResultDTO <T>{
    private int code;
    private String msg;
    private T data;
    private Integer count;

    public static UserResultDTO success(Object data,Integer size){
        UserResultDTO UserResultDTO = new UserResultDTO();
        UserResultDTO.setData(data);
        UserResultDTO.setCode(0);
        UserResultDTO.setMsg("成功!");
        UserResultDTO.setCount(size);
        return UserResultDTO;
    }

}
