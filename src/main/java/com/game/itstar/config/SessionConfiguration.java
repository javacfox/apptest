package com.game.itstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Author 朱斌
 * @Date 2019/9/26  2:22
 * @Desc
 */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer {
//    @Autowired
//    LoginInterceptor loginInterceptor;

    /**
     * 不需要登录拦截的url:登录注册和验证码
     */
//    final String[] notLoginInterceptPaths = {
//            "api/auth/login",
//            "api/auth/register",
//            "/api/auth/logout",
//            "/index/**",
//            "/kaptcha.jpg/**",
//            "/kaptcha/**"};
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 日志拦截器
//        //registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//        // 登录拦截器
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
//    }
//
//    /**
//     * 请求静态文件
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //请求的地址
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");//映射的项目中的路径，这个路径也可以是额外路劲，例如E://file路径
//    }
//
//    /**
//     * 增加跨域支持
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")//对哪些请求支持跨域访问
//                .allowedOrigins("*")//哪些机器可以跨域访问
//                .allowCredentials(true)//是否支持用户凭证
//                .allowedMethods("", "", "", "")//支持跨域请求的请求方式
//                .maxAge(3600);//准备响应前的缓存持续的最大时间（以秒为单位）。
//    }
    @Bean
    public RedisSessionInterceptor getSessionInterceptor() {
        return new RedisSessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有已api开头的访问都要进入RedisSessionInterceptor拦截器进行登录验证，并排除login接口(全路径)。必须写成链式，分别设置的话会创建多个拦截器。
        //必须写成getSessionInterceptor()，否则SessionInterceptor中的@Autowired会无效
        registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login")
                .excludePathPatterns("/api/auth/register")
                .excludePathPatterns("/api/auth/logout");
    }

}
