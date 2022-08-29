package com.dsr.linker.repository;

import com.dsr.linker.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m " +
            "from Message m " +
            "where m.sender in (:firstAcc, :secondAcc) " +
            "and m.repliedMessage in (:firstAcc, :secondAcc)" +
            "order by m.sendingDate")
    List<Message> getMessages(Long firstAcc, Long secondAcc);
}
