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
    @Insert("insert into test (account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);


//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "accountId", column = "account_id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "token", column = "token"),
//            @Result(property = "gmtCreate", column = "gmt_create"),
//            @Result(property = "gmtModified", column = "gmt_modified")
//    })
    @Select("Select * from test")
    List<User> findAll();


    //如果不是一个类用#{} 需要加一个注解@Param("token")
    @Select("select * from test where token = #{token}")
    User findBytoken(@Param("token") String token);


    @Select("select * from test where id = #{id}")
    User findById(@Param("id") Integer id);

    //变量找value 对象找get方法
    @Select("select * from test where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update test set name = #{name} , token = #{token} , gmt_modified = #{gmt_modified} ," +
            " avatar_url = #{avatar_url} where id = #{id}")
    void update(User user);
}
