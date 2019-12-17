package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础业务类
 *
 * @author FuneralObjects
 * CreateTime 2019-04-18 11:58
 */
public abstract class BaseService implements Service {

    protected Logger logger = LoggerFactory.getLogger(getName());

    @Override
    public String getName() {
        return "BASE";
    }
}
