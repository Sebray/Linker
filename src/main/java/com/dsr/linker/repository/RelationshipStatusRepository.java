package com.dsr.linker.repository;

import com.dsr.linker.entity.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipStatusRepository extends JpaRepository<RelationshipStatus, Long> {
    RelationshipStatus findFirstByName(String name);
}
