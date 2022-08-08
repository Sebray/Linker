insert into city (name, country_id)
values
    ('Москва',
     (select id
      from country c
      where c.name like 'Россия')
    );

insert into city (name, country_id)
values
    ('Воронеж',
     (select id
      from country c
      where c.name like 'Россия')
    );

insert into city (name, country_id)
values
    ('Санкт-Петербург',
     (select id
      from country c
      where c.name like 'Россия')
    );

insert into city (name, country_id)
values
    ('Нью-Йорк',
     (select id
      from country c
      where c.name like 'США')
    );

insert into city (name, country_id)
values
    ('Лос-Анджелес',
     (select id
      from country c
      where c.name like 'США')
    );

insert into city (name, country_id)
values
    ('Чикаго',
     (select id
      from country c
      where c.name like 'США')
    );

insert into city (name, country_id)
values
    ('Минск',
     (select id
      from country c
      where c.name like 'Беларусь')
    );

insert into city (name, country_id)
values
    ('Гомель',
     (select id
      from country c
      where c.name like 'Беларусь')
    );

insert into city (name, country_id)
values
    ('Брест',
     (select id
      from country c
      where c.name like 'Беларусь')
    );

insert into city (name, country_id)
values
    ('Алматы',
     (select id
      from country c
      where c.name like 'Казахстан')
    );

insert into city (name, country_id)
values
    ('Нур-Султан',
     (select id
      from country c
      where c.name like 'Казахстан')
    );