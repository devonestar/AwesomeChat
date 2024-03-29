package com.awesome.domains.chat.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomDAO extends JpaRepository<ChatRoomEntity, Long> {
    ChatRoomEntity findByRoomId(String roomId);
    List<ChatRoomEntity> findAllByRoomCreatorUserId(String userId);
    List<ChatRoomEntity> findAllByRoomIds(List<String> roomIds);
    void deleteAllById(String id);
}
