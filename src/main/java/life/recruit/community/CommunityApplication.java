package life.recruit.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication相当于3个注解
 * 1. @Configuration  定义一个配置类
 * 2. @EnableAutoConfiguration spring boot自动根据jar包的依赖来自动配置项目
 * 3. @ComponentScan  spring自动扫描类上的注解, 并注入spring容器
 */

@SpringBootApplication
//用于声明mapper类的位置
@MapperScan(basePackages = "life.recruit.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
