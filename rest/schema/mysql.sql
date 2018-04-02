DROP DATABASE IF EXISTS table_base;
CREATE DATABASE table_base;

USE table_base;

CREATE TABLE table_base.table_list(
  table_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  table_name VARCHAR(500) NOT NULL,
  tags TEXT,
  public BOOLEAN NOT NULL,
  PRIMARY KEY (table_id)
) ENGINE INNODB;

CREATE TABLE table_base.categories (
  table_id INT NOT NULL,
  category_id INT NOT NULL AUTO_INCREMENT,
  attribute_name VARCHAR(400) NOT NULL,
  parent_id INT,
  type INT NOT NULL,
  PRIMARY KEY (category_id, table_id),
  CONSTRAINT cat_table_fk FOREIGN KEY (table_id) REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT parent_id_fk FOREIGN KEY (parent_id) REFERENCES categories(category_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.table_data (
  table_id INT NOT NULL,
  entry_id INT NOT NULL AUTO_INCREMENT,
  data VARCHAR(500) NOT NULL,
  PRIMARY KEY (entry_id, table_id),
  CONSTRAINT data_table_fk FOREIGN KEY (table_id)	REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.data_access_path (
  table_id INT NOT NULL,
  entry_id INT NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (table_id, entry_id, category_id),
  CONSTRAINT table_id_fk FOREIGN KEY (table_id) REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT entry_id_fk FOREIGN KEY (entry_id) REFERENCES table_base.table_data(entry_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES table_base.categories(category_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

INSERT INTO table_base.table_list (user_id, table_name, tags, public) VALUES (1, 'Quantifying Fuel-Saving Opportunities from Specific Driving', 'FuelSaving, Driving', true);

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

INSERT INTO table_base.table_data (table_id, data) VALUES (1, '3.30');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '1.3');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '5.9%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '9.5%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '29.2%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '17.4%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.68');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '11.2');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '2.4%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.1%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '9.5%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '2.7%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.59');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '58.7');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '8.5%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '1.3%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '8.5%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '3.3%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.17');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '57.8');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '21.7%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.3%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '2.7%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '1.2%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.07');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '173.9');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '58.1%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '1.6%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '2.1%');
INSERT INTO table_base.table_data (table_id, data) VALUES (1, '0.5%');

INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 1, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 1, 3);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 2, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 2, 4);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 3, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 3, 11);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 4, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 4, 12);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 5, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 5, 13);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 6, 6);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 6, 14);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 7, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 7, 3);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 8, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 8, 4);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 9, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 9, 11);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 10, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 10, 12);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 11, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 11, 13);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 12, 7);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 12, 14);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 13, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 13, 3);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 14, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 14, 4);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 15, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 15, 11);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 16, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 16, 12);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 17, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 17, 13);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 18, 8);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 18, 14);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 19, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 19, 3);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 20, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 20, 4);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 21, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 21, 11);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 22, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 22, 12);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 23, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 23, 13);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 24, 9);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 24, 14);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 25, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 25, 3);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 26, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 26, 4);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 27, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 27, 11);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 28, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 28, 12);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 29, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 29, 13);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 30, 10);
INSERT INTO table_base.data_access_path(table_id, entry_id, category_id) VALUES (1, 30, 14);