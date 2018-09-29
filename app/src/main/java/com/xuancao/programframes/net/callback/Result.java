package com.xuancao.programframes.net.callback;


public class Result extends Error {

    public String result;
    public int retcode;
    public String retmsg;

    public Result(){
    }

    public Result(int code, String msg){
        retcode = code;
        retmsg = msg;
    }

    public String getMessage() {
        return retmsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setMessage(String retmsg) {
        this.retmsg = retmsg;
    }
}
