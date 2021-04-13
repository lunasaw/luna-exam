package com.luna.spring.session.utils;

import com.luna.common.dto.constant.ResultCode;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.platform.commons.util.StringUtils;
import org.omg.CORBA.UserException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luna
 */
public class CookieUtils {
    /** session key名字 */
    public static final String SESSION_KEY_NAME = "sessionKey";

    public static String getSessionKeyFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SESSION_KEY_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getOneSessionKey(HttpServletRequest request) {
        return getOneSessionKey(null, request);
    }

    /**
     * 优先使用paramSessionKey
     * 
     * @param request
     * @param paramSessionKey
     * @return
     */
    public static String getOneSessionKey(String paramSessionKey, HttpServletRequest request) {
        if (StringUtils.isNotBlank(paramSessionKey)) {
            return paramSessionKey;
        }

        String sessionKey = getSessionKeyFromRequest(request);
        if (StringUtils.isNotBlank(sessionKey)) {
            return sessionKey;
        }
        throw new RuntimeException(ResultCode.MSG_ERROR_SYSTEM_EXCEPTION);
    }
}
