INSERT INTO clients (passport_num, last_name, first_name, middle_name, password) VALUES ('0000000000', 'Петров', 'Дмитрий', null, '$2a$10$SugPK03HDyc.8ZHBPjgrcOXyQIVGHrHOtZOHhTE4yAc8R8FlCdmXS');
INSERT INTO clients (passport_num, last_name, first_name, middle_name, password) VALUES ('1111111111', 'Сидоров', 'Петр', 'Михайлович', '$2a$10$etOmN5cIHqctP6EaoT7ywe/o4U6eZiOFd806gwU87w9SvJL3TFJ1y');

INSERT INTO accounts (number, description, money, client_id) VALUES ('201', 'Maestro', 100.50, 1);
INSERT INTO accounts (number, description, money, client_id) VALUES ('202', 'Visa', 123.45, 2);
INSERT INTO accounts (number, description, money, client_id) VALUES ('203', 'MasterCard', 543.21, 2);
INSERT INTO accounts (number, description, money, client_id) VALUES ('204', 'MIR', 500.00, 2);

INSERT INTO transactions (type, account1_num, account2_num, sum, created) VALUES (0, '201', NULL, 100.50, NOW());
INSERT INTO transactions (type, account1_num, account2_num, sum, created) VALUES (0, '202', NULL, 123.45, NOW());
INSERT INTO transactions (type, account1_num, account2_num, sum, created) VALUES (0, '203', NULL, 543.21, NOW());
INSERT INTO transactions (type, account1_num, account2_num, sum, created) VALUES (0, '204', NULL, 500.00, NOW());