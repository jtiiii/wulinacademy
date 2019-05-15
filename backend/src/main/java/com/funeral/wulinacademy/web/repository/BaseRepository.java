package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.ParseId;
import org.springframework.data.repository.CrudRepository;

/**
 * @author FuneralObjects
 * @date 2019-04-17 13:36
 */
public interface BaseRepository<T extends ParseId<ID>,ID> extends CrudRepository<T,ID> {

    /**
     * 相比 [saveAll] 方法，这个方法虽然不会返回主键，但是，速度更快！
     * @param entities 需要保存/更新的实体类
     * @param <S> 保存类
     */
    <S extends T> void saveBatch(Iterable<S> entities);

    void deleteByIdThroughStatus(ID id);
}
