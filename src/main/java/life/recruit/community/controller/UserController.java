package life.recruit.community.controller;

import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController 用于声明一个Restful风格的控制器
// @Autowired用于连接到StudentService
// @RequstMapping用于声明请求映射方法
// @RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public Object showAll() {
        return userService.showAll();
    }
}
