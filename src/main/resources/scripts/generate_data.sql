INSERT INTO clients (passport_num, last_name, first_name, middle_name, address, dob, password) VALUES ('0000000000', 'Петров', 'Дмитрий', null, null, null, '$2a$10$SugPK03HDyc.8ZHBPjgrcOXyQIVGHrHOtZOHhTE4yAc8R8FlCdmXS');
INSERT INTO clients (passport_num, last_name, first_name, middle_name, address, dob, password) VALUES ('2222222222', 'Сидоров', 'Петр', 'Михайлович', 'Удмуртия', '1984-02-10', '$2a$10$VVt9fJTJtkqJdGvRQ7MSTu8q4QQ4tJqF2MB5h2FqzjCMU4COA857e');

INSERT INTO accounts (description, money, client_id) VALUES ('Maestro', 100.50, 1);
INSERT INTO accounts (description, money, client_id) VALUES ('Visa', 123.45, 2);
INSERT INTO accounts (description, money, client_id) VALUES ('MasterCard', 543.21, 2);
INSERT INTO accounts (description, money, client_id) VALUES ('MIR', 500.00, 2);