package cn.msgcode.bean;

import java.io.Serializable;

/**
 * @author jl
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
