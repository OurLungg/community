package life.recruit.community.result;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Result <T>{
    private int code;
    private String msg;
    private T data;

    public static Result success(){
        Result result = new Result<>();
        result.setMsg("成功！");
        result.setCode(200);
        result.setData(new ArrayList<>());
        return result;
    }


}
