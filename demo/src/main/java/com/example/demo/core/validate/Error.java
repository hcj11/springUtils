package com.example.demo.core.validate;

public class Error  {
    private String msg;

    public Error() {

    }

    public Error(String message) {
        this.msg=message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Error{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
