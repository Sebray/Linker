insert into account (first_name, last_name, email, password, birthday, city_id, role_id, account_status_id)
values
    ('Андрей',
     'Гаврилов',
     'and.sgors@yandex.ru',
     'tim89320_q',
     date('2002-03-05'),
     (select id
      from city c
      where c.name like 'Воронеж'),
     (select id
      from role r
      where r.name like 'Пользователь'),
     (select id
      from account_status ast
      where ast.name like 'Открытый')
    );

insert into account (first_name, last_name, email, password, birthday, city_id, role_id, account_status_id)
values
    ('Екатерина',
     'Альбертовна',
     'aor.dreams@yandex.ru',
     'trt_wow143',
     date('2000-11-25'),
     (select id
      from city c
      where c.name like 'Минск'),
     (select id
      from role r
      where r.name like 'Пользователь'),
     (select id
      from account_status ast
      where ast.name like 'Открытый')
    );

insert into account (first_name, last_name, email, password, birthday, description, city_id, role_id, account_status_id)
values
    ('Андрей',
     'Свиридов',
     'and.swiridow2014@yandex.ru',
     'timteamdr_1',
     date('2000-11-05'),
     'Увлекаюсь программированием, учусь в ВГУ.',
     (select id
      from city c
      where c.name like 'Москва'),
     (select id
      from role r
      where r.name like 'Администратор'),
     (select id
      from account_status ast
      where ast.name like 'Закрытый')
    );

insert into account (first_name, last_name, email, password, birthday, city_id, role_id, account_status_id)
values
    ('Марк',
     'Крейн',
     'mark3201@gmail.com',
     'tortoror_32',
     date('1992-03-01'),
     (select id
      from city c
      where c.name like 'Чикаго'),
     (select id
      from role r
      where r.name like 'Пользователь'),
     (select id
      from account_status ast
      where ast.name like 'Открытый')
    );

insert into account (first_name, last_name, email, password, birthday, city_id, role_id, account_status_id)
values
    ('Роман',
     'Чирков',
     'romanlv20010@yandex.ru',
     'romanchik_3201',
     date('1999-11-11'),
     (select id
      from city c
      where c.name like 'Воронеж'),
     (select id
      from role r
      where r.name like 'Пользователь'),
     (select id
      from account_status ast
      where ast.name like 'Открытый')
    );

insert into account (first_name, last_name, email, password, birthday, city_id, role_id, account_status_id)
values
    ('Елизавета',
     'Сильв',
     'elzsi20@yandex.ru',
     'siest_o_12',
     date('2001-09-21'),
     (select id
      from city c
      where c.name like 'Санкт-Петербург'),
     (select id
      from role r
      where r.name like 'Пользователь'),
     (select id
      from account_status ast
      where ast.name like 'Открытый')
    );