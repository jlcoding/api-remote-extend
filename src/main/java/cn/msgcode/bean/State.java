package cn.msgcode.bean;


/**
 * 
 * <b><code>State</code></b>
 * <p>
 * Comment here.
 * </p>
 * <b>Creation Time:</b> 2016年10月10日 下午2:29:41
 * 
 * @author abin.yao
 * @since qlchat 1.0
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
