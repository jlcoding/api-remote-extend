package cn.msgcode.core;

import cn.msgcode.annotation.ApiModule;
import cn.msgcode.utils.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * register for api handler
 * Created by jl on 18-1-28.
 */
public class ApiRemoteRegister {

    private static Logger logger = LoggerFactory.getLogger(ApiRemoteRegister.class);

    public static void init(String basePackage) {
        List<Class<?>> serviceClasses = Scanner.getClasses(basePackage);

        for(Class<? > serviceClass : serviceClasses){
            String module = null;
            ApiModule apiModule = serviceClass.getAnnotation(ApiModule.class);
            if(null != apiModule) {
                module = apiModule.name();
                ApiMapper.apiModule(module, serviceClass);
                for(Method method : serviceClass.getMethods()) {

                    //add param types
                    ApiMapper.methodParamTypes(method.getName(), method.getParameterTypes());

                    //add param names
                    List<String> paramNameList = new ArrayList<>();
                    Parameter[] parameters = method.getParameters();
                    for(Parameter parameter : parameters) {
                        paramNameList.add(parameter.getName());
                    }
                    String[] paramNames = new String[paramNameList.size()];
                    ApiMapper.methodParamNames(method.getName(), paramNameList.toArray(paramNames));
                }

                logger.info("loading {} success", serviceClass.getName());
            }else {
                logger.error("| missing @ApiModule annotation");
            }
        }
    }
}
