package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.ImageRelate;
import com.funeral.wulinacademy.web.repository.ImageRelateRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuneralObjects
 * @date 2019-05-18 15:27
 */
@Repository
public class ImageRelateRepositoryImpl extends BaseRepositoryImpl<ImageRelate,String> implements ImageRelateRepository {

    @Override
    public List<ImageRelate> findAllByFolderId(Integer folderId){
        Map<String,Object> params = new HashMap<>(1);
        params.put("folderId",folderId);
        return new SqlStatement(this.getJdbcTemplate(),params)
                .SELECT_ALL()
                .FROM("v_image_relate")
                .WHERE()
                .EQUALSE("folder_id","#{folderId}").END_Condition()
                .query(ImageRelate.class);
    }
}
