package xyz.acproject.danmuji.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @Description spring通用工具类
 * @author Jane
 * @ClassName SpringUtils
 * @date 2021/1/23 21:51
 * @Copyright:2021
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
    private static Map<String,String> propertiesMap;

    private static Environment environment;
 
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
 
    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
 
    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     *
     * @Description 获取springboot默认yml中的配置值
     * @author Jane
     * @date 2021/1/24 0:47
     * @params [key]
     * @return java.lang.String
     *
     * @Copyright
     */
    public static String getConfigByKey(String key) {
        if(environment!=null) {
          return environment.getProperty(key);
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
