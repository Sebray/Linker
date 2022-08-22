update relationship
set relationship_status_id = (
    select rs.id
    from relationship_status rs
    where rs.name like 'Дружба')
where second_acc_id = (
    select a.id
    from account a
    where a.email like 'and.swiridow2014@yandex.ru');

delete
from relationship_status
where name like 'Черный список';