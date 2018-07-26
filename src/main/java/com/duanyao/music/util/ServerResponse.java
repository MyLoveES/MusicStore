package com.duanyao.music.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ServerResponse<T> implements Serializable {
    private  int status;

    private String msg;

    private T data;

    public ServerResponse() {
    }

    private ServerResponse (int status){

        this.status=status;

    }
    private ServerResponse (int  status, String msg){

        this.status=status;

        this.msg=msg;

    }

    private ServerResponse (int status,String  msg,T data){

        this.status=status;

        this.msg=msg;

        this.data=data;

    }

    private ServerResponse(int  status,T data){

        this.status=status;

        this.data=data;

    }

    @JsonIgnore
    public boolean  isSuccess(){

        return this.status==ResponseCode.SUCCESS.getCode();

    }

    public int  getStatus(){

        return  status;

    }

    public T  getData(){

        return  data;

    }

    public String  getMsg(){

        return  msg;

    }

    public ServerResponse  createBySuccess(){

        return  new  ServerResponse(ResponseCode.SUCCESS.getCode());

    }
    public  ServerResponse createBySuccessMsg(String msg ){

        return  new
                ServerResponse(ResponseCode.SUCCESS.getCode(),msg);

    }

    public  ServerResponse createBySuccess(T data){

        return  new
                ServerResponse(ResponseCode.SUCCESS.getCode(),data);

    }

    public  ServerResponse createBySuccess(String msg , T  data){

        return  new
                ServerResponse(ResponseCode.SUCCESS.getCode(), msg, data);

    }

    public  ServerResponse createByError(){

        return  new
                ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMsg());

    }

    public  ServerResponse createByErrorMsg(String msg){

        return new
                ServerResponse(ResponseCode.ERROR.getCode(),msg);

    }

    public  ServerResponse createByError(T data){

        return  new
                ServerResponse(ResponseCode.ERROR.getCode(),data);

    }

    public  ServerResponse createByError(String msg , T  data){

        return  new
                ServerResponse(ResponseCode.ERROR.getCode(), msg, data);

    }

    public  ServerResponse createByError(int  code,String  msg){

        return  new  ServerResponse( code,msg);

    }


}
