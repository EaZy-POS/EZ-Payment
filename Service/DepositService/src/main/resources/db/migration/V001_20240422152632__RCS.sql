-- Script generated by Redgate Compare v1.19.7.21820


-- deployment: Creating trx_balance_history...
CREATE TABLE trx_balance_history (
    id char(50) NOT NULL,
    transaction_date datetime NOT NULL,
    account char(15) NOT NULL,
    journal char(1) NOT NULL DEFAULT '0',
    amount bigint(20) NOT NULL DEFAULT 0,
    refnum varchar(50) NOT NULL,
    remarks varchar(255) NOT NULL
);
ALTER TABLE trx_balance_history 
ADD PRIMARY KEY (id);


-- deployment: Creating bal_deposit...
CREATE TABLE bal_deposit (
    id int(11) NOT NULL,
    created_at datetime NULL,
    created_by varchar(50) NULL,
    updated_at datetime NULL,
    updated_by varchar(50) NULL,
    account char(15) NOT NULL,
    balance bigint(20) NULL DEFAULT 0,
    deleted_at datetime NULL,
    deleted_by varchar(50) NULL
);
ALTER TABLE bal_deposit 
CHANGE COLUMN id  id INT NOT NULL AUTO_INCREMENT FIRST, 
ADD PRIMARY KEY (id, account);


-- deployment: Creating bal_account...
CREATE TABLE bal_account (
    id char(15) NOT NULL,
    created_at datetime NULL,
    created_by varchar(50) NULL,
    updated_at datetime NULL,
    updated_by varchar(50) NULL,
    das varchar(255) NOT NULL,
    status int(11) NULL DEFAULT 0,
    deleted_at datetime NULL,
    deleted_by varchar(50) NULL
);
ALTER TABLE bal_account 
ADD PRIMARY KEY (id, das);

