package xyz.acproject.danmuji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.interceptors.LoginInterceptor;
/**
 * @author Jane
 * @ClassName WebMvcConfigurer
 * @Description 登录拦截器 与静态资源的配置
 * @date 2021/6/13 23:12
 * @Copyright:2021
 */
@Configuration
//@AutoConfigureAfter(DanmujiConfig.class)
//@Import(DanmujiConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {
    private DanmujiInitConfig danmujiInitConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        danmujiInitConfig.init();
        if (PublicDataConf.centerSetConf.is_manager_login()) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*").excludePathPatterns("/manager/login", "/manager/logins");
        }else {
        WebMvcConfigurer.super.addInterceptors(registry);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(2592000)
                .resourceChain(true).addResolver(versionResourceResolver)
        ;
//        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    /**
     * 资源url编码过滤器
     *
     * @return {@link ResourceUrlEncodingFilter}
     */
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }


    @Autowired
    public void setDanmujiConfig(DanmujiInitConfig danmujiInitConfig) {
        this.danmujiInitConfig = danmujiInitConfig;
    }
}
