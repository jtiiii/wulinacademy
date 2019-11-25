package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author FuneralObjects
 * @date 2019-01-06 02:22
 */
public interface NewsRepository extends JpaRepository<News,Integer> {

}
