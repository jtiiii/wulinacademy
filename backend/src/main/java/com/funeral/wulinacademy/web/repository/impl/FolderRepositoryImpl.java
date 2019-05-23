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
    public List<Folder> findAllByParentId(Integer parentId) {
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
    public boolean existsFolderByParentId(Integer parentId) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("parentId", parentId);
        Long count = new SqlStatement(this.getJdbcTemplate(), param)
                .SELECT("count(1)")
                .FROM(this.getTable())
                .WHERE()
                .EQUALSE("parent_id", "#{parentId}").END_Condition()
                .queryOne(long.class);
        return count > 0;
    }

    @Override
    public boolean existsByFolderIdAndUserId(Integer folderId,String userId) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("userId",userId);
        params.put("folderId",folderId);
        Long count = new SqlStatement(this.getJdbcTemplate(),params)
                .SELECT("count(1)")
                .FROM(this.getTable())
                .WHERE()
                    .EQUALSE("user_id","#{userId}")
                .AND()
                    .EQUALSE("folder_id","#{folderId}")
                .END_Condition()
                .queryOne(long.class);
        return count > 0;
    }

    @Override
    public List<Folder> findFoldersByParentIdAndUserId(Integer parentId, String userId) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("parentId",parentId);
        params.put("userId",userId);
        return new SqlStatement(this.getJdbcTemplate(),params)
                .SELECT_ALL()
                .FROM(this.getTable())
                .WHERE()
                    .EQUALSE("parent_id","#{parentId}")
                .AND()
                    .EQUALSE("user_id", "#{userId}")
                .END_Condition().query(Folder.class);
    }
}
