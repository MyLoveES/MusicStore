package com.duanyao.music.mapper;

import com.duanyao.music.model.LoginTicket;
import com.duanyao.music.model.User;
import com.duanyao.music.util.GlobalConsts;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LoginTicketDAO {
    /**
     * 新增一条ticket信息
     *
     * @param loginTicket Ticket obj
     */
    @Insert("insert into loginticket(user_id,expired,status,ticket) " +
            "values(#{user_id},#{expired},#{status},#{ticket})")
    void save(LoginTicket loginTicket);

    /**
     * 修改ticket状态信息
     *
     * @param ticket,status Ticket obj
     *
     */
    @Update("update loginticket set status=#{status} where ticket=#{ticket}")
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    /***
     * del ticket
     *
     * @param id id
     */
    @Update("update loginticket set status="+ GlobalConsts.NotExitsStatus +" where id=#{id} ")
    void del(@Param("id")int id);

    /**
     * 获取ticket
     *
     * @param ticket Ticket obj
     */
    @Select("select * from loginticket " +
            "where " +
            "ticket=#{ticket}")
    LoginTicket getTicketbyTicket(@Param("ticket")String ticket);

    /**
     * 获取ticket
     *
     * @param user_id Ticket obj
     */
    @Select("select * from loginticket " +
            "where " +
            "user_id=#{user_id} and status=#{status}")
    LoginTicket getTicketbyUserIDAndStatus(@Param("user_id")int user_id, @Param("status")int status);
}
