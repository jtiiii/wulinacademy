package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.repository.FolderRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:47
 */
@Repository
public class FolderRepositoryImpl extends BaseRepositoryImpl<Folder,Integer> implements FolderRepository {
    @Override
    public List<Folder> findByParentId(Integer parentId) {
        Map<String,Object> param = new HashMap<>(1);
        param.put("parentId",parentId);
        return new SqlStatement(this.getJdbcTemplate(),param)
                .SELECT_ALL()
                .FROM(this.getTable())
                .WHERE()
                .EQUALSE("parent_id","#{parentId}").END_Condition()
                .query(Folder.class);
    }

    @Override
    public boolean existsFolderByParentId(Integer parentId){
        Map<String,Object> param = new HashMap<>(1);
        param.put("parentId",parentId);
        Long count = new SqlStatement(this.getJdbcTemplate(),param)
                .SELECT("count(1)")
                .FROM(this.getTable())
                .WHERE()
                .EQUALSE("parent_id","#{parentId}").END_Condition()
                .query(long.class).get(0);
        return count > 0;
    }
}
