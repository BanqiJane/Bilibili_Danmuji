package xyz.acproject.danmuji.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.acproject.danmuji.component.TaskRegisterComponent;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.config.DanmujiInitConfig;
import xyz.acproject.danmuji.entity.login_data.LoginData;
import xyz.acproject.danmuji.entity.login_data.Qrcode;
import xyz.acproject.danmuji.entity.other.EditionResult;
import xyz.acproject.danmuji.entity.other.InitCheckServerParam;
import xyz.acproject.danmuji.entity.room_data.RoomBlock;
import xyz.acproject.danmuji.file.JsonFileTools;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.returnJson.Response;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;
import xyz.acproject.danmuji.utils.FastJsonUtils;
import xyz.acproject.danmuji.utils.QrcodeUtils;
import xyz.acproject.danmuji.utils.SchedulingRunnableUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BanqiJane
 * @ClassName WebController
 * @Description TODO
 * @date 2020年8月10日 下午12:21:50
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Controller
@Deprecated
public class WebController {
    private SetService checkService;
    private ClientService clientService;
    @Resource
    private DanmujiInitConfig danmujiInitConfig;
    private TaskRegisterComponent taskRegisterComponent;

    @RequestMapping(value = {"/", "index"})
    public String index(HttpServletRequest req, Model model) {
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            if (req.getSession().getAttribute("status") == null) {
                req.getSession().setAttribute("status", "login");
            }
        }
        model.addAttribute("ANAME", PublicDataConf.ANCHOR_NAME);
        model.addAttribute("EDITION", PublicDataConf.EDITION);
        model.addAttribute("NEW_EDITION", PublicDataConf.NEW_EDITION);
        model.addAttribute("ROOMID", PublicDataConf.ROOMID);
        model.addAttribute("POPU", PublicDataConf.ROOM_POPULARITY);
        model.addAttribute("MANAGER", PublicDataConf.USERMANAGER != null ? PublicDataConf.USERMANAGER.isIs_manager() : false);
        if (PublicDataConf.USER != null) {
            model.addAttribute("USER", PublicDataConf.USER);
        }

