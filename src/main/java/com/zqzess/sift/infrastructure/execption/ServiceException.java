package com.zqzess.sift.infrastructure.execption;

import lombok.Getter;

/**
 * @author: Amim
 * @year: 2022/9
 **/
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}
