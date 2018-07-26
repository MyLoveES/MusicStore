package com.duanyao.music.service;

import com.duanyao.music.mapper.LoginTicketDAO;
import com.duanyao.music.mapper.UserMapper;
import com.duanyao.music.model.LoginTicket;
import com.duanyao.music.model.User;
import com.duanyao.music.util.GlobalConsts;
import com.duanyao.music.util.GlobalEnums;
import com.duanyao.music.util.MusicUtil;
import com.duanyao.music.util.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketDAO loginTicketMapper;

    @Autowired
    private ServerResponse serverResponse;

    public List<User> getUserList(){
        //User us= userMapper.getUser();
        return userMapper.getUserList();
    }

    public User getUserByID(int id){
        //User us= userMapper.getUser();
        return userMapper.getUserByID(id);
    }

    public User getUserByName(String name){
        //User us= userMapper.getUser();
        return userMapper.getUserByName(name);
    }

    public Map<String, Object> login(String name, String password){
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        String ticket = "ticket";
        User queryUser = userMapper.getUserByName(name);
        if(StringUtils.isBlank(name)){
            result.put(status, GlobalEnums.UserNameEmptyERROR);
        }
        else if(StringUtils.isBlank(password)){
            result.put(status, GlobalEnums.UserPasswordEmptyERROR);
        }
        else if(queryUser==null){
            result.put(status, GlobalEnums.UserNameNotExitsERROR);
        }
        else{
            if (!MusicUtil.MD5(password+queryUser.getSalt()).equals(queryUser.getPassword())) {
                result.put(status, GlobalEnums.UserPasswordWrongERROR);
            }
            else{
                int user_id = queryUser.getId();
                LoginTicket oldTicket = loginTicketMapper.getTicketbyUserIDAndStatus(user_id, GlobalConsts.ExitsStatus);
                if(oldTicket!=null){
                    loginTicketMapper.updateStatus(oldTicket.getTicket(), GlobalConsts.NotExitsStatus);
                }

                String loginTicket = addLoginTicket(queryUser.getId());
                result.put(ticket, loginTicket);
                result.put(status, GlobalEnums.UserSUCCESS);
            }
        }
        return result;
    }

    public Map<String, Object> register(String name, String password){
        //User us= userMapper.getUser();
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        String ticket = "ticket";
        if(StringUtils.isBlank(name)){
            result.put(status, GlobalEnums.UserNameEmptyERROR);
        }
        else if(StringUtils.isBlank(password)){
            result.put(status, GlobalEnums.UserPasswordEmptyERROR);
        }
        else if(userMapper.getUserByName(name) != null){
            result.put(status, GlobalEnums.UserNameExitsERROR);
        }
        else{
            User user = new User();
            user.setName(name);
            user.setSalt(UUID.randomUUID().toString().substring(0, 5));
            String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
            user.setHeadUrl(head);
            Date date = new Date();
            user.setDate_joined(date);
            user.setLast_login_time(date);
            user.setIs_active(true);
            user.setStatus(GlobalConsts.ExitsStatus);
            user.setPassword(MusicUtil.MD5(password+user.getSalt()));
            userMapper.save(user);
            result.put(status, GlobalEnums.UserSUCCESS);
            result.put(data, user);

            int user_id = user.getId();
            LoginTicket oldTicket = loginTicketMapper.getTicketbyUserIDAndStatus(user_id, GlobalConsts.ExitsStatus);
            if(oldTicket!=null){
                loginTicketMapper.updateStatus(oldTicket.getTicket(), GlobalConsts.NotExitsStatus);
            }

            String loginTicket = addLoginTicket(user_id);
            result.put(ticket, loginTicket);
        }
        return result;
    }

    private String addLoginTicket(int user_id) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUser_id(user_id);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24*7);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketMapper.save(ticket);
        return ticket.getTicket();
    }


    public Map<String, Object> logout(String ticket) {
        LoginTicket oldTicket = loginTicketMapper.getTicketbyTicket(ticket);
        Map<String, Object> result = new HashMap<String, Object>();
        String status = "status";
        String data = "data";
        if(oldTicket==null){
            result.put(status, GlobalEnums.UserTicketNotExitsERROR);
            return result;
        }
        loginTicketMapper.updateStatus(ticket, GlobalConsts.NotExitsStatus);
        result.put(status, GlobalEnums.UserSUCCESS);
        return result;
        //result.put(data);
    }

    public void updateUser(User user){
        //User us= userMapper.getUser();
        userMapper.update(user);
    }
}
