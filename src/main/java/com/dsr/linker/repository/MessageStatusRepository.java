package com.dsr.linker.repository;

import com.dsr.linker.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
    MessageStatus findByName(String name);
}
