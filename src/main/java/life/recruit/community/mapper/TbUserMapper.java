package life.recruit.community.mapper;

import life.recruit.community.model.TbUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TbUserMapper {
    @Select("select * from tb_user where username = #{username} and password = #{password}")
    TbUser findByUsernameAndPassword(@Param("username") String username,
                                     @Param("password") String password);

    @Insert("insert into tb_user(username,password) values(#{username},#{password})")
    void regist(TbUser tbUser);

    @Select(("select * from tb_user where username = #{username}"))
    TbUser findUserByname(@Param("username") String username);

    @Update("update tb_user set token = #{token} where id = #{id}")
    void updateToken(TbUser user);
}
