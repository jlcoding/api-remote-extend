package cn.msgcode.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.debugger.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Api请求载体
 */
public class ApiRequest extends Request implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(ApiRequest.class);

    private static final long serialVersionUID = 1L;

    private Map<String, Object> data;
    
    private String etag;

    private String userAgent;
    
    private String remoteAddr;
    
    private Client client = new Client();

    private String format;

    private String callbackParam;

    private String userCode; //公众号唯一标识

    private String appId;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
   
    public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getDataParamAsString(String name) {
        return getDataParam(name) != null ? getDataParam(name).toString() : null;
    }

    public Boolean getDataParamAsBool(String name) {
        return !StringUtils.isBlank(getDataParamAsString(name)) ? Boolean.parseBoolean(getDataParam(name).toString()) : null;
    }

    public Integer getDataParamAsInt(String name) {
        return !StringUtils.isBlank(getDataParamAsString(name)) ?
                new BigDecimal(getDataParamAsString(name)).intValue() : null;
    }

    public Float getDataParamAsFloat(String name) {
        return !StringUtils.isBlank(getDataParamAsString(name)) ?
                new BigDecimal(getDataParamAsString(name)).floatValue() : null;
    }

    public Date getDateParamAsDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf.parse(getDataParamAsString(time));
        } catch (ParseException e) {
            logger.error("时间类型转换失败", e);
            return null;
        }
    }

    public Date getDateParamAsDateByTimestamp(String time) {
        try {
            return new Date(Long.valueOf(getDataParamAsString(time)));
        } catch (Exception e) {
            logger.error("时间类型转换失败", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getDataParamAsMap(String name) {
        Object o = this.getDataParam(name);
        return (o instanceof Map) ? (Map<String, Object>) o : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getDataParamAsType(String name, Class<T> clazz) {
        Object o = this.getDataParam(name);
        if (clazz.isInstance(o)) {
            return (T) o;
        } else if (o instanceof JSONObject) {
            try {
                return JSON.parseObject(((JSONObject) o).toJSONString(), clazz);
            } catch (Exception e) {
                logger.error("Get data param as type error", e);
                return null;
            }
        }
        return null;
    }

    public JSONObject getDataParamAsJSONObject(String name) {
        Object o = this.getDataParam(name);
        try {
            return (JSONObject) o;
        } catch (Exception e) {
            logger.error("Get data param as type error", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getDataParamAsList(String name, Class<T> clazz) {
        Object o = this.getDataParam(name);
        List<T> list = new ArrayList<T>();
        if (clazz.isInstance(o)) {
            list.add((T) o);
            return list;
        } else if (o instanceof JSONArray) {
            try {
                JSONArray arrs = (JSONArray) o;
                for (Iterator<Object> iter = arrs.iterator(); iter.hasNext();) {
                    list.add(JSON.parseObject(JSON.toJSONString(iter.next()), clazz));
                }
                return list;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public Object getDataParam(String name) {
        if (data == null || name == null) {
            return null;
        }
        return data.get(name);
    }

    public List<String> getParamStringList(String param) {
        return Arrays.asList(this.getDataParamAsString(param).split("\\,"));
    }

    public List<Integer> getParamIntList(String param) {
        List<String> params = Arrays.asList(this.getDataParamAsString(param).split("\\,"));
        if (null == params || params.size() == 0) {
            return null;
        }
        List<Integer> result = new ArrayList<Integer>();
        for (String p : params) {
            result.add(Integer.valueOf(p));
        }
        return result;
    }


    public Long getDataParamAsLong(String name) {
        return StringUtils.isNotEmpty(getDataParamAsString(name)) ? Long.valueOf(getDataParamAsString(name)) : null;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCallbackParam() {
        return callbackParam;
    }

    public void setCallbackParam(String callbackParam) {
        this.callbackParam = callbackParam;
    }

    public String getUserCode() {
        return userCode;
    }

    public ApiRequest setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
