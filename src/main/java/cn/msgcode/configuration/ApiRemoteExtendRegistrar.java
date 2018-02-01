package cn.msgcode.configuration;

import cn.msgcode.annotation.ApiClient;
import cn.msgcode.core.ApiClientFactoryBean;
import cn.msgcode.utils.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiRemoteExtendRegistrar implements ImportBeanDefinitionRegistrar {

    private Logger logger = LoggerFactory.getLogger(ApiRemoteExtendRegistrar.class);

    ApiRemoteExtendRegistrar() {

    }

    protected Class<? extends Annotation> getAnnotation() {
        return EnableApiRemoteExtend.class;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableApiRemoteExtend.class.getName()));
        String basePackage = attributes.getString("basePackage");
        //扫描包里类
        List<Class<?>> apiClientClasses = Scanner.getClasses(basePackage);

        for (Class<?> apiClientClass : apiClientClasses) {
            registerApiClient(apiClientClass, beanDefinitionRegistry);
        }

        logger.info(" - 注册ApiClient完成");
    }

    private void registerApiClient(Class<?> apiClientClass, BeanDefinitionRegistry registry) {
        ApiClient apiClient = apiClientClass.getAnnotation(ApiClient.class);
        if(null != apiClient) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(ApiClientFactoryBean.class);
            beanDefinition.setLazyInit(true);
            beanDefinition.getPropertyValues().addPropertyValue("apiClientClass", apiClientClass);
            String beanName = getBeanName(apiClientClass.getSimpleName());
            registry.registerBeanDefinition(beanName, beanDefinition);
            logger.info(" - {} - load api remote client success", apiClientClass.getName());
        }
    }

    private String getBeanName(String text) {
        String firstLetter = text.substring(0, 1);
        String firstLow = firstLetter.toLowerCase();
        text = text.replaceFirst(firstLetter, firstLow);
        return text;
    }
}
