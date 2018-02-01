# API REMOTEC EXTEND (还没想到名字)


>##前言

    你在用Feign时是否有遇到传参十分麻烦. 传多个参数又得加上 @RequestParam 和 @RequestBody 
    基于SpringBoot+SpringCloud FeignClient的替代品, 
    约定优于配置
    
    
>##使用教程

####用法类似Dubbo 
- 基本配置
    1. 在SpringBoot的启动类加上配置
```
@EnableApiRemoteExtend(basePackage = "cn.msgcode.service.v1")
```

- 创建远程调用接口 
    1. @ApiClient用于标记调用客户端 
    2. serviceId为注册到eureka的服务名
    3. module定义模块
```
    @ApiClient(serviceId = "wechat-service-test", module = "test")
    public interface HelloService {
        Object test(String param1, String param2);
    }
```

- 远程接口实现远程调用接口(没有特别作用,单纯为了保证方法,参数类型和返回值类型一样)
    1. @Service单纯注册成Spring Bean
    2. @ApiModule(name = "test") 标记为远程接口 name和 @ApiClient的module相同
```
    @Service
    @ApiModule(name = "test")
    public class TestServiceImpl implements HelloService {
    
        private Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    
        @Override
        public String test(String param1, String param2) {
            logger.info("show test param1:{} param2:{}", param1, param2);
            return param1 + param2;
        }
    }
```

>##后续版本
- 后续版本有几个目标:
   1. 修复各类bug
   2. 增强错误提示
   3. 增强安全性
   4. 增强可定制化配置