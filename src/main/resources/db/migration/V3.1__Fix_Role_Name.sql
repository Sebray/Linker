update role set name = 'ROLE_USER'
where name like 'Пользователь';

update role set name = 'ROLE_ADMIN'
where name like 'Администратор';