package com.dsr.linker.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class RelationshipId implements Serializable {
    @Column(name = "first_acc", nullable = false)
    private Long firstAcc;

    @Column(name = "second_acc", nullable = false)
    private Long secondAcc;
}
