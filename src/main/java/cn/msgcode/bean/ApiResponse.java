package cn.msgcode.bean;


import sun.jvm.hotspot.debugger.Page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Api回复载体
 */
public class ApiResponse extends Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> data;

    private String etag;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        if(null != this.data) {
            this.data.putAll(data);
        }else {
            this.data = data;
        }
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * Add object to data
     *
     * @since qlchat 1.0
     */
    public ApiResponse addObjectToData(Object obj) throws Exception {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        for (Field f : obj.getClass().getDeclaredFields()) {
            if ("this$0".equals(f.getName())) {
                continue;
            }
            f.setAccessible(true);
            this.data.put(f.getName(), f.get(obj));
        }
        return this;
    }

    public ApiResponse addValueToData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, value);
        return this;
    }

    public ApiResponse addPageToData(Page page) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put("page", page);
        return this;
    }

    public <T> ApiResponse addListToData(List<T> list) {
        return this.addListToData("dataList", list);
    }

    public <T> ApiResponse addListToData(String key, List<T> list) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.data.put(key, list);
        return this;
    }

    public static ApiResponse ok() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.OK_WITHOUT_MSG);
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiResponse ok(Map<String, Object> data) {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.OK_WITHOUT_MSG);
        response.setData(data);
        return response;
    }

    public static ApiResponse okWithDefaultMsg() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.OK);
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiResponse ok(String msg) {
        ApiResponse response = new ApiResponse();
        response.setState(new State(StateCode.OK.getCode(), msg));
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiResponse error() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SERVER_ERROR);
        return response;
    }

    public static ApiResponse error(String msg) {
        ApiResponse response = new ApiResponse();
        response.setState(new State(StateCode.SERVER_ERROR.getCode(), msg));
        return response;
    }

    public static ApiResponse error(int code, String msg) {
        ApiResponse response = new ApiResponse();
        response.setState(new State(code, msg));
        return response;
    }

    public static ApiResponse error(State state) {
        ApiResponse response = new ApiResponse();
        response.setState(state);
        return response;
    }

    public static ApiResponse notExist() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.OBJECT_NOT_EXIST);
        return response;
    }

    public static ApiResponse reqContentError() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.REQUEST_CONTENT_ERROR);
        return response;
    }

    public static ApiResponse invalidUid() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_UID);
        return response;
    }

    public static ApiResponse invalidSignError() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_SIGH_ERROR);
        return response;
    }

    public static ApiResponse missIdError() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.MISS_ID_ERROR);
        return response;
    }

    public static ApiResponse sessionExist() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SESSION_EXIST);
        return response;
    }

    public static ApiResponse sessionNotExist() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SESSION_NOT_EXIST);
        return response;
    }

    public static ApiResponse sessionHasLogin() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SESSION_HAS_LOGIN);
        return response;
    }

    public static ApiResponse sessionDoesNotCorrespondToUser() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SESSION_USER_NOT_CORRESPOND);
        return response;
    }

    public static ApiResponse missParamError() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.MISS_PARAM_ERROR);
        return response;
    }

    public static ApiResponse missParamError(String message) {
        ApiResponse response = new ApiResponse();
        State state = StateCode.MISS_PARAM_ERROR;
        state.setMsg(message);
        response.setState(state);
        return response;
    }

    public static ApiResponse invalidSessionParam() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_SESSION_PARAM);
        return response;
    }

    public static ApiResponse apiNotExist() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.API_NOT_EXIST);
        return response;
    }

    public static ApiResponse invalidParamLenth() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_PARAM_LENGTH);
        return response;
    }

    public static ApiResponse requestFrequencyLimit() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.REQUEST_FREQUENCY_LIMIT);
        return response;
    }

    public static ApiResponse userBanned() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.USER_BANNED);
        return response;
    }

    public static ApiResponse invalidCode() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_CODE);
        return response;
    }

    public static ApiResponse invalidUserCode() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_USER_CODE);
        return response;
    }

    public static ApiResponse loginError() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.LOGIN_ERROR);
        return response;
    }

    public static ApiResponse forbidVisit() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.FORBID_VISIT);
        return response;
    }

    public static ApiResponse invalidParam() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.INVALID_PARAM);
        return response;
    }

    public static ApiResponse invalidParam(String msg) {
        ApiResponse response = new ApiResponse();
        State state = StateCode.INVALID_PARAM;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    public static ApiResponse entityNotExist() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.ENTITY_NOT_EXIST);
        return response;
    }

    public static ApiResponse entityNotExist(String msg) {
        ApiResponse response = new ApiResponse();
        State state = StateCode.ENTITY_NOT_EXIST;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    public static ApiResponse noAuth() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.NO_AUTH);
        return response;
    }

    public static ApiResponse noAuth(String msg) {
        ApiResponse response = new ApiResponse();
        State state = StateCode.NO_AUTH;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    public static ApiResponse noVip(String msg) {
        ApiResponse response = new ApiResponse();
        State state = StateCode.NO_VIP;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }
    
    public static ApiResponse responseTimeout() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.RESPONSE_TIMEOUT);
        return response;
    }

    /**
     * 黑名单
     * @return ApiResponse
     */
    public static ApiResponse isBlack() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.BLACK_LIST);
        return response;
    }

    public static ApiResponse shouldAuthFirst() {
        ApiResponse response = new ApiResponse();
        response.setState(StateCode.SHOULD_AUTH_FIRST);
        return response;
    }
}
