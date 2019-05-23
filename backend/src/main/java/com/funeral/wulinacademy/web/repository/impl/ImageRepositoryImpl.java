package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.Image;
import com.funeral.wulinacademy.web.entity.ImageRelate;
import com.funeral.wulinacademy.web.repository.ImageRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:55
 */
@Repository
public class ImageRepositoryImpl extends BaseRepositoryImpl<Image,Integer> implements ImageRepository {
    @Override
    public Image findByMd5(String md5) {
        Map<String,Object> params = new HashMap<>(1);
        params.put("md5",md5);

        List<Image> images = new SqlStatement(this.getJdbcTemplate(),params)
                .SELECT_ALL()
                .FROM(this.getTable())
                .WHERE()
                .EQUALSE("md5", "#{md5}").END_Condition()
                .query(Image.class);
        if(CollectionUtils.isEmptyAfterRemoveNull(images)){
            return null;
        }
        return images.get(0);
    }
}
