package cn.msgcode.core;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * api map
 * Created by jl on 18-1-28.
 */
public class ApiMapper {

    private static Map<String, Class<?>> apiModuleMap = new HashMap<>();

    private static Map<String, Class<?>[]> methodParamTypesMap = new HashMap<>();

    private static Map<Class<?>, String> classMethodsMap = new HashMap<>();

    private static Map<String, String[]> methodParamNamesMap = new HashMap<>();

    public static void apiModule(String module, Class<?> serviceClass) {
        apiModuleMap.put(module, serviceClass);
    }

    public static Class<?> apiModule(String module) {
        return apiModuleMap.get(module);
    }
    public static String getMap() {
        return JSON.toJSONString(apiModuleMap);
    }

    public static void methodParamTypes(String method, Class<?>[] paramterTypes) {
        methodParamTypesMap.put(method, paramterTypes);
    }

    public static Class<?>[] methodParamTypes(String method) {
        return methodParamTypesMap.get(method);
    }

    public static void classMethods(Class<?> serviceClass, String method) {
        classMethodsMap.put(serviceClass, method);
    }

    public static String classMethods(Class<?> serviceClass) {
        return classMethodsMap.get(serviceClass);
    }

    public static void methodParamNames(String method, String[] names) {
        methodParamNamesMap.put(method, names);
    }

    public static String[] methodParamNames(String method) {
        return methodParamNamesMap.get(method);
    }
}
