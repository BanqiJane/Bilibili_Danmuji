package xyz.acproject.danmuji.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.config.DanmujiConfig;
import xyz.acproject.danmuji.controller.param.IndexDataParam;
import xyz.acproject.danmuji.entity.login_data.LoginData;
import xyz.acproject.danmuji.entity.login_data.Qrcode;
import xyz.acproject.danmuji.entity.room_data.RoomBlock;
import xyz.acproject.danmuji.file.JsonFileTools;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.returnJson.Response;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.utils.FastJsonUtils;
import xyz.acproject.danmuji.utils.QrcodeUtils;

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
 * @author Jane
 * @ClassName ApiController
 * @Description TODO
 * @date 2021/4/12 16:04
 * @Copyright:2021
 */
@RestController
@RequestMapping("/api")
public class ApiController {


    private SetService checkService;

    private ClientService clientService;

    @Resource
    private DanmujiConfig danmujiConfig;

    /**
     * @api {GET} /api/index index
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取主页信息-index
     * @apiDescription 获取主页信息
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"xnwAnYbTa","result":{},"code":"WjXiGeCHP"}
     */
    @GetMapping("/index")
    public Response<?> index(HttpServletRequest req) {
        IndexDataParam indexDataParam = new IndexDataParam();
        indexDataParam.setEdition(PublicDataConf.EDITION);
        indexDataParam.setAnchorName(PublicDataConf.ANCHOR_NAME);
        indexDataParam.setManager(PublicDataConf.USERMANAGER != null ? PublicDataConf.USERMANAGER.isIs_manager() : false);
        indexDataParam.setPopularity(PublicDataConf.ROOM_POPULARITY);
        indexDataParam.setRoomId(PublicDataConf.ROOMID);
        indexDataParam.setUser(PublicDataConf.USER);
        return Response.success(indexDataParam, req);
    }

    /**
     * @api {GET} /api/connect connect
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取历史连接房间-connect
     * @apiDescription 获取历史连接房间
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"liHlP6","result":{},"code":"zbg5udzW0"}
     */
    @GetMapping("/connect")
    public Response<?> connect(HttpServletRequest req) {
        return Response.success(PublicDataConf.centerSetConf.getRoomid(), req);
    }

