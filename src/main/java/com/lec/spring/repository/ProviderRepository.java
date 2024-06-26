package com.lec.spring.repository;

import com.lec.spring.domain.ProvLodging;
import com.lec.spring.domain.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProviderRepository {
    List<ProvLodging> findLodgings(Long userId);
    ProvLodging findAllDetails(Long lodgingId);
    void saveLodging(ProvLodging lodging);

    List<ProvLodging> findByUserId(Long userId);

    void updateLodging(ProvLodging lodging);

    void deleteLodging(int lodgingId);

}
