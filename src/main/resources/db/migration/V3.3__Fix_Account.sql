alter table account
    add column activation_code varchar(100);

alter table if exists account
    add constraint UK_account_activation_code
    unique (activation_code);

update account set account_status_id = (
    select id
    from account_status acs
    where acs.name like 'Активирован')
where email like 'and.swiridow2014@yandex.ru';