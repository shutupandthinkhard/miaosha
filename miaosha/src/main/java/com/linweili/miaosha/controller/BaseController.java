package com.linweili.miaosha.controller;

import com.linweili.miaosha.error.BusinessException;
import com.linweili.miaosha.error.EnumBusinessError;
import com.linweili.miaosha.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object HandleException(HttpServletRequest httpServletRequest, Exception ex){
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsgs());
        } else {
            responseData.put("errCode", EnumBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EnumBusinessError.UNKNOWN_ERROR.getErrMsgs());
        }
        if (ex != null){
            logger.info(ex.getMessage());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
