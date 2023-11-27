#!/bin/bash

USER="zhu"
PASSWORD="zhuyingcong"
SCHEMA="test"
TABLE="orders"

create_db_sql="create schema if not exists ${SCHEMA}"
mysql -u${USER} -p${PASSWORD} -e "${create_db_sql}"

drop_table_sql="DROP TABLE IF EXISTS ${TABLE}"
create_table_sql="
CREATE TABLE ${TABLE} (
	  id int NOT NULL AUTO_INCREMENT,
    origin_latitude varchar(100) NOT NULL,
    origin_longitude varchar(100) NOT NULL,
    destination_latitude varchar(100) NOT NULL,
    destination_longitude varchar(100) NOT NULL,
    distance int NOT NULL,
    status tinyint NOT NULL,
    create_time bigint DEFAULT NULL,
    update_time bigint DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB
"
mysql -u${USER} -p${PASSWORD} ${SCHEMA} -e "${drop_table_sql}"
mysql -u${USER} -p${PASSWORD} ${SCHEMA} -e "${create_table_sql}"

exit 0