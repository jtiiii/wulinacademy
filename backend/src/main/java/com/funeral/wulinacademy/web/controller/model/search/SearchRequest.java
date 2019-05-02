package com.funeral.wulinacademy.web.controller.model.search;

import com.funeral.wulinacademy.web.controller.model.TimePeriod;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 搜索请求
 *
 * @author FuneralObjects
 * @date 2019-03-20 09:46
 */

@Data
@Accessors(chain = true)
public class SearchRequest{
    private SimplePage page = new SimplePage();
    private TimePeriod timePeriod = new TimePeriod();

    public void setStartTime(Date startTime){
        this.timePeriod.setStart(startTime);
    }

    public void setEndTime(Date endTime){
        this.timePeriod.setEnd(endTime);
    }

    public void setPageNum(Integer pageNum){
        if(pageNum != null) {
            this.page.setNum(pageNum);
        }
    }

    public void setPageSize(Integer pageSize){
        if(pageSize != null){
            this.page.setSize(pageSize);
        }
    }

}
