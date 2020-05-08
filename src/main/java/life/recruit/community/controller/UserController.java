package life.recruit.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import life.recruit.community.dto.UserResultDTO;
import life.recruit.community.model.User;
import life.recruit.community.result.Result;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController 用于声明一个Restful风格的控制器
// @Autowired用于连接到StudentService
// @RequstMapping用于声明请求映射方法
// @RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String,Object> findAll(@RequestParam Map condition){

// 接受前端传过来的，起始页，每页记录数这两个参数，将其转换为整数
        int startPage= Integer.parseInt((String)condition.get("page"));
        int pageSize= Integer.parseInt((String)condition.get("limit"));

//  创建Page对象，将page，limit参数传入，必须位于从数据库查询数据的语句之前，否则不生效
        Page page= PageHelper.startPage(startPage, pageSize);
//  ASC是根据id 正向排序，DESC是反向排序
        PageHelper.orderBy("id ASC");
// 从数据库查询，这里返回的的allUser就已经分页成功了
        List<User> allUser = userService.showAll();

// 获取查询记录总数，必须位于从数据库查询数据的语句之后，否则不生效
        long total = page.getTotal();

// 一下是layui的分页要求的信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("data",allUser);
        map.put("count",total);

        return  map;
    }


    @DeleteMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestParam("id") Integer id){
//        System.out.println("服务器收到的id是：" +id);
        userService.deleteById(id);
        return Result.success();
    }

}