        return "index";
    }

    @RequestMapping(value = "/connect")
    public String connect(Model model) {
        model.addAttribute("ROOMID", PublicDataConf.centerSetConf.getRoomid());
        return "connect";
    }

    @RequestMapping(value = "/cookie_set")
    public String cookie_set(Model model) {
//        model.addAttribute("ROOMID", PublicDataConf.centerSetConf.getRoomid());
        return "cookie_set";
    }


    @RequestMapping(value = "/login")
    public String login(HttpServletRequest req) {
        if (req.getSession().getAttribute("status") == null) {
            return "login";
        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = "/quit")
    public String quit(HttpServletRequest req) {
        req.getSession().setAttribute("status", null);
        req.getSession().removeAttribute("status");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            HttpUserData.quit();
            checkService.quit();
        }
        return "login";
    }

    @ResponseBody
    @GetMapping(value = "/qrcode")
    public void qrcode(HttpServletRequest req, HttpServletResponse resp, @RequestParam("url") String url) {
        if (req.getSession().getAttribute("status") != null)
            return;
        QrcodeUtils.creatRrCode(url, 140, 140, resp);
    }

    @ResponseBody
    @PostMapping(value = "/qrcodeUrl")
    public Response<?> qrcodeUrl(HttpServletRequest req) {
        if (req.getSession().getAttribute("status") != null)
            return null;
        Qrcode qrcode = HttpUserData.httpGetQrcode();
        req.getSession().setAttribute("auth", qrcode.getOauthKey());
        return Response.success(qrcode.getUrl(), req);
    }

    @ResponseBody
    @PostMapping(value = "/loginCheck")
    public JSONObject loginCheck(HttpServletRequest req) {
        if (req.getSession().getAttribute("status") != null)
            return null;
        JSONObject jsonObject = null;
        String oauthKey = (String) req.getSession().getAttribute("auth");
        LoginData loginData = new LoginData();
        loginData.setOauthKey(oauthKey);
        String jsonString = HttpUserData.httpPostCookie(loginData);
        jsonObject = JSONObject.parseObject(jsonString);
        if (jsonObject != null) {
            if (jsonObject.getBoolean("status")) {
                danmujiInitConfig.init();
//                checkService.init();
                if (PublicDataConf.USER != null) {
                    req.getSession().setAttribute("status", "login");
                }
            }
        }
        return jsonObject;
    }


    @ResponseBody
    @PostMapping(value = "/customCookie")
    public Response<?> customCookie(String cookie,HttpServletRequest req){
        boolean flag = CurrencyTools.pariseCookie(cookie);
        if(flag){
            danmujiInitConfig.init();
        }
        return Response.success(flag,req);
    }

    @ResponseBody
    @GetMapping(value = "/connectRoom")
    public Response<?> connectRoom(HttpServletRequest req, @RequestParam("roomid") Long roomid) {
        boolean flag = false;
        if (null == PublicDataConf.webSocketProxy || !PublicDataConf.webSocketProxy.isOpen()) {
            try {
                clientService.startConnService(roomid);
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if (PublicDataConf.ROOMID != null) {
                PublicDataConf.centerSetConf.setRoomid(PublicDataConf.ROOMID);
                PublicDataConf.ROOMID_SAFE = PublicDataConf.ROOMID;
            }
            checkService.connectSet(PublicDataConf.centerSetConf);
        }
        if (PublicDataConf.webSocketProxy != null) {
            if (PublicDataConf.webSocketProxy.isOpen()) {
                flag = true;
            }
        }
        return Response.success(flag, req);
    }

    @ResponseBody
    @GetMapping(value = "/disconnectRoom")
    public Response<?> disconnectRoom(HttpServletRequest req) {
        boolean flag = false;
        flag = clientService.closeConnService();
        return Response.success(flag, req);
    }

    @ResponseBody
    @GetMapping(value = "/connectCheck")
    public Response<?> connectCheck(HttpServletRequest req) {
        boolean flag = false;
        if (PublicDataConf.webSocketProxy != null) {
            if (PublicDataConf.webSocketProxy.isOpen()) {
                flag = true;
            }
        }
        return Response.success(flag, req);
    }

    @ResponseBody
    @GetMapping(value = "/heartBeat")
    public Response<?> heartBeat(HttpServletRequest req) {
        return Response.success(PublicDataConf.ROOM_POPULARITY, req);
    }

    @ResponseBody
    @GetMapping(value = "/getSet")
    public Response<?> getSet(HttpServletRequest req) {
        return Response.success(PublicDataConf.centerSetConf, req);
    }

    @ResponseBody
    @PostMapping(value = "/sendSet")
    public Response<?> sendSet(HttpServletRequest req, @RequestParam("set") String set) {
        try {
            CenterSetConf centerSetConf = JSONObject.parseObject(set, CenterSetConf.class);
            //登录设置
            if (centerSetConf.isIs_manager_login() && StringUtils.isNotBlank(centerSetConf.getManager_key())) {
                centerSetConf.setManager_key(DigestUtils.md5DigestAsHex(centerSetConf.getManager_key().getBytes()));
            }else if(StringUtils.isBlank(centerSetConf.getManager_key())){
                centerSetConf.setManager_key(PublicDataConf.centerSetConf.getManager_key());
            }
            //签到时间 & 打卡时间
            if(centerSetConf.isIs_dosign()&&!centerSetConf.getSign_time().equals(PublicDataConf.centerSetConf.getSign_time())){
                SchedulingRunnableUtil task = new SchedulingRunnableUtil("dosignTask", "dosign");
                taskRegisterComponent.addTask(task, CurrencyTools.dateStringToCron(centerSetConf.getSign_time()));
            }
            if(centerSetConf.getClock_in()!=null&&centerSetConf.getClock_in().isIs_open()&&!centerSetConf.getClock_in().getTime().equals(PublicDataConf.centerSetConf.getClock_in().getTime())){
                SchedulingRunnableUtil dakatask = new SchedulingRunnableUtil("dosignTask", "clockin");
                taskRegisterComponent.addTask(dakatask, CurrencyTools.dateStringToCron(centerSetConf.getClock_in().getTime()));
            }
            //自动送礼时间
            if(centerSetConf.getAuto_gift()!=null&&centerSetConf.getAuto_gift().isIs_open()&&
                    !centerSetConf.getAuto_gift().getTime().equals(PublicDataConf.centerSetConf.getAuto_gift().getTime())){
                SchedulingRunnableUtil autoSendGiftTask = new SchedulingRunnableUtil("dosignTask","autosendgift");
                taskRegisterComponent.removeTask(autoSendGiftTask);
            }
            checkService.changeSet(centerSetConf);
        } catch (Exception e) {
            // TODO: handle exception
            return Response.success(false, req);
        }
        return Response.success(true, req);
    }

    @ResponseBody
    @GetMapping(value = "/getIp")
    public Response<?> getIp(HttpServletRequest req) {
        String ip = HttpOtherData.httpGetIp();
        if (!StringUtils.isEmpty(ip)) {
            return Response.success(ip, req);
        } else {
            return Response.success(null, req);
        }

    }

    @ResponseBody
    @GetMapping("/checkWebInit")
    public Response<?> checkWebInit(HttpServletRequest req){
        InitCheckServerParam param = new InitCheckServerParam();
        param.setInit_edition(PublicDataConf.INIT_CHECK_EDITION);
        param.setInit_announce(PublicDataConf.INIT_CHECK_ANNOUNCE);
        return Response.success(param,req);
    }

    @ResponseBody
    @GetMapping("/checkNewAnnounce")
    public Response<?> checkNewAnnounce(HttpServletRequest req){
        try {
            PublicDataConf.INIT_CHECK_ANNOUNCE=true;
            return Response.success(StringUtils.isNotBlank(PublicDataConf.ANNOUNCE)?StringUtils.replace(PublicDataConf.ANNOUNCE,"\r\n","<br/>"):"公告获取失败",req);
        } finally {
            //一次性公告清除了
            PublicDataConf.ANNOUNCE = null;
        }
    }

    @ResponseBody
    @GetMapping(value = "/checkupdate")
    public Response<?> checkUpdate(HttpServletRequest req) {
        String edition = HttpOtherData.httpGetNewEdition();
        EditionResult editionResult = new EditionResult();
        editionResult.setEdition(edition);
        if (!StringUtils.isEmpty(edition)) {
            if (edition.equals("获取公告失败")) {
                editionResult.setStatus(2);
                return Response.success(editionResult, req);
            } else {
                PublicDataConf.INIT_CHECK_EDITION=true;
                if (!edition.equals(PublicDataConf.EDITION)) {
                    editionResult.setStatus(0);
                    return Response.success(editionResult, req);
                } else {
                    editionResult.setStatus(1);
                    return Response.success(editionResult, req);
                }
            }
        } else {
            editionResult.setStatus(2);
            return Response.success(editionResult, req);
        }
    }

    @ResponseBody
    @GetMapping(value = "/getNewEdition")
    public Response<?> getNewEdition(HttpServletRequest req) {
        String edition = HttpOtherData.httpGetNewEdition();
        if (!StringUtils.isEmpty(edition)) {
            if (edition.equals("获取公告失败")) {
                return Response.success(-1, req);
            } else {
                if (!edition.equals(PublicDataConf.EDITION)) {
                    return Response.success(edition, req);
                } else {
                    return Response.success(-1, req);
                }
            }
        } else {
            return Response.success(-1, req);
        }
    }

    @ResponseBody
    @GetMapping(value = "/block")
    public Response<?> block(@RequestParam("uid") long uid, @RequestParam("time") short time, HttpServletRequest req) {
        short code = -1;
        if (time > 720 && time <= 0) {
            //required time error
            code = 2;
            return Response.success(code, req);
        }
        if (StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            code = HttpUserData.httpPostAddBlock(uid, time);
        }
        return Response.success(code, req);
    }

    @ResponseBody
    @GetMapping(value = "/del_block")
    public Response<?> del_block(@RequestParam("id") long id, HttpServletRequest req) {
        short code = -1;
        code = HttpUserData.httpPostDeleteBlock(id);
        return Response.success(code, req);
    }


    @ResponseBody
    @GetMapping(value = "/blocks")
    public Response<?> blocks(@RequestParam("page") int page, HttpServletRequest req) {
        if (page <= 0) {
            page = 1;
        }
        List<RoomBlock> roomBlockList = new ArrayList<>();
        if (PublicDataConf.ROOMID != null && StringUtils.isNotBlank(PublicDataConf.USERCOOKIE) && PublicDataConf.USERMANAGER != null && PublicDataConf.USERMANAGER.isIs_manager()) {
            roomBlockList = HttpRoomData.getBlockList(page);
        }
        return Response.success(roomBlockList, req);
    }

    @ResponseBody
    @GetMapping(value = "/setExport")
    public Response<?> setExport(HttpServletRequest req) {
        boolean flag = JsonFileTools.createJsonFile(PublicDataConf.centerSetConf.toJson());
        if (flag) {
            return Response.success(0, req);
        } else {
            return Response.success(1, req);
        }
    }

    //配置文件导入
    @ResponseBody
    @PostMapping(value = "/setImport")
    public Response<?> setImport(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        if (!file.getResource().getFilename().endsWith(".json")) {
            return Response.success(2, req);
        }
        String jsonString = new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            CenterSetConf centerSetConf = FastJsonUtils.parseObject(jsonString, CenterSetConf.class);
            if (centerSetConf != null) {
                centerSetConf = ParseSetStatusTools.initCenterChildConfig(centerSetConf);
                centerSetConf.setClock_in(PublicDataConf.centerSetConf.getClock_in());
                BeanUtils.copyProperties(centerSetConf, PublicDataConf.centerSetConf);
                //如果有密钥 如果没密
                checkService.changeSet(centerSetConf);
            }
        } catch (Exception e) {
            return Response.success(1, req);
        }
        return Response.success(0, req);
    }

    @Autowired
    public void setCheckService(SetService checkService) {
        this.checkService = checkService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setTaskRegisterComponent(TaskRegisterComponent taskRegisterComponent) {
        this.taskRegisterComponent = taskRegisterComponent;
    }
}
