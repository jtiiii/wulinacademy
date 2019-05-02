package com.funeral.wulinacademy.web.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funeral.wulinacademy.web.common.standard.DateStandard;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 时间段对象
 *
 * @author FuneralObjects
 * CreateTime 2019-03-14 10:48
 */
@Data
@Accessors(chain = true)
public class TimePeriod {
    /**
     * 开始时间
     */
    @JsonFormat(pattern = DateStandard.DATE_TIME_FORMAT)
    private Date start;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = DateStandard.DATE_TIME_FORMAT)
    private Date end;
    /**
     * 检查 开始时间 和 结束时间 是否能正确的成为一个时间段
     * @return 检查结果
     */
    public boolean checkIsPeriod(){
        if(start == null || end == null){
            return false;
        }
        return !end.before(start);
    }
}
