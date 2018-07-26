package com.duanyao.music.model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String email;
    private String phone;
    private String group_id;
    private Boolean is_active;
    private Boolean is_staff;
    private Date last_login_time;
    private Date date_joined;
    private int status;
    private String headurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group) {
        this.group_id = group;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Boolean getIs_staff() {
        return is_staff;
    }

    public void setIs_staff(Boolean is_staff) {
        this.is_staff = is_staff;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(Date date_joined) {
        this.date_joined = date_joined;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHeadUrl() {
        return headurl;
    }

    public void setHeadUrl(String headUrl) {
        this.headurl = headUrl;
    }

    public User() {
    }

    public User(String name, String password, String salt, String phone,String email ,String headurl) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.headurl = headurl;
        this.email = email;
        this.phone = phone;
    }

}
