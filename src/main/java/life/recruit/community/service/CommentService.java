package life.recruit.community.service;

import life.recruit.community.mapper.ArticleMapper;
import life.recruit.community.mapper.CommentMapper;
import life.recruit.community.model.Article;
import life.recruit.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

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

}
