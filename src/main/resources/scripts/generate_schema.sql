CREATE TABLE clients (
  id serial PRIMARY KEY,
  passport_num varchar(10) NOT NULL UNIQUE,
  last_name varchar(50) NOT NULL,
  first_name varchar(50) NOT NULL,
  middle_name varchar(50),
  address varchar(100),
  dob date,
  password varchar(60) NOT NULL
);

CREATE TABLE accounts (
  id serial PRIMARY KEY,
  description varchar(50) NOT NULL,
  money numeric(8, 2) NOT NULL,
  client_id int REFERENCES clients(id)
    ON UPDATE CASCADE
    ON DELETE SET NULL
);