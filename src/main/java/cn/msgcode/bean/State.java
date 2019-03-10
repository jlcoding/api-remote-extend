package cn.msgcode.bean;


/**
 * @author jl
 */
public class State {

    private int code;

    private String msg;

    public State() {}


    public State(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
