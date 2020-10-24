package xyz.acproject.danmuji.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.login_data.LoginData;
import xyz.acproject.danmuji.entity.login_data.Qrcode;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.returnJson.Response;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.utils.QrcodeUtils;

/**
 * @ClassName WebController
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:50
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Controller
public class WebController {
	@Autowired
	private SetService checkService;
	@Autowired
	private ClientService clientService;

	@RequestMapping(value = { "/", "index" })
	public String index(HttpServletRequest req, Model model) {
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			if (req.getSession().getAttribute("status") == null) {
				req.getSession().setAttribute("status", "login");
			}
		}
		model.addAttribute("ANAME", PublicDataConf.ANCHOR_NAME);
		model.addAttribute("EDITION",PublicDataConf.EDITION);
		model.addAttribute("ROOMID", PublicDataConf.ROOMID);
		model.addAttribute("POPU", PublicDataConf.ROOM_POPULARITY);
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
	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public void qrcode(HttpServletRequest req, HttpServletResponse resp, @RequestParam("url") String url) {
		if (req.getSession().getAttribute("status") != null)
			return;
		QrcodeUtils.creatRrCode(url, 140, 140, resp);
	}

	@ResponseBody
	@RequestMapping(value = "/qrcodeUrl", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Response<?> qrcodeUrl(HttpServletRequest req) {
		if (req.getSession().getAttribute("status") != null)
			return null;
		Qrcode qrcode = HttpUserData.httpGetQrcode();
		req.getSession().setAttribute("auth", qrcode.getOauthKey());
		return Response.success(qrcode.getUrl(), req);
	}

	@ResponseBody
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String loginCheck(HttpServletRequest req) {
		if (req.getSession().getAttribute("status") != null)
			return "";
		JSONObject jsonObject = null;
		String oauthKey = (String) req.getSession().getAttribute("auth");
		LoginData loginData = new LoginData();
		loginData.setOauthKey(oauthKey);
		String jsonString = HttpUserData.httpPostCookie(loginData);
		jsonObject = JSONObject.parseObject(jsonString);
		if (jsonObject != null) {
			if (jsonObject.getBoolean("status")) {
				checkService.init(1);
				if (PublicDataConf.USER != null) {
					req.getSession().setAttribute("status", "login");
				}
			}
		}
		return jsonString;
	}

	@ResponseBody
	@RequestMapping(value = "/connectRoom", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> connectRoom(HttpServletRequest req, @RequestParam("roomid") Long roomid) {
		boolean flag = false;
		if (null == PublicDataConf.webSocketProxy || !PublicDataConf.webSocketProxy.isOpen()) {
			try {
				clientService.startConnService(roomid);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(PublicDataConf.ROOMID!=null) {
				PublicDataConf.centerSetConf.setRoomid(PublicDataConf.ROOMID);
				PublicDataConf.ROOMID_SAFE=PublicDataConf.ROOMID;
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
	@RequestMapping(value = "/disconnectRoom", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> disconnectRoom(HttpServletRequest req) {
		boolean flag = false;
		flag = clientService.closeConnService();
		return Response.success(flag, req);
	}

	@ResponseBody
	@RequestMapping(value = "/connectCheck", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
	@RequestMapping(value = "/heartBeat", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> heartBeat(HttpServletRequest req) {
		return Response.success(PublicDataConf.ROOM_POPULARITY, req);
	}

	@ResponseBody
	@RequestMapping(value = "/getSet", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> getSet(HttpServletRequest req) {
		return Response.success(PublicDataConf.centerSetConf, req);
	}

	@ResponseBody
	@RequestMapping(value = "/sendSet", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
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
	@ResponseBody
	@RequestMapping(value = "/getIp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> getIp(HttpServletRequest req) {
		String ip =HttpOtherData.httpGetIp();
		if(!StringUtils.isEmpty(ip)) {
			return Response.success(ip, req);
		}else {
			return Response.success(null, req);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/checkupdate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Response<?> checkUpdate(HttpServletRequest req) {
		String edition = HttpOtherData.httpGetNewEdition();
		if(!StringUtils.isEmpty(edition)) {
			 if(edition.equals("获取公告失败")) {
				 return Response.success(2, req);
			 }else {
				 if(!edition.equals(PublicDataConf.EDITION)) {
					 return Response.success(0, req);
				 }else {
					 return Response.success(1, req);
				 }	
			 }
		}else {
			return Response.success(2, req);
		}	
	}
	@ResponseBody
	@RequestMapping(value="/block",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public Response<?> block(@RequestParam("uid")long uid,@RequestParam("time")short time,HttpServletRequest req){
		short code = 0;
		if(time>720&&time<=0) {
			//required time error
			code =2;
			return Response.success(code, req);
		}
		code = HttpUserData.httpPostAddBlock(uid, time);
		return Response.success(code, req);
	}
}
