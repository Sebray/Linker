package com.dsr.linker.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Relationship {
    @EmbeddedId
    private RelationshipId relationshipId;

    @OneToOne
    @MapsId("firstAcc")
    private Account firstAcc;

    @OneToOne
    @MapsId("secondAcc")
    private Account secondAcc;

    @OneToOne
    @JoinColumn(name = "relationship_status_id", nullable = false)
    private RelationshipStatus status;

    @Column(nullable = false)
    private Byte sender = 0; //-1 – первый пользователь, 1 – второй
}
