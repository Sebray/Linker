package com.dsr.linker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatChannel {
    @Id
    private String uuid;

    @OneToOne
    @JoinColumn(name = "first_acc_id", nullable = false)
    private Account firstAcc;

    @OneToOne
    @JoinColumn(name = "second_acc_id", nullable = false)
    private Account secondAcc;
}
