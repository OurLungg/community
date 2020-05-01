package life.recruit.community.service;

import life.recruit.community.dto.CommentDTO;
import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.mapper.CommentMapper;
import life.recruit.community.mapper.UserMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.Comment;
import life.recruit.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    //开启事务
    @Transactional
    public void insert(Comment comment) {
        if(comment.getType() == 1){
            //对文章进行的评论
            commentMapper.insert(comment);
            //获取当前文章的评论数
            Article article = articleMapper.getById(comment.getParent_id());
            article.setComment_count(commentMapper.commentCountByParentId(comment.getParent_id()));
            //更新文章评论数
            articleMapper.IncCommentCount(article);
        }
        if(comment.getType() == 2){
            //对文章评论进行的回复
            System.out.println("aaa");
            commentMapper.insert(comment);
        }
    }

    public List<CommentDTO> listByArticleId(Integer id) {
        //当前文章的所有的一级评论列表
        List<Comment>  commentList = commentMapper.listByParentId(id);
        if(commentList.size() == 0) return new ArrayList<>();
        //java8 语法  遍历之后返回一个结果集,结果集要为list
        //获得了当前文章一级评论的评论者
//        List<Integer> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
        Set<Integer> commentators = commentList.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>(commentators);

        //获取评论人为转换为map
        List<User> users = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userMapper.findById(userId);
            users.add(user);
        }
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换Comment为CommentDTO
        List<CommentDTO> commentDTOS = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
