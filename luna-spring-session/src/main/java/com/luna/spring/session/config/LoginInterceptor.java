package com.luna.spring.session.config;

import com.alibaba.fastjson.JSON;
import com.luna.common.dto.ResultDTO;
import com.luna.common.dto.constant.ResultCode;
import com.luna.redis.util.RedisBoundUtil;
import com.luna.redis.util.RedisKeyUtil;
import com.luna.redis.util.RedisValueUtil;
import com.luna.spring.session.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author luna@mac
 * 2021年04月10日 10:37
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log        = LoggerFactory.getLogger(LoginInterceptor.class);

    public static String        sessionKey = "luna-session";

    @Autowired
    private RedisValueUtil      redisValueUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String oneSessionKey = CookieUtils.getSessionKeyFromRequest(request);
        String session = (String)redisValueUtil.get(oneSessionKey);
        if (session != null) {
            log.info(session);
            return true;
        } else {
            response.setHeader("Content-Type", "application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(
                new ResultDTO(false, ResultCode.ERROR_SYSTEM_EXCEPTION, ResultCode.MSG_ERROR_SYSTEM_EXCEPTION)));
            printWriter.flush();
            printWriter.close();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

    }
}
