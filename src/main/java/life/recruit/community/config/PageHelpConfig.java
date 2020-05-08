package life.recruit.community.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageHelpConfig {
    @Bean
    public PageHelper createPageHelper() {
        PageHelper page = new PageHelper();
        return page;
    }
}
