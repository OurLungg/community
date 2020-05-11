package life.recruit.community.controller;

import life.recruit.community.model.Help;
import life.recruit.community.model.User;
import life.recruit.community.result.Result;
import life.recruit.community.service.ArticleService;
import life.recruit.community.service.HelpService;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


//@RestController (Controller + ResponseBody)
@Controller
public class HelpController {

    @Autowired
    private HelpService helpService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @PostMapping("/help")
    public Result Help(@RequestBody Help help) {

        articleService.updateHelpState(help.getArticle_id(),1);

        Help help_info = new Help();
        help_info.setArticle_id(help.getArticle_id());
        help_info.setArticle_creator(help.getArticle_creator());
        help_info.setHelper(help.getHelper());
        help_info.setAccomplish(help.getAccomplish());
        helpService.insert(help_info);
        return Result.success();
    }


    @ResponseBody
    @PostMapping("/finish")
    public Result Finish(@RequestParam("id") Integer article_id,
                       @RequestParam("title") String article_title,
                       @RequestParam("user") Integer helper) {

        //更新完成状态
        helpService.updateAccomplish(article_id);
        //获取用户的个人经历
        String userBio = userService.selectBio(helper);
        userBio =article_title + ",";
        //将帮助过程写到用户个人经历中
        userService.updateBio(helper,userBio);
        return Result.success();
    }
}
