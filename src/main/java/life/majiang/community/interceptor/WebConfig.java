package life.majiang.community.interceptor;

import org.h2.engine.Session;
import org.h2.engine.SessionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author songjian
 * @date 2020/12/8 11:14
 */

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SeetionIntercptor seetionIntercptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(seetionIntercptor).addPathPatterns("/**");
    }
}
