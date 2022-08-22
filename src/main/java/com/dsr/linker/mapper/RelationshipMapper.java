package com.dsr.linker.mapper;

import com.dsr.linker.dto.RelationshipDto;
import com.dsr.linker.dto.RelationshipStatusDto;
import com.dsr.linker.entity.Relationship;
import org.springframework.stereotype.Component;

@Component
public class RelationshipMapper {
    public RelationshipDto toRelationshipDto(Relationship relationship){
        return new RelationshipDto(relationship.getRelationshipId().getFirstAcc(), relationship.getRelationshipId().getSecondAcc(),
                new RelationshipStatusDto(relationship.getStatus().getId(), relationship.getStatus().getName()), relationship.getSender());
    }
}
