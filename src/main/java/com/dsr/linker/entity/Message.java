package com.dsr.linker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date sendingDate;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver_id", nullable = false)
    private Account receiver;


    @OneToOne
    @JoinColumn(name = "message_status_id", nullable = false)
    private MessageStatus messageStatus;

    @OneToOne
    @JoinColumn(name = "replied_message_id")
    private Message repliedMessage;
}
