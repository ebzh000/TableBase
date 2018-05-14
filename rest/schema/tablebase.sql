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
  tree_id INT NOT NULL,
  attribute_name VARCHAR(400) NOT NULL,
  parent_id INT,
  PRIMARY KEY (category_id, table_id),
  CONSTRAINT cat_table_fk FOREIGN KEY (table_id) REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.table_data (
  table_id INT NOT NULL,
  entry_id INT NOT NULL AUTO_INCREMENT,
  data VARCHAR(500) NOT NULL,
  type INT NOT NULL,
  PRIMARY KEY (entry_id, table_id),
  CONSTRAINT data_table_fk FOREIGN KEY (table_id)	REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;

CREATE TABLE table_base.data_access_path (
  id INT NOT NULL AUTO_INCREMENT,
  table_id INT NOT NULL,
  entry_id INT NOT NULL,
  category_id INT NOT NULL,
  tree_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT table_id_fk FOREIGN KEY (table_id) REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT entry_id_fk FOREIGN KEY (entry_id) REFERENCES table_base.table_data(entry_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES table_base.categories(category_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE INNODB;


/*
 * This is a 2-Dimensional Table
 */
INSERT INTO table_list (user_id, table_name, tags, public) VALUES (1, 'Quantifying Fuel-Saving Opportunities from Specific Driving', 'FuelSaving, Driving', true);

INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 1, 1, 'VH', null);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 2, 2, 'Cycle Name', null);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 3, 1, 'KI (1/km)', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 4, 1, 'Distance (mi)', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 5, 1, 'Percent Fuel Savings', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 6, 2, '2012_2', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 7, 2, '2145_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 8, 2, '4234_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 9, 2, '2032_2', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 10, 2, '4171_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 11, 1, 'Improved Speed', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 12, 1, 'Decreased Accel', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 13, 1, 'Eliminate Stops', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (1, 14, 1, 'Decreased Idle', 5);

INSERT INTO table_data (entry_id, table_id, data, type) VALUES (1, 1, '3.30', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (2, 1, '1.3', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (3, 1, '5.9%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (4, 1, '9.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (5, 1, '29.2%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (6, 1, '17.4%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (7, 1, '0.68', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (8, 1, '11.2', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (9, 1, '2.4%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (10, 1, '0.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (11, 1, '9.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (12, 1, '2.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (13, 1, '0.59', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (14, 1, '58.7', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (15, 1, '8.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (16, 1, '1.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (17, 1, '8.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (18, 1, '3.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (19, 1, '0.17', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (20, 1, '57.8', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (21, 1, '21.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (22, 1, '0.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (23, 1, '2.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (24, 1, '1.2%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (25, 1, '0.07', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (26, 1, '173.9', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (27, 1, '58.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (28, 1, '1.6%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (29, 1, '2.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (30, 1, '0.5%', 4);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 1, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 1, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 1, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 1, 3, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 2, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 2, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 2, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 2, 4, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 3, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 3, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 3, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 3, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 3, 11, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 4, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 4, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 4, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 4, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 4, 12, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 5, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 5, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 5, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 5, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 5, 13, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 6, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 6, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 6, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 6, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 6, 14, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 7, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 7, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 7, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 7, 3, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 8, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 8, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 8, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 8, 4, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 9, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 9, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 9, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 9, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 9, 11, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 10, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 10, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 10, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 10, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 10, 12, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 11, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 11, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 11, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 11, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 11, 13, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 12, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 12, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 12, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 12, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 12, 14, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 13, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 13, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 13, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 13, 3, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 14, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 14, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 14, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 14, 4, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 15, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 15, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 15, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 15, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 15, 11, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 16, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 16, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 16, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 16, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 16, 12, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 17, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 17, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 17, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 17, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 17, 13, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 18, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 18, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 18, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 18, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 18, 14, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 19, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 19, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 19, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 19, 3, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 20, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 20, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 20, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 20, 4, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 21, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 21, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 21, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 21, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 21, 11, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 22, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 22, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 22, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 22, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 22, 12, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 23, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 23, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 23, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 23, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 23, 13, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 24, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 24, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 24, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 24, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 24, 14, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 25, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 25, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 25, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 25, 3, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 26, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 26, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 26, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 26, 4, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 27, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 27, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 27, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 27, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 27, 11, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 28, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 28, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 28, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 28, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 28, 12, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 29, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 29, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 29, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 29, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 29, 13, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 30, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 30, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 30, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 30, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (1, 30, 14, 1);


/*
 * This is a 3-Dimensional Table.
 */
INSERT INTO table_list (user_id, table_name, tags, public) VALUES (1, 'Uni Marks', 'Uni, Marks', true);

INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 1, 1, null, 'Year');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 2, 2, null, 'Term');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 3, 3, null, 'Mark');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 4, 1, 1,    '1991');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 5, 1, 1,    '1992');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 6, 2, 2,    'Winter');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 7, 2, 2,    'Spring');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 8, 2, 2,    'Fall');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 9, 3, 3,    'Assignments');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 10, 3, 9,   'Ass1');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 11, 3, 9,   'Ass2');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 12, 3, 9,   'Ass3');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 13, 3, 3,   'Examinations');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 14, 3, 13,  'Midterm');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 15, 3, 13,  'Final');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (2, 16, 3, 3,   'Grade');

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 1,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 2,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 3,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 4,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 5,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 6,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 7,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 8,'65', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 9,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 10,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 11,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 12,'70', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 13,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 14,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 15,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 16,'55', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 17,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 18,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 19,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 20,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 21,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 22,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 23,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 24,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 25,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 26,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 27,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 28,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 29,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 30,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 31,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 32,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 33,'65', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 34,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 35,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (2, 36,'70', 1);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 1, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 2, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 3, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 4, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 5, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 6, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 7, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 8, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 9, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 10, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 11, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 12, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 13, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 14, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 15, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 16, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 17, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 18, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 19, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 20, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 21, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 22, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 23, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 24, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 25, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 26, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 27, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 28, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 29, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 30, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 31, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 32, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 33, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 34, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 35, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (2, 36, 16, 3);