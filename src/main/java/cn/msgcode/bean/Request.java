package cn.msgcode.bean;

import java.io.Serializable;

/**
 * 
 * <b><code>Request</code></b>
 * <p>
 * Comment here.
 * </p>
 * <b>Creation Time:</b> 2016年10月10日 下午2:28:45
 * 
 * @author abin.yao
 * @since qlchat 1.0
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String sign;

    private Long timestamp;
    
    private String encrypt = "md5";

    private String ip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
