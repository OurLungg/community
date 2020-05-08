package life.recruit.community.controller;

import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

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
        User tb_user = userService.findByUsernameAndPassword(username, password);

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken subjectToken = new UsernamePasswordToken(username,password);

        if (tb_user != null) {
            //登录成功
            subject.login(subjectToken);
            //写入cookie和session
            request.getSession().setAttribute("tb_user",tb_user);
            String token = UUID.randomUUID().toString();
            userService.updateToken(tb_user.getId(),token);
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
                    @RequestParam("email") String email,
                     Model model) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "login";
        } else {
            User user = userService.findUserByname(username);
            if (user == null) {
                //此用户名没人注册 可以注册
                User tbUser = new User();
                tbUser.setUsername(username);
                tbUser.setPassword(password);
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tbUser.setGmt_create(dateformat.format(System.currentTimeMillis()));
                tbUser.setEmail(email);
                userService.regist(tbUser);
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

    //无权限页面
    @RequestMapping("/noRole")
    public String noRole() {
        return "noRole";
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
