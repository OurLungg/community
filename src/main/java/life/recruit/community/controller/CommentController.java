package life.recruit.community.controller;


import life.recruit.community.dto.CommentCreateDTO;
import life.recruit.community.model.Comment;
import life.recruit.community.model.User;
import life.recruit.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    /**
     * json 异步方式 网络传输的数据结构
     * 服务端和前端 约定俗成用一套体系来互相传输数据并且都能解析 已达到前后端交流
     *
     * @return
     */

    @Autowired
    private CommentService commentService;

    //@ResponseBody 自动将对象序列化成json 返给前端
    @ResponseBody
    @RequestMapping(value = "/comment" , method = RequestMethod.POST)
    //将前端传来的数据json反序列化成对象 进行操作 返给前端时再转成json(Spring处理)
    //@RequestBody 把前端传来的json 反序列化成对象 相当于增强版的@RequestParam
    //CommentDTO是前端传来的数据
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        //从request中拿到session
        User user = (User) request.getSession().getAttribute("tb_user");
        if (user == null) {
            //未登录 提示登录 也可以返回未登录界面nologin.html
            return "redirect:/";
        }

        Comment comment = new Comment();
        comment.setParent_id(commentCreateDTO.getParent_id());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setLike_count((long) 0);
        //从session中拿到创建评论人的id
        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return null;
    }
}
