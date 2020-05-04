package life.recruit.community.service;

import life.recruit.community.mapper.TbUserMapper;
import life.recruit.community.model.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    public TbUser findByUsernameAndPassword(String username ,String password) {
         return tbUserMapper.findByUsernameAndPassword(username,password);
    }

    public void regist(TbUser tbUser) {
        tbUserMapper.regist(tbUser);
    }

    public TbUser findUserByname(String username) {
        return tbUserMapper.findUserByname(username);
    }

    public void updateToken(TbUser tbUser){
        tbUserMapper.updateToken(tbUser);
    }
}
