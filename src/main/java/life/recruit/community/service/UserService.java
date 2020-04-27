package life.recruit.community.service;


import life.recruit.community.mapper.UserMapper;
import life.recruit.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service用于将该类声明为一个服务类bean；
//@Autowired用于连接到StudentDao
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> showAll() {
        return userMapper.findAll();
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public User findBytoken(String token) {
        return userMapper.findBytoken(token);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
