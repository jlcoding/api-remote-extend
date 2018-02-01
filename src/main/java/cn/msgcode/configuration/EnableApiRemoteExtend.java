package cn.msgcode.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 自动配置仓库
 * Created by jl on 17-7-11.
 */
@Order(value = -Integer.MAX_VALUE)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ApiRemoteExtendRegistrar.class})
public @interface EnableApiRemoteExtend {

    String basePackage() default "";

}
