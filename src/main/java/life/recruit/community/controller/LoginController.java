package life.recruit.community.controller;

import life.recruit.community.model.TbUser;
import life.recruit.community.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private TbUserService tbUserService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @PostMapping("/dologin")
    String doLogin(@RequestParam("loginName") String username,
                   @RequestParam("loginPass") String password,
                   HttpServletRequest request,
                   HttpServletResponse response,
                   Model model) {
        TbUser tb_user = tbUserService.findByUsernameAndPassword(username, password);
        if (tb_user != null) {
            //登录成功
            //写入cookie和session
            request.getSession().setAttribute("tb_user",tb_user);
            String token = UUID.randomUUID().toString();
            tbUserService.updateToken(tb_user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            //登录失败
            //提示用户名或密码错误
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    @PostMapping("/doregist")
    String doRegist(@RequestParam("regName") String username,
                    @RequestParam("regPass") String password,
                    Model model) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "login";
        } else {
            TbUser user = tbUserService.findUserByname(username);
            if (user == null) {
                //此用户名没人注册 可以注册
                TbUser tbUser = new TbUser();
                tbUser.setUsername(username);
                tbUser.setPassword(password);
                tbUserService.regist(tbUser);
                model.addAttribute("msg", "恭喜你，注册成功");
                return "login";
            } else {
                //注册失败
                //提示用户名已被占用
                model.addAttribute("error", "用户名已存在");
                return "login";
            }
        }
    }


    //退出功能
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //request 拿到 user 信息
        //response 拿到 cookie 信息
        request.getSession().removeAttribute("tb_user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
