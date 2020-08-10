package xyz.acproject.danmuji.returnJson;

/**
 * @ClassName ResponseCode
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:19
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public enum ResponseCode {
	 normal("200", "Successful.", "操作成功"),
	    syserror("400", "System is busy!", "系统繁忙"),
	    accountnotexist("101", "User does not exist.", "用户不存在"),
	    accountremoved("102", "User has been disabled.", "用户已停用"),
	    passworderror("103", "Account or password error.", "账号或密码错误"),
	    noroleforaccount("104", "User has no role.", "用户没有任何的角色"),
	    propertyUsed("105", "This value has been taken.", "该值已被占用"),
	    infonotmatch("106", "Information do not match", "信息不匹配"),
	    choiceUserError("107", "Please select the user", "请选择用户"),
	    fiveresult("108", "已经有五条记录", "已经有五条记录"),
	    paramserror("301", "Requested parameter error!", "请求参数错误"),
	    tokenerror("302", "No Token!", "无token"),
	    tokenfail("303", "Token validation failed!", "token验证失败"),
	    adminError("304", "permission restrictions!", "超级管理员权限限制"),
	    resourceError("305", "No permission", "没有该功能权限"),
	    invalidlink("306", "Invalid link", "无效的链接"),
	    filetimeout("316", "File was invalid!", "文件已失效"),
	    filenoexist("317", "File doesn't exist!", "文件不存在"),
	    providerNotExist("501", "Provider does not exist.", "供应商不存在"),
	    serviceNotExist("502", "Service does not exist.", "服务不存在"),
	    kpiNotExist("503", "KPI does not exist.", "KPI不存在");

	    private String code;
	    private String msg;
	    private String cnMsg;

	    private ResponseCode(String code, String msg, String cnMsg) {
	        this.code = code;
	        this.msg = msg;
	        this.cnMsg = cnMsg;
	    }

	    public String getCode() {
	        return this.code;
	    }

	    public String getMsg() {
	        return this.msg;
	    }

	    public String getCnMsg() {
	        return this.cnMsg;
	    }

	    public String toString() {
	        return this.code;
	    }
}

