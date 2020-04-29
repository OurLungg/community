package life.recruit.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器 实现代码复用
 */
@Configuration
//@EnableWebMvc 会拦截静态资源 自动实现addResourceHandlers
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Sessioninterceptor sessioninterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //对所有地址都经过interceptor处理
        registry.addInterceptor(sessioninterceptor).addPathPatterns("/**");
    }

}
