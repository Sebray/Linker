package com.dsr.linker.repository;

import com.dsr.linker.entity.Relationship;
import com.dsr.linker.entity.RelationshipId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {

    @Query("select r " +
            "from Relationship r" +
            " where r.relationshipId.firstAcc = :firstId and r.relationshipId.secondAcc = :secondId" +
            " or r.relationshipId.firstAcc = :secondId and r.relationshipId.secondAcc = :firstId")
    Relationship findByFirstAccAndSecondAcc(Long firstId, Long secondId);

    @Query("select r.relationshipId " +
            "from Relationship r" +
            " where (r.relationshipId.firstAcc = :firstId or r.relationshipId.secondAcc = :firstId)" +
            " and r.sender = 0")
    Page<RelationshipId> getFriends(Long firstId, Pageable pageable);
}
