package com.homelab.onlinechat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.userId = :user1 AND m.receiver.userId = :user2) OR " +
            "(m.sender.userId = :user2 AND m.receiver.userId = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findChatHistory(@Param("user1") String user1, @Param("user2") String user2);
}
