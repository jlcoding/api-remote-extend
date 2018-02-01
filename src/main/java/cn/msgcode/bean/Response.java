package cn.msgcode.bean;

import java.io.Serializable;

/**
 * 
 * <b><code>Response</code></b>
 * <p>
 * Comment here.
 * </p>
 * <b>Creation Time:</b> 2016年10月10日 下午2:28:53
 * 
 * @author abin.yao
 * @since qlchat 1.0
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private State state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
