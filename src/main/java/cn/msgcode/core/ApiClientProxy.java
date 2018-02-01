package cn.msgcode.core;

import cn.msgcode.annotation.ApiClient;
import cn.msgcode.bean.ApiRequest;
import cn.msgcode.utils.YamlUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 仓库代理
 * Created by jl on 17-7-6.
 */
public class ApiClientProxy implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ApiClientProxy.class);

    public Object newProxyInstance(Class<?> interfaceClass) throws IllegalArgumentException {
       return Enhancer.create(interfaceClass, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ApiClient apiClient = method.getDeclaringClass().getAnnotation(ApiClient.class);
        if(null == apiClient) {
            logger.info("missing @ApiClient");
            return null;
        }

        String methodPath = method.getName();

        String module = apiClient.module();
        String serviceId = apiClient.serviceId();

        if(serviceId.contains("${")) {
            String valueKey = serviceId.replace("${", "")
                    .replace("}", "");

            serviceId = YamlUtil.getPropertyAsString(valueKey);
        }

        ApiRequest request = new ApiRequest();
        Map<String, Object> params = new HashMap<>();
        String[] paramNames = ApiMapper.methodParamNames(method.getName());

        for(int i = 0; i < paramNames.length; i++) {
            System.out.println(paramNames[i] + " - " + objects[i]);
            params.put(paramNames[i], objects[i]);
        }
        request.setData(params);
        request.setId(UUID.randomUUID().toString());

        ApiClientExtend apiClientExtend = SpringUtil.getBean(ApiClientExtend.class);
        String url = "http://".concat(serviceId)
                .concat("/remote/api/")
                .concat(module)
                .concat(File.separator)
                .concat(methodPath);
        return apiClientExtend.exec(url, request, method.getReturnType());
    }
}
