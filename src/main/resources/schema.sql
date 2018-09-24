CREATE TABLE clients (
  id INT IDENTITY,
  passport_num VARCHAR(10) NOT NULL UNIQUE,
  last_name VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  middle_name VARCHAR(50),
  password VARCHAR(60) NOT NULL
);

CREATE TABLE accounts (
  id INT IDENTITY,
  number VARCHAR(50) UNIQUE,
  description VARCHAR(50) NOT NULL,
  money NUMERIC(14, 2) NOT NULL,
  client_id INT REFERENCES clients(id)
    ON UPDATE CASCADE
    ON DELETE SET NULL
);

CREATE TABLE transactions (
  id INT IDENTITY,
  type INT NOT NULL,
  account1_num VARCHAR(50) NOT NULL,
  account2_num VARCHAR(50),
  sum NUMERIC(14, 2) NOT NULL,
  created TIMESTAMP WITH TIME ZONE
);