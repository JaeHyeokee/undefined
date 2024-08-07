package com.lec.spring.service;

import com.lec.spring.domain.ProvLodging;
import com.lec.spring.domain.Room;
import com.lec.spring.repository.ProviderRepository;
import com.lec.spring.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private RoomRepository roomRepository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository, RoomRepository roomRepository) {
        this.providerRepository = providerRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<ProvLodging> getLodgings(Long userId) {
        return providerRepository.findLodgings(userId);
    }

    @Override
    public ProvLodging getAllDetails(Long lodgingId) {
        return providerRepository.findAllDetails(lodgingId);
    }

    @Override
    public void saveLodging(ProvLodging lodging) {
        providerRepository.saveLodging(lodging);
    }

    @Override
    public List<ProvLodging> getLodgingsAndRoomsByUserId(Long userId) {
        List<ProvLodging> lodgingList = providerRepository.findByUserId(userId);
        lodgingList.forEach(lodging -> {
            List<Room> roomList = roomRepository.findRoomsByLodgingId(lodging.getLodgingId());
            lodging.setRoomList(roomList);
        });
        return lodgingList;
    }

    @Override
    public void updateLodging(ProvLodging lodging) {
        providerRepository.updateLodging(lodging);
    }

    @Override
    public void deleteLodging(int lodgingId) {
        providerRepository.deleteLodging(lodgingId);
    }
}