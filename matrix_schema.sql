DROP DATABASE IF EXISTS table_base;
CREATE DATABASE table_base;

USE table_base;

CREATE TABLE table_base.tablelist(
	table_id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	table_name VARCHAR(500) NOT NULL,
	tags TEXT,
	PRIMARY KEY (table_id)
) ENGINE INNODB;

CREATE TABLE table_base.categories (
	table_id INT NOT NULL,
	category_id INT NOT NULL,
	attribute_name VARCHAR(400) NOT NULL,
	parent_id INT,
	type INT NOT NULL,
	PRIMARY KEY (category_id, table_id),
	CONSTRAINT cat_table_fk FOREIGN KEY (table_id)
	REFERENCES table_base.tablelist(table_id)
		ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.tabledata (
	table_id INT NOT NULL ,
	access_id INT NOT NULL,
	header1_id INT NOT NULL,
	header2_id INT,
	header3_id INT,
	header4_id INT,
	header5_id INT,
	header6_id INT,
	header7_id INT,
	header8_id INT,
	header9_id INT,
	header10_id INT,
	data VARCHAR(500) NOT NULL,
	PRIMARY KEY (table_id, access_id, header1_id),
	CONSTRAINT data_table_fk FOREIGN KEY (table_id)
	REFERENCES table_base.tablelist(table_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT access_fk FOREIGN KEY (access_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header1_fk FOREIGN KEY (header1_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header2_fk FOREIGN KEY (header2_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header3_fk FOREIGN KEY (header3_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header4_fk FOREIGN KEY (header4_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header5_fk FOREIGN KEY (header5_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header6_fk FOREIGN KEY (header6_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header7_fk FOREIGN KEY (header7_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header8_fk FOREIGN KEY (header8_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header9_fk FOREIGN KEY (header9_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT header10_fk FOREIGN KEY (header10_id)
	REFERENCES table_base.categories(category_id)
		ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

INSERT INTO table_base.tablelist (user_id, table_name, tags) VALUES (1, 'Quantifying Fuel-Saving Opportunities from Specific Driving', 'FuelSaving, Driving');

INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 1, 'VH', null, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 2, 'Cycle Name', null, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 3, 'KI (1/km)', 1, 1);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 4, 'Distance (mi)', 1, 2);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 5, 'Percent Fuel Savings', 1, 4);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 6, '2012_2', 2, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 7, '2145_1', 2, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 8, '4234_1', 2, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 9, '2032_2', 2, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 10, '4171_1', 2, 0);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 11, 'Improved Speed', 5, 4);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 12, 'Decreased Accel', 5, 4);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 13, 'Eliminate Stops', 5, 4);
INSERT INTO table_base.categories (table_id, category_id, attribute_name, parent_id, type) VALUES (1, 14, 'Decreased Idle', 5, 4);

INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 3, null, null, null, null, null, null, null, null, null, '3.30');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 4, null, null, null, null, null, null, null, null, null, '1.3');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 11, null, null, null, null, null, null, null, null, null, '5.9%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 12, null, null, null, null, null, null, null, null, null, '9.5%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 13, null, null, null, null, null, null, null, null, null, '29.2%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 6, 14, null, null, null, null, null, null, null, null, null, '17.4%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 3, null, null, null, null, null, null, null, null, null, '0.68');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 4, null, null, null, null, null, null, null, null, null, '11.2');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 11, null, null, null, null, null, null, null, null, null, '2.4%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 12, null, null, null, null, null, null, null, null, null, '0.1%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 13, null, null, null, null, null, null, null, null, null, '9.5%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 7, 14, null, null, null, null, null, null, null, null, null, '2.7%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 3, null, null, null, null, null, null, null, null, null, '0.59');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 4, null, null, null, null, null, null, null, null, null, '58.7');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 11, null, null, null, null, null, null, null, null, null, '8.5%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 12, null, null, null, null, null, null, null, null, null, '1.3%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 13, null, null, null, null, null, null, null, null, null, '8.5%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 8, 14, null, null, null, null, null, null, null, null, null, '3.3%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 3, null, null, null, null, null, null, null, null, null, '0.17');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 4, null, null, null, null, null, null, null, null, null, '57.8');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 11, null, null, null, null, null, null, null, null, null, '21.7%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 12, null, null, null, null, null, null, null, null, null, '0.3%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 13, null, null, null, null, null, null, null, null, null, '2.7%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 9, 14, null, null, null, null, null, null, null, null, null, '1.2%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 3, null, null, null, null, null, null, null, null, null, '0.07');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 4, null, null, null, null, null, null, null, null, null, '173.9');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 11, null, null, null, null, null, null, null, null, null, '58.1%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 12, null, null, null, null, null, null, null, null, null, '1.6%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 13, null, null, null, null, null, null, null, null, null, '2.1%');
INSERT INTO table_base.tabledata (table_id, access_id, header1_id, header2_id, header3_id, header4_id, header5_id, header6_id, header7_id, header8_id, header9_id, header10_id, data) VALUES (1, 10, 14, null, null, null, null, null, null, null, null, null, '0.5%');