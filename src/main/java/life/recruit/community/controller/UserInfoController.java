package life.recruit.community.controller;

import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserInfoController {


    @RequestMapping("/userInfo")
    public String userInfo(Model model, HttpServletRequest request) {
        User tb_user = (User) request.getSession().getAttribute("tb_user");
        model.addAttribute("tb_user", tb_user);
        return "userInfo";
    }


}
