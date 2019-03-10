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
public class ApiRemoteResponse extends Response implements Serializable {

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

    /**
     * Add object to data
     */
    public ApiRemoteResponse addObjectToData(Object obj) throws Exception {
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

    public ApiRemoteResponse addValueToData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, value);
        return this;
    }

    public ApiRemoteResponse addPageToData(Page page) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put("page", page);
        return this;
    }

    public <T> ApiRemoteResponse addListToData(List<T> list) {
        return this.addListToData("dataList", list);
    }

    public <T> ApiRemoteResponse addListToData(String key, List<T> list) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.data.put(key, list);
        return this;
    }

    public static ApiRemoteResponse ok() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.OK_WITHOUT_MSG);
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiRemoteResponse ok(Map<String, Object> data) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.OK_WITHOUT_MSG);
        response.setData(data);
        return response;
    }

    public static ApiRemoteResponse okWithDefaultMsg() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.OK);
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiRemoteResponse ok(String msg) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(new State(StateCode.OK.getCode(), msg));
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static ApiRemoteResponse error() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.SERVER_ERROR);
        return response;
    }

    public static ApiRemoteResponse error(String msg) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(new State(StateCode.SERVER_ERROR.getCode(), msg));
        return response;
    }

    public static ApiRemoteResponse error(int code, String msg) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(new State(code, msg));
        return response;
    }

    public static ApiRemoteResponse error(State state) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(state);
        return response;
    }

    public static ApiRemoteResponse notExist() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.OBJECT_NOT_EXIST);
        return response;
    }

    public static ApiRemoteResponse reqContentError() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.REQUEST_CONTENT_ERROR);
        return response;
    }

    public static ApiRemoteResponse invalidUid() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.INVALID_UID);
        return response;
    }

    public static ApiRemoteResponse invalidSignError() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.INVALID_SIGH_ERROR);
        return response;
    }

    public static ApiRemoteResponse missIdError() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.MISS_ID_ERROR);
        return response;
    }

    public static ApiRemoteResponse sessionExist() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.SESSION_EXIST);
        return response;
    }

    public static ApiRemoteResponse sessionNotExist() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.SESSION_NOT_EXIST);
        return response;
    }

    public static ApiRemoteResponse sessionHasLogin() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.SESSION_HAS_LOGIN);
        return response;
    }

    public static ApiRemoteResponse sessionDoesNotCorrespondToUser() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.SESSION_USER_NOT_CORRESPOND);
        return response;
    }

    public static ApiRemoteResponse missParamError() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.MISS_PARAM_ERROR);
        return response;
    }

    public static ApiRemoteResponse missParamError(String message) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        State state = StateCode.MISS_PARAM_ERROR;
        state.setMsg(message);
        response.setState(state);
        return response;
    }

    public static ApiRemoteResponse invalidSessionParam() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.INVALID_SESSION_PARAM);
        return response;
    }

    public static ApiRemoteResponse apiNotExist() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.API_NOT_EXIST);
        return response;
    }

    public static ApiRemoteResponse invalidParamLenth() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.INVALID_PARAM_LENGTH);
        return response;
    }

    public static ApiRemoteResponse requestFrequencyLimit() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.REQUEST_FREQUENCY_LIMIT);
        return response;
    }

    public static ApiRemoteResponse invalidParam() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.INVALID_PARAM);
        return response;
    }

    public static ApiRemoteResponse invalidParam(String msg) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        State state = StateCode.INVALID_PARAM;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    public static ApiRemoteResponse noAuth() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.NO_AUTH);
        return response;
    }

    public static ApiRemoteResponse noAuth(String msg) {
        ApiRemoteResponse response = new ApiRemoteResponse();
        State state = StateCode.NO_AUTH;
        state.setMsg(msg);
        response.setState(state);
        return response;
    }
    
    public static ApiRemoteResponse responseTimeout() {
        ApiRemoteResponse response = new ApiRemoteResponse();
        response.setState(StateCode.RESPONSE_TIMEOUT);
        return response;
    }
}
