package life.recruit.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInfoController {

    @RequestMapping("/userInfo")
    public String userInfo() {
        return "userInfo";
    }
}
