package com.dsr.linker.repository;

import com.dsr.linker.entity.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipStatusRepository extends JpaRepository<RelationshipStatus, Long> {
    RelationshipStatus findFirstByName(String name);
}
