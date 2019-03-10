package cn.msgcode.bean;

/**
 * @author jl
 */
public final class StateCode {

    // 全局状态码
    public static final State OK = new State(0, "请求成功");

    public static final State OK_WITHOUT_MSG = new State(0, "操作成功");

    public static final State SERVER_ERROR = new State(10001, "服务端正忙，请稍后重试");

    public static final State REQUEST_CONTENT_ERROR = new State(10002, "内容格式错误");

    public static final State INVALID_SIGH_ERROR = new State(10003, "无效签名");

    public static final State MISS_ID_ERROR = new State(10004, "请求参数缺少id");

    public static final State RESPONSE_TIMEOUT = new State(10005, "接口超时");

    public static final State API_NOT_EXIST = new State(10006, "接口不存在");

    public static final State OBJECT_NOT_EXIST = new State(10014, "对象不存在");

    public static final State FAVICON_INVALID_REQUEST = new State(10007, "favicon非法请求");

    public static final State REQUEST_FREQUENCY_LIMIT = new State(10008, "请求频率限制");

    public static final State PROCESSING = new State(10009, "系统正在处理，请耐心等待");

    public static final State NETWORK_ERROR = new State(10010, "网络发生波动，稍等一下再发起请求");

    public static final State ENTITY_EXIST = new State(10011, "该实体已存在");

    public static final State ENTITY_NOT_EXIST = new State(10012, "该实体不存在");

    public static final State NO_AUTH = new State(10013, "没有权限");

    public static final State NO_VIP = new State(10014, "没有权限");

    // etag
    public static final State CACHE_NOT_CHANGE = new State(30001, "缓存数据未变化");

    public static final State CACHE_CHANGED = new State(30002, "缓存数据发生变化");

    // user and page in data error
    public static final State MISS_PARAM_ERROR = new State(20001, "输入的内容不合法");

    public static final State INVALID_UID = new State(20002, "非法的uid");

    public static final State INVALID_PARAM_LENGTH = new State(20009, "参数长度不合法");

    // session
    public static final State SESSION_EXIST = new State(20003, "SESSION已存在");

    public static final State SESSION_NOT_EXIST = new State(20004, "SESSION不存在");

    public static final State SESSION_HAS_LOGIN = new State(20005, "您的账号在在别处登录，请重新登陆");


    public static final State INVALID_SESSION_PARAM = new State(20006, "SESSION参数缺失");

    public static final State INVALID_PARAM = new State(20007, "无效参数");

    public static final State LOGIN_ERROR = new State(20008, "登录出现错误");

    public static final State USER_BANNED = new State(20010, "用户已被禁用");

    public static final State ORDER_NO_RESULT = new State(20011, "支付成功还未回调");

    public static final State SESSION_USER_NOT_CORRESPOND =new State(20011, "非法访问, 原因是session与用户不对应");

    //极光短信相关
    public static final State INVALID_CODE = new State(40001, "验证码已经失效");

    public static final State INVALID_USER_CODE = new State(40002, "无法获取公众号信息");

    //业务上的限制
    public static final State FORBID_VISIT = new State(50001, "为响应国家监管政策，更进一步保证直播平台的内容安全\n凌晨0：00-7:00暂不支持内容发布");
    
    public static final State TOPIC_ENDED = new State(50002, "直播已经结束");
    
    public static final State BLACK_LIST = new State(50003, "你的访问被该页面拒绝了");
    
    public static final State CODE_USED = new State(50004, "该CODE已被使用");
    
    public static final State INVALID_APP_CODE = new State(60001,"code非法");

    public static final State SHOULD_AUTH_FIRST = new State(60002, "请先报名");

    public static final State INVITE_CODE_EXCEED = new State(70001, "邀请码已经用完");
}
