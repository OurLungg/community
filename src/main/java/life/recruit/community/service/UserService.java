package life.recruit.community.service;


import life.recruit.community.mapper.UserMapper;
import life.recruit.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccount_id());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dbUser == null) {
            //插入
            user.setGmt_create(dateformat.format(System.currentTimeMillis()));
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        } else{
            //更新
            dbUser.setGmt_modified(dateformat.format(System.currentTimeMillis()));
            dbUser.setAvatar_url(user.getAvatar_url());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }

    public User findByUsernameAndPassword(String username , String password) {
        return userMapper.findByUsernameAndPassword(username,password);
    }

    public void regist(User user) {
        userMapper.regist(user);
    }

    public User findUserByname(String username) {
        return userMapper.findUserByname(username);
    }

    public void updateToken(User user){
        userMapper.updateToken(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void updatePerms(Integer id, String perms) {
        userMapper.updatePerms(id,perms);
    }
}
