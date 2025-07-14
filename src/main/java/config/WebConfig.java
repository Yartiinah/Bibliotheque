package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AbonnementInterceptor abonnementInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(abonnementInterceptor)
                .addPathPatterns("/membre/**", "/reservation/**", "/prolongation/**")
                .excludePathPatterns("/membre/login-form", "/membre/login", "/membre/inscription-form-en-ligne", "/membre/inscription-en-ligne");
    }
}