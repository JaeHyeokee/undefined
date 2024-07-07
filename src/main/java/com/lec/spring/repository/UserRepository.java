package com.lec.spring.repository;

import com.lec.spring.domain.Post;
import com.lec.spring.domain.Room;
import com.lec.spring.domain.User;


public interface UserRepository {

    User findById(Long userId);

    // 특정 email 의 user return
    User findByNickname(String nickname);
    User findByEmail(String email);
    User findByPhonenum(String phonenum);

    int save(User user);

    int userupdate(User user);

    void delete(Long userId);

    void deleteUserReferences(Long userId);

    void deleteLodging(Long userId);
    void deleteUserAuthority(Long userId);
    void deleteLikes(Long userId);
    void deletePosts(Long userId);
    void deleteComments(Long userId);
    void deleteReservations(Long userId);
    void deleteBookings(Long userId);

    void deleteRooms(Long userId);

    void deleteUser(Long userId);

    void deletePostsByBookingId(Long userId);

    User findByProviderId(String providerId);

    void deleteBookingByRoomId(Long userId);

    void deleteRoomByLodgingId(Long userId);

    void deleteLoves(Long userId);
}