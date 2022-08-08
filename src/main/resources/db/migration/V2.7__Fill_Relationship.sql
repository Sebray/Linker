insert into relationship (first_acc_id, second_acc_id, relationship_status_id, sender)
values
    ((select id
      from account a
      where a.email like 'and.sgors@yandex.ru'),
     (select id
      from account a
      where a.email like 'aor.dreams@yandex.ru'),
     (select id
      from relationship_status rs
      where rs.name like 'Дружба'),
     0
    );

insert into relationship (first_acc_id, second_acc_id, relationship_status_id, sender)
values
    ((select id
      from account a
      where a.email like 'and.swiridow2014@yandex.ru'),
     (select id
      from account a
      where a.email like 'aor.dreams@yandex.ru'),
     (select id
      from relationship_status rs
      where rs.name like 'Отправлена заявка'),
     -1
    );

insert into relationship (first_acc_id, second_acc_id, relationship_status_id, sender)
values
    ((select id
      from account a
      where a.email like 'and.sgors@yandex.ru'),
     (select id
      from account a
      where a.email like 'and.swiridow2014@yandex.ru'),
     (select id
      from relationship_status rs
      where rs.name like 'Черный список'),
     1
    );