package cn.msgcode.core;

import org.springframework.beans.factory.FactoryBean;

public class ApiClientFactoryBean implements FactoryBean {

    private Class<?> apiClientClass;

    public Class<?> getApiClientClass() {
        return apiClientClass;
    }

    public void setApiClientClass(Class<?> apiClientClass) {
        this.apiClientClass = apiClientClass;
    }

    @Override
    public Object getObject() throws Exception {
        return new ApiClientProxy().newProxyInstance(this.apiClientClass);
    }

    @Override
    public Class getObjectType() {
        return apiClientClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}