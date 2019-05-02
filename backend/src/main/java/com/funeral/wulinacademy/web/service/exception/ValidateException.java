package com.funeral.wulinacademy.web.service.exception;

import com.funeral.wulinacademy.web.common.standard.StatusStandard;

/**
 * 业务校验异常
 * @author FuneralObjects
 * @date 2019-04-18 11:53
 */
public class ValidateException extends BusinessException {
    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void validStatus(Integer status) throws ValidateException {
        if(StatusStandard.getStatusByNum(status) == null){
            throw new ValidateException("Illegal arg of status!");
        }
    }

    public static void validStatusIsNotDelete(Integer status) throws ValidateException {
        if(StatusStandard.getStatusByNum(status) == StatusStandard.DELETED){
            throw new ValidateException("Operate failed, illegal arg of status!");
        }
    }
}
