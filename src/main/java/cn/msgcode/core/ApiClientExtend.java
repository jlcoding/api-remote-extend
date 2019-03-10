package cn.msgcode.core;


import cn.msgcode.bean.ApiRemoteRequest;
import cn.msgcode.exception.RemoteMethodInvokeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ApiClientExtend {

    @Resource
    private RestTemplate restTemplate;

    public Object exec(String url, ApiRemoteRequest request, Class<?> returnType) throws URISyntaxException, RemoteMethodInvokeException {
        URI uri = new URI(url);
        ResponseEntity responseEntity = restTemplate.postForEntity(uri, JSON.toJSONString(request), returnType);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        }else {
            throw new RemoteMethodInvokeException("调用远程接口失败");
        }
    }

}
