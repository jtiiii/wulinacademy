package com.funeral.wulinacademy.web.controller.model.search;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 简单分页
 *
 * @author FuneralObjects
 * @date 2019-03-20 10:11
 */
@Data
@Accessors(chain = true)
public class SimplePage{
    /**
     * 页数 默认为1
     */
    private int num = 1;

    /**
     * 每页条数 默认为10
     */
    private int size = 10;

    public SimplePage(){}

    public SimplePage(int num, int size) {
        if(num > 1){
            this.num = num;
        }
        if(num > 0){
            this.size = size;
        }
    }
    public int getStart(){
        return (this.num - 1) * this.size;
    }

    public int getPagesAndlimitPage(int total){
        int pages = this.getPages(total);
        if(this.num > pages){
            this.num = pages;
        }
        return pages;
    }

    private int getPages(int total){
        if( total <= 0){
            return 1;
        }
        return total % this.size == 0
                ? total / this.size
                : total / this.size +1;
    }
}
