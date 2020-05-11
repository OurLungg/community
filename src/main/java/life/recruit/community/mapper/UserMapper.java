package life.recruit.community.mapper;


import life.recruit.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 数据库操作
 */
@Component
@Mapper
public interface UserMapper {

    //mybatis会把#{name}替换成user中的name
    //如果是一个类 可以直接用#{}
    @Insert("insert into tb_user (account_id,name,token,gmt_create,gmt_modified,avatar_url,email) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{email})")
    void insert(User user);

//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "accountId", column = "account_id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "token", column = "token"),
//            @Result(property = "gmtCreate", column = "gmt_create"),
//            @Result(property = "gmtModified", column = "gmt_modified")
//    })

    //查询用户信息 用于后台管理
    @Select("Select id,username,perms,email,gmt_create,gmt_modified from tb_user")
    List<User> findAll();


    //如果不是一个类用#{} 需要加一个注解@Param("token")
    @Select("select * from tb_user where token = #{token}")
    User findBytoken(@Param("token") String token);


    @Select("select * from tb_user where id = #{id}")
    User findById(@Param("id") Integer id);

    //变量找value 对象找get方法
    @Select("select * from tb_user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update tb_user set name = #{name} , token = #{token} , gmt_modified = #{gmtModified} ," +
            " avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);


    @Select("select * from tb_user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username,
                                   @Param("password") String password);


    @Insert("insert into tb_user(username,password,email,gmt_create) values(#{username},#{password},#{email},#{gmt_create})")
    void regist(User user);

    @Select(("select * from tb_user where username = #{username}"))
    User findUserByname(@Param("username") String username);

    @Update("update tb_user set token = #{token} , gmt_modified = #{gmt_modified} where id = #{id}")
    void updateToken(User user);

    @Delete("delete from tb_user where id = #{id}")
    void deleteById(@Param("id") Integer id);

    @Update("update tb_user set perms = #{perms} where id = #{id}")
    void updatePerms(@Param("id") Integer id,
                     @Param("perms") String perms);

    @Select("select bio from tb_user where id = #{id}")
    String selectBio(@Param("id") Integer id);

    @Update("update tb_user set bio = #{bio} where id = #{id}")
    void updateBio(@Param("id") Integer id,
                   @Param("bio") String bio);

    @Update("update tb_user set name = #{name} ,email = #{email} , avatar_url = #{avatar_url} where id = #{id}")
    void updateInfo(User tb_user);
}
