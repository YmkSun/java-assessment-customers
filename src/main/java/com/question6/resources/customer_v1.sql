
CREATE DATABASE customer_db;
USE customer_db;

CREATE TABLE customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(60) NOT NULL,
    short_name VARCHAR(30) NOT NULL,
    UNIQUE (short_name)
);

CREATE TABLE address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    address1 VARCHAR(80),
    address2 VARCHAR(80),
    address3 VARCHAR(80),
    city VARCHAR(50),
    customer_id INT,
    postal_code VARCHAR(10),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);