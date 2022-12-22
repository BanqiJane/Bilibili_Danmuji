package xyz.acproject.danmuji.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.returnJson.Response;
import xyz.acproject.danmuji.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jane
 * @ClassName LoginController
 * @Description TODO
 * @date 2021/6/14 19:06
 * @Copyright:2021
 */
@Controller
@RequestMapping("/manager")
public class LoginController {
    private Logger LOGGER = LogManager.getLogger(LoginController.class);
    @RequestMapping(value = "/login")
    public String loginPage(){
        if(!PublicDataConf.centerSetConf.is_manager_login()){
            return "redirect:/";
        }
        return "manager_login";
    }
    @ResponseBody
    @PostMapping(value = "/logins")
    public Response<?> login(HttpServletRequest req, @RequestParam("key")String key){
        boolean flag = false;
        if(!PublicDataConf.centerSetConf.is_manager_login()){
            Response.success(flag, req);
        }
        String ip =  IpUtils.getIpAddr(req);
        LOGGER.error("来自Ip:{}的登录尝试",ip);
        if(PublicDataConf.manager_login_size>=PublicDataConf.centerSetConf.getManager_maxSize()){
            LOGGER.error("来自Ip:{}的登录尝试到达上限",ip);
            Response.success(flag, req);
        }
        LOGGER.error("暗号:{}",PublicDataConf.centerSetConf.getManager_key());
        if(key.equals(PublicDataConf.centerSetConf.getManager_key())){
           LOGGER.error("来自Ip:{}的登录成功",ip);
           flag =true;
           req.getSession().setAttribute("manager", "manager_login");
           PublicDataConf.manager_login_size=0;
        }else{
            LOGGER.error("来自Ip:{}的登录失败", ip);
            PublicDataConf.manager_login_size +=1;
        }
        return Response.success(flag, req);
    }
}
