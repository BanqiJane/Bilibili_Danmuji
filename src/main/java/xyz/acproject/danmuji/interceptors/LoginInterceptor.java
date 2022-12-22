package xyz.acproject.danmuji.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.acproject.danmuji.conf.PublicDataConf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jane
 * @ClassName LoginInterceptor
 * @Description TODO
 * @date 2021/6/13 23:17
 * @Copyright:2021
 */
public class LoginInterceptor implements HandlerInterceptor {
    private Logger LOGGER = LogManager.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(PublicDataConf.centerSetConf.is_manager_login()) {
            String manager= (String) request.getSession().getAttribute("manager");
            if (StringUtils.isBlank(manager)||!manager.equals("manager_login")) {
                response.sendRedirect("/manager/login");
                return false;
            } else {
                return true;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
