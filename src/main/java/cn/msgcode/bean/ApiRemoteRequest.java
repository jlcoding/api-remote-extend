package cn.msgcode.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Api请求载体
 */
public class ApiRemoteRequest extends Request implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(ApiRemoteRequest.class);

    private static final long serialVersionUID = 1L;

    private Map<String, Object> data;

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
