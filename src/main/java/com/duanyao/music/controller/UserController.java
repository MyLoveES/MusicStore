package com.duanyao.music.controller;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.duanyao.music.model.User;
import com.duanyao.music.service.UserService;
import com.duanyao.music.util.GlobalConsts;
import com.duanyao.music.util.GlobalEnums;
import com.duanyao.music.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
//    @RequestParam(value = "name", required = true) String name,
//    @RequestParam(value = "password", required = true) String password,
//    @RequestParam(value = "email", required = true) String email
    public List<User> userList() {
        return userService.getUserList();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User userProfile(@PathVariable(value = "id")int id) {
        return userService.getUserByID(id);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ServerResponse userLogin(@RequestBody JSONObject requestParams) {
        logger.info("LOGIN BEGIN");

        String name = requestParams.getString("name");
        String password = requestParams.getString("password");

        ServerResponse<Object> serverResponse = new ServerResponse<Object>();
        String msg;
        try{

            Map<String, Object> serviceResult = userService.login(name, password);
            Object result = serviceResult.get("status");

            if(result== GlobalEnums.UserSUCCESS){
                msg = "Login SUCCESS";
                return serverResponse.createBySuccess(msg, serviceResult);
            }else{
                msg = "Login FAILED";
                return serverResponse.createByError(msg, serviceResult);
            }
        }catch (Exception e) {
            logger.error("login异常" + e.getMessage());
            msg = "Login Server FAILED";
            return serverResponse.createByError(msg, "Login SERVER ERROR");
        }

    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ServerResponse userLogout(@RequestHeader LinkedHashMap header) {
        ServerResponse<Object> serverResponse = new ServerResponse<Object>();
        String msg;
        try{
            Object jwt = header.get(GlobalConsts.JWTHeader);
            if(jwt == null){
                msg = "TICKET timeout";
                return serverResponse.createByErrorMsg(msg);
            }
            Map<String, Object> result = userService.logout(jwt.toString());
            if(result.get("status")!=GlobalEnums.UserSUCCESS){
                msg = "logout Failed";
                return serverResponse.createBySuccess(msg, result);
            }
            msg = "logout Successfully";
            return serverResponse.createBySuccess(msg, result);
        }catch (Exception e) {
            logger.error("logout异常" + e.getMessage());
            msg = "logout Server FAILED";
            return serverResponse.createByError(msg, "logout SERVER ERROR");
        }
    }

    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public ServerResponse userReg(@RequestBody JSONObject requestParams) {
        String msg;
        ServerResponse<Object> serverResponse = new ServerResponse<Object>();

        String name = requestParams.getString("name");
        String password = requestParams.getString("password");
        try{
            logger.info("REG BEGIN");
            Map<String, Object> serviceResult = userService.register(name, password);
            Object result = serviceResult.get("status");
            if(result == GlobalEnums.UserSUCCESS){
                msg = "Reg SUCCESS";
                return serverResponse.createBySuccess(msg, serviceResult);
            }else{
                msg = "Reg FAILED";
                return serverResponse.createByError(msg, serviceResult);
            }
        }catch (Exception e) {
            logger.error("reg异常" + e.getMessage());
            msg = "Reg Server FAILED";
            return serverResponse.createByError(msg, "Reg SERVER ERROR");
        }
    }
}
