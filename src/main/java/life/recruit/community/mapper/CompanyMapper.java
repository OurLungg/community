package life.recruit.community.mapper;

import life.recruit.community.model.Company;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CompanyMapper {

    @Insert("insert into company (user_id,name,type,info,address,email)" +
            "values(#{user_id},#{name},#{type},#{info},#{address},#{email})")
    void create(Company company);

    @Select("select * from company where user_id = #{id}")
    Company selectByUserId(Integer id);
}
