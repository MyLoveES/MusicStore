package com.duanyao.music.mapper;

import com.duanyao.music.model.User;

import com.duanyao.music.util.GlobalConsts;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    /**
     * 获取所有用户
     *
     * @return List<User>
     */
    @Select("select * from user ")
    List<User> getUserList();

    /**
     * 获取单一用户
     *
     * @return User
     */
    @Select("select * from user " +
            "where " +
            "id=#{id} ")
    User getUserByID(@Param("id")int id);

    /**
     * 获取单一用户
     *
     * @return User
     */
    @Select("select * from user " +
            "where " +
            "phone=#{phone} ")
    User getUserByPhone(@Param("phone")String phone);

    /**
     * 获取单一用户
     *
     * @return User
     */
    @Select("select * from user " +
            "where " +
            "email=#{email} ")
    User getUserByEmail(@Param("email")String email);

    /**
     * 获取单一用户
     *
     * @return User
     */
    @Select("select * from user " +
            "where " +
            "name=#{name} ")
    User getUserByName(@Param("name")String name);


    /**
     * 修改用户信息
     *
     * @param user 用户obj
     */
    @Update("update user set " +
            "name=#{name},password=#{password},email=#{email},phone=#{phone},salt=#{salt}, " +
            "group=#{group},is_active=#{is_active},is_staff=#{is_staff},last_login_time=#{last_login_time}, " +
            "status=#{status},headUrl=#{headUrl},date_joined=#{date_joined} " +
            "where id=#{id} ")
    void update(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @Update({"update user set status = "+ GlobalConsts.NotExitsStatus +"where id=#{id} "})
    void del(@Param("id")int id);

    /**
     * 新增一条用户信息
     *
     * @param user 用户obj
     */
    @Insert("insert into user(name,password,email,phone,group_id,salt,is_active,is_staff,last_login_time,status,headurl,date_joined) " +
            "values(#{name},#{password},#{email},#{phone},#{group_id},#{salt},#{is_active},#{is_staff},#{last_login_time},#{status},#{headurl},#{date_joined})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(User user);

}
