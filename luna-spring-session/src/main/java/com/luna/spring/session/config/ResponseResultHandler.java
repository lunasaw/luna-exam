package com.luna.spring.session.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luna.common.anno.ResponseResult;
import com.luna.common.dto.ResultDTO;
import com.luna.common.dto.ResultDTOUtils;
import com.luna.common.dto.constant.ResultCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luna@mac
 * 2021年04月13日 09:02
 */
@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    public static final String RESPONSE_RESULT_ANN = "RESPONSE_RESULT_ANN";

    /**
     * 判断有无标记 没有就直接返回
     * 
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult responseResult = (ResponseResult)request.getAttribute(RESPONSE_RESULT_ANN);
        return responseResult != null;
    }

    // @ExceptionHandler(value = Exception.class)
    // Object handleException(Exception e, HttpServletRequest request) {
    // return ResultDTOUtils.failure(ResultCode.ERROR_SYSTEM_EXCEPTION, e.getMessage());
    // }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {
        if (body instanceof Throwable) {
            Throwable exception = (Throwable)body;
            return ResultDTOUtils.failure(ResultCode.ERROR_SYSTEM_EXCEPTION, exception.getMessage());
        }
        if (body instanceof String) {
            return body;
        }
        if (body instanceof ResultDTO) {
            return body;
        }
        return ResultDTOUtils.success(body);
    }
}
