CREATE TABLE employees(
    id BIGINT not null AUTO_INCREMENT,
    name VARCHAR(150) not null,
    salary DECIMAL(10, 2) not null,
    birthday TIMESTAMP not null,
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8;
