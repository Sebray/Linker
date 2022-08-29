package com.dsr.linker.repository;

import com.dsr.linker.entity.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannel, Long> {
    @Query("select cc " +
            "from ChatChannel cc " +
            "where cc.firstAcc in (:firstAcc, :secondAcc) " +
            "and cc.secondAcc in (:firstAcc, :secondAcc)")
    Optional<ChatChannel> getChatByIds(Long firstAcc, Long secondAcc);

    Optional<ChatChannel> getChatChannelByUuid(String uuid);

}
