create table chat_channel (
    uuid varchar(255) not null,
    first_acc_id int8 not null,
    second_acc_id int8 not null,
    primary key (uuid));

alter table if exists chat_channel
    add constraint FK_chat_channel_first_acc_id
    foreign key (first_acc_id)
    references account;

alter table if exists chat_channel
    add constraint FK_chat_channel_second_acc_id
    foreign key (second_acc_id)
    references account;
