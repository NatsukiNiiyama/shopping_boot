CREATE TABLE IF NOT EXISTS LOGIN_USER (
    userId VARCHAR(10) PRIMARY KEY,
    userName VARCHAR(99),
    password VARCHAR(8)
);
CREATE TABLE IF NOT EXISTS ITEM (
    id INT PRIMARY KEY,
	name VARCHAR(100),
	imageUrl VARCHAR(255),
	price INT
);
CREATE TABLE IF NOT EXISTS BUY_HISTORY (
    historyId INT PRIMARY KEY,
    userId VARCHAR(10),
	itemId INT,
	count INT
);
CREATE SEQUENCE IF NOT EXISTS CUSTOMER_CODE_SEQ
    INCREMENT BY 1
    MAXVALUE 99999999
    START WITH 1
    NO CYCLE
;