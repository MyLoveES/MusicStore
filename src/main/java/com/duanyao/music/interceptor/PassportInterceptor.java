package com.duanyao.music.interceptor;

import com.duanyao.music.mapper.LoginTicketDAO;
import com.duanyao.music.mapper.UserMapper;
import com.duanyao.music.model.HostHolder;
import com.duanyao.music.model.LoginTicket;
import com.duanyao.music.model.User;
import com.duanyao.music.util.GlobalConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDAO loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        String jwt = httpServletRequest.getHeader("JWT");
        if (jwt != null) {
            ticket = jwt;
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketMapper.getTicketbyTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != GlobalConsts.ExitsStatus) {
                return true;
            }

            User user = userMapper.getUserByID(loginTicket.getUser_id());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        if (modelAndView != null && hostHolder.getUser() != null) {
//            modelAndView.addObject("user", hostHolder.getUser());
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
