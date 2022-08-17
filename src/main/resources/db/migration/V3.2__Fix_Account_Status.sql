insert into account_status (name)
values ('Не активирован');

update account_status set name = 'Активирован'
where name like 'Открытый';

update account_status set name = 'Заблокирован'
where name like 'Закрытый';
