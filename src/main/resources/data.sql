INSERT INTO roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES('2022-01-05 00:00:00', 1,false, '2022-01-05 00:00:00',1,'Admin');
INSERT INTO roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES('2022-01-05 00:00:00', 1,false, '2022-01-05 00:00:00',1,'Manager');
INSERT INTO roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES('2022-01-05 00:00:00', 1,false, '2022-01-05 00:00:00',1,'Employee');

insert into users(enabled, is_deleted, insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, role_id, first_name, gender, last_name, pass_word, phone, user_name)
values (true, false,'2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 1, 'admin', 'MALE', 'admin','wwdwd','2323332313','admin@gmail.com');

insert into users(enabled, is_deleted, insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, role_id, first_name, gender, last_name, pass_word, phone, user_name)
values (true, false,'2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 2, 'manager', 'MALE', 'menager','wwd213wd','2323332313','manager@gmail.com');

insert into users(enabled, is_deleted, insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, role_id, first_name, gender, last_name, pass_word, phone, user_name)
values (true, false,'2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 3, 'employee', 'MALE', 'employee','wwd213wd232','23233323131313','employee@gmail.com');