    /**
     * @api {GET} /api/quit quit
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 退出登录-quit
     * @apiDescription 退出登录
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"EoS0dG5Z","result":{},"code":"y3D"}
     */
    @GetMapping("/quit")
    public Response<?> quit(HttpServletRequest req) {
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            HttpUserData.quit();
            checkService.quit();
            Response.success(0, req);
        }
        return Response.success(1, req);
    }


    /**
     * @api {GET} /api/qrcode qrcode
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取二维码图片-qrcode
     * @apiDescription 获取二维码图片接口
     * @apiParam (请求参数) {String} url
     * @apiParamExample 请求参数示例
     * url=t0
     * @apiSuccess (响应结果) {Object} response
     * @apiSuccessExample 响应结果示例
     * null
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public void qrcode(HttpServletRequest req, HttpServletResponse resp, @RequestParam("url") String url) {
//        if (req.getSession().getAttribute("status") != null)
//            return;
        QrcodeUtils.creatRrCode(url, 140, 140, resp);
    }


    /**
     * @api {POST} /api/qrcodeUrl qrcodeUrl
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取二维码图片链接包括key-qrcodeUrl
     * @apiDescription 获取二维码图片链接包括key key需要扫码最后一部传给后台
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"EQt1ZLezYX","result":{},"code":"u"}
     */
    @RequestMapping(value = "/qrcodeUrl", method = RequestMethod.POST)
    public Response<?> qrcodeUrl(HttpServletRequest req) {
//        if (req.getSession().getAttribute("status") != null)
//            return null;
        Qrcode qrcode = HttpUserData.httpGetQrcode();
        LoginData loginData = new LoginData();
        loginData.setOauthKey(qrcode.getOauthKey());
        loginData.setGourl(qrcode.getUrl());
//        req.getSession().setAttribute("auth", qrcode.getOauthKey());
        return Response.success(loginData, req);
    }


    /**
     * @api {POST} /api/loginCheck loginCheck
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 轮询扫码状态-loginCheck
     * @apiDescription 轮询扫码状态
     * @apiParam (请求参数) {String} oauthKey 二维码图片接口获取的key
     * @apiParamExample 请求参数示例
     * oauthKey=x63hUCoxIN
     * @apiSuccess (响应结果) {Object} response
     * @apiSuccessExample 响应结果示例
     * {}
     */
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public JSONObject loginCheck(HttpServletRequest req, @RequestParam("oauthKey") String oauthKey) {
//        if (req.getSession().getAttribute("status") != null)
//            return null;
        JSONObject jsonObject = null;
        LoginData loginData = new LoginData();
        loginData.setOauthKey(oauthKey);
        String jsonString = HttpUserData.httpPostCookie(loginData);
        jsonObject = JSONObject.parseObject(jsonString);
        if (jsonObject != null) {
            if (jsonObject.getBoolean("status")) {
                danmujiConfig.init();
                checkService.init();
//                if (PublicDataConf.USER != null) {
//                    req.getSession().setAttribute("status", "login");
//                }
            }
        }
        return jsonObject;
    }

    /**
     * @api {GET} /api/loginCheck loginCheck
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 登录状态检查接口-loginCheck
     * @apiDescription 登录状态检查接口
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"K1","result":{},"code":"b"}
     */
    @GetMapping("/loginCheck")
    public Response<?> loginCheck(HttpServletRequest req) {
        HttpUserData.httpGetUserInfo();
        if (StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            return Response.success(0, req);
        }
        return Response.success(1, req);
    }

    /**
     * @api {GET} /api/connectRoom connectRoom
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 连接房间-connectRoom
     * @apiDescription 连接房间
     * @apiParam (请求参数) {Number} roomid 要连接的房间号
     * @apiParamExample 请求参数示例
     * roomid=5980
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"1QrGH","result":{},"code":"VvATLV"}
     */
    @RequestMapping(value = "/connectRoom", method = RequestMethod.GET)
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


    /**
     * @api {GET} /api/disconnectRoom disconnectRoom
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 断开连接房间-disconnectRoom
     * @apiDescription 断开连接房间
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"IV8O","result":{},"code":"vyKwr"}
     */
    @RequestMapping(value = "/disconnectRoom", method = RequestMethod.GET)
    public Response<?> disconnectRoom(HttpServletRequest req) {
        boolean flag = false;
        flag = clientService.closeConnService();
        return Response.success(flag, req);
    }


    /**
     * @api {GET} /api/connectCheck connectCheck
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 连接检查-connectCheck
     * @apiDescription 连接检查
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"wfIIn","result":{},"code":"UVBxP"}
     */
    @RequestMapping(value = "/connectCheck", method = RequestMethod.GET)
    public Response<?> connectCheck(HttpServletRequest req) {
        boolean flag = false;
        if (PublicDataConf.webSocketProxy != null) {
            if (PublicDataConf.webSocketProxy.isOpen()) {
                flag = true;
            }
        }
        return Response.success(flag, req);
    }


    /**
     * @api {GET} /api/heartBeat heartBeat
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 心跳返回人气-heartBeat
     * @apiDescription 心跳返回人气（建议30秒更新一次）
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"lz5B","result":{},"code":"372r"}
     */
    @RequestMapping(value = "/heartBeat", method = RequestMethod.GET)
    public Response<?> heartBeat(HttpServletRequest req) {
        return Response.success(PublicDataConf.ROOM_POPULARITY, req);
    }


    /**
     * @api {GET} /api/getSet getSet
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取设置-getSet
     * @apiDescription 获取设置
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"T","result":{},"code":"H"}
     */
    @RequestMapping(value = "/getSet", method = RequestMethod.GET)
    public Response<?> getSet(HttpServletRequest req) {
        return Response.success(PublicDataConf.centerSetConf, req);
    }


    /**
     * @api {POST} /api/sendSet sendSet
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 保存设置-sendSet
     * @apiDescription 保存设置
     * @apiParam (请求参数) {String} set的json串
     * @apiParamExample 请求参数示例
     * set=VMNpNzrR
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"LyxCS","result":{},"code":"CCg"}
     */
    @RequestMapping(value = "/sendSet", method = RequestMethod.POST)
    public Response<?> sendSet(HttpServletRequest req, @RequestParam("set") String set) {
        try {
            CenterSetConf centerSetConf = JSONObject.parseObject(set, CenterSetConf.class);
            checkService.changeSet(centerSetConf);

        } catch (Exception e) {
            // TODO: handle exception
            return Response.success(false, req);
        }
        return Response.success(true, req);
    }

    /**
     * @api {GET} /api/getIp getIp
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 获取ip地址-getIp
     * @apiDescription 获取ip地址
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"KcihQ","result":{},"code":"j9cMT"}
     */
    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public Response<?> getIp(HttpServletRequest req) {
        String ip = HttpOtherData.httpGetIp();
        if (!StringUtils.isEmpty(ip)) {
            return Response.success(ip, req);
        } else {
            return Response.success(null, req);
        }

    }

    /**
     * @api {GET} /api/checkupdate checkUpdate
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 检查更新-checkUpdate
     * @apiDescription 检查更新
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"7Zry","result":{},"code":"miC6Z8"}
     */
    @RequestMapping(value = "/checkupdate", method = RequestMethod.GET)
    public Response<?> checkUpdate(HttpServletRequest req) {
        String edition = HttpOtherData.httpGetNewEdition();
        if (!StringUtils.isEmpty(edition)) {
            if (edition.equals("获取公告失败")) {
                return Response.success(2, req);
            } else {
                if (!edition.equals(PublicDataConf.EDITION)) {
                    return Response.success(0, req);
                } else {
                    return Response.success(1, req);
                }
            }
        } else {
            return Response.success(2, req);
        }
    }

    /**
     * @api {GET} /api/block block
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 禁言-block
     * @apiDescription 禁言
     * @apiParam (请求参数) {Number} uid 用户uid
     * @apiParam (请求参数) {Number} time 时间（1-720）
     * @apiParamExample 请求参数示例
     * uid=2800&time=175
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"tUInIsESzI","result":{},"code":"Le3H"}
     */
    @RequestMapping(value = "/block", method = RequestMethod.GET)
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


    /**
     * @api {GET} /api/del_block del_block
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 撤销禁言-del_block
     * @apiDescription 撤销禁言
     * @apiParam (请求参数) {Number} id 禁言编号
     * @apiParamExample 请求参数示例
     * id=1423
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"dKI1M6","result":{},"code":"EmtOdc"}
     */
    @RequestMapping(value = "/del_block", method = RequestMethod.GET)
    public Response<?> del_block(@RequestParam("id") long id, HttpServletRequest req) {
        short code = -1;
        code = HttpUserData.httpPostDeleteBlock(id);
        return Response.success(code, req);
    }


    /**
     * @api {GET} /api/blocks blocks
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 分页获取封禁列表-blocks
     * @apiDescription 分页获取封禁列表
     * @apiParam (请求参数) {Number} page 页码
     * @apiParamExample 请求参数示例
     * page=5095
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"1piR","result":{},"code":"SeE5vB3"}
     */
    @RequestMapping(value = "/blocks", method = RequestMethod.GET)
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


    /**
     * @api {GET} /api/setExport setExport
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 导出json配置文件-setExport
     * @apiDescription 导出json配置文件
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"Z8bzHv4","result":{},"code":"9yjaltwsf"}
     */
    @RequestMapping(value = "/setExport", method = RequestMethod.GET)
    public Response<?> setExport(HttpServletRequest req) {
        boolean flag = JsonFileTools.createJsonFile(PublicDataConf.centerSetConf.toJson());
        if (flag) {
            return Response.success(0, req);
        } else {
            return Response.success(1, req);
        }
    }

    /**
     * @api {POST} /api/setImport setImport
     * @apiVersion 1.0.0
     * @apiGroup ApiController
     * @apiName 导入设置-setImport
     * @apiDescription 导入设置(json文件)
     * @apiParam (请求参数) {Object} file 要上传json文件
     * @apiParamExample 请求参数示例
     * file=null
     * @apiSuccess (响应结果) {String} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} result
     * @apiSuccess (响应结果) {Number} timestamp
     * @apiSuccessExample 响应结果示例
     * {"msg":"JFPOd0","result":{},"code":"RKrqnj8x"}
     */
    @RequestMapping(value = "/setImport", method = RequestMethod.POST)
    public Response<?> setImport(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        if (!file.getResource().getFilename().endsWith(".json")) {
            return Response.success(2, req);
        }
        String jsonString = new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            CenterSetConf centerSetConf = FastJsonUtils.parseObject(jsonString, CenterSetConf.class);
            if (centerSetConf != null) {
                PublicDataConf.centerSetConf = centerSetConf;
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
}
