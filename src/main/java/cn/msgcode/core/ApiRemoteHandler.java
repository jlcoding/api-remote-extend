package cn.msgcode.core;

import cn.msgcode.annotation.ApiInterceptor;
import cn.msgcode.annotation.ApiParam;
import cn.msgcode.bean.ApiRemoteRequest;
import cn.msgcode.bean.ApiRemoteResponse;
import cn.msgcode.utils.MethodUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * remote api handler
 * Created by jl on 18-1-28.
 */
@Import(SpringUtils.class)
@RestController
@RequestMapping("/remote/api")
public class ApiRemoteHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiRemoteHandler.class);

    @RequestMapping(value = "/{module}/{method}", produces = {"application/json;charset=UTF-8"})
    public Object handle(@PathVariable String module, @PathVariable String method,
                         @RequestBody String requestBody, HttpServletRequest req, HttpServletResponse res) {
        long startTime = System.nanoTime();
        logger.info("ip={}, module={}, method={}, request={}", getRemoteIP(req), module, method, requestBody);

        ApiRemoteRequest request = null;
        Object response = null;
        String id = null;

        try {
            if (StringUtils.isEmpty(requestBody)) {
                logger.error("JSON error, request body is blank, module={}, method={}",
                        module, method);
                return ApiRemoteResponse.reqContentError();
            }
            // transfer request string to Request object
            request = JSON.parseObject(requestBody, ApiRemoteRequest.class);
            request.setIp(getRemoteIP(req)); // Get real ip
            request.setUserAgent(req.getHeader("user-agent"));
            request.setRemoteAddr(String.valueOf(req.getSession().getAttribute("RemoteAddr")));

            id = request.getId();
            if (!StringUtils.isEmpty(id)) {
//                if (this.validSign(request)) {
                // invoke controller method by methodName
                Method callMethod = null;
                Object obj = null;

                Class<?> serviceClass = ApiMapper.apiModule(module);
                Class<?>[] paramterTypes = ApiMapper.methodParamTypes(method);
                obj = SpringUtils.getBean(serviceClass);
                callMethod = serviceClass.getMethod(method, paramterTypes);

                // response result
                ApiInterceptor apiInterceptor = callMethod.getAnnotation(ApiInterceptor.class);
                if (null != apiInterceptor) {
                    Class<?> interceptorClass = apiInterceptor.interceptorClass();
                    Object interceptorInstance = SpringUtils.getBean(interceptorClass);
                    String interceptorMethodName = apiInterceptor.method();
                    Method interceptorMethod = interceptorClass.getMethod(interceptorMethodName, ApiRemoteRequest.class);
                    response = interceptorMethod.invoke(interceptorInstance, request);
                    if (null != response) {
//                        response.setId(request.getId());
                        return response;
                    }
                }

                String[] paramNames = MethodUtil.getMethodParamNames(callMethod);
                List<Object> values = new ArrayList<>();
                for(String paramName : paramNames) {
                    Object value = request.getData().get(paramName);
                    values.add(value);
                }
                Object[] params = new Object[values.size()];
                params = values.toArray(params);
                response = callMethod.invoke(obj, params);

//                } else {
//                    response = ApiRemoteResponse.invalidSignError();
//                }
            } else {
                response = ApiRemoteResponse.missIdError();
            }
        } catch (NoSuchMethodException e) {
            logger.error(String.format("Api not exist error, module=%s, method=%s, request=%s", module, method, requestBody), e);
            response = ApiRemoteResponse.apiNotExist();
        } catch (JSONException e) {
            logger.error(String.format("JSON error, module=%s, method=%s, request=%s", module, method, requestBody), e);
            response = ApiRemoteResponse.reqContentError();
        } catch (Throwable t) {
            logger.error(String.format("Unexpected error, module=%s, method=%s, request=%s", module, method, requestBody), t);
            response = ApiRemoteResponse.error();
        }
//        response.setId(id);
        logger.info("module={}, method={}, total time spent is {}ms",
                module, method,
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

        return response;

    }

    /**
     * 从HttpServletRequest中获取远程IP
     */
    private static String getRemoteIP(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static Map<String, Object> getParamMap(Parameter[] parameters, Object[] args) {
        Map<String, Object> paramMap = new HashMap<>();
        for(int index = 0; index < parameters.length; index++) {
            String paramName = null;
            ApiParam apiParam = parameters[index].getAnnotation(ApiParam.class);

            if(null != apiParam) {
                paramName = apiParam.value();
            }else {
                paramName = parameters[index].getName();
            }
            paramMap.put(paramName, args[index]);
        }
        return paramMap;
    }

}
