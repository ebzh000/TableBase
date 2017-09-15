DROP DATABASE IF EXISTS table_base;
CREATE DATABASE table_base;

USE table_base;

CREATE TABLE table_base.tablelist(
	table_id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	PRIMARY KEY (table_id)
) ENGINE INNODB;

CREATE TABLE table_base.categories (
	table_id INT NOT NULL, 
	category_id INT NOT NULL, 
	attribute_name VARCHAR(400) NOT NULL, 
	parent_id INT, 
	type TINYINT,
	PRIMARY KEY (category_id, table_id),
	CONSTRAINT cat_table_fk FOREIGN KEY (table_id)
		REFERENCES table_base.tablelist(table_id)
		ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.tabledata (
	table_id INT NOT NULL ,
	access_id INT NOT NULL,
	header_id INT NOT NULL,
	data VARCHAR(500) NOT NULL,
	PRIMARY KEY (table_id, access_id, header_id),
	CONSTRAINT data_table_fk FOREIGN KEY (table_id)
		REFERENCES table_base.tablelist(table_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT access_fk FOREIGN KEY (access_id) 
		REFERENCES table_base.categories(category_id) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header_fk FOREIGN KEY (header_id) 
		REFERENCES table_base.categories(category_id) 
		ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

