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
  PRIMARY KEY (entry_id),
  CONSTRAINT data_table_fk FOREIGN KEY (table_id) REFERENCES table_base.table_list(table_id) ON DELETE CASCADE ON UPDATE CASCADE
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
 * This is a 3-Dimensional Table.
 */
INSERT INTO table_list (user_id, table_name, tags, public) VALUES (1, 'Uni Marks', 'Uni, Marks', true);

INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 1, 1, null, 'Year');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 2, 2, null, 'Term');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 3, 3, null, 'Mark');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 4, 1, 1,    '1991');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 5, 1, 1,    '1992');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 6, 2, 2,    'Winter');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 7, 2, 2,    'Spring');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 8, 2, 2,    'Fall');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 9, 3, 3,    'Assignments');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 10, 3, 9,   'Ass1');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 11, 3, 9,   'Ass2');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 12, 3, 9,   'Ass3');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 13, 3, 3,   'Examinations');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 14, 3, 13,  'Midterm');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 15, 3, 13,  'Final');
INSERT INTO categories (table_id, category_id, tree_id, parent_id, attribute_name) VALUES (1, 16, 3, 3,   'Grade');

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 1,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 2,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 3,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 4,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 5,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 6,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 7,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 8,'65', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 9,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 10,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 11,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 12,'70', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 13,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 14,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 15,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 16,'55', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 17,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 18,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 19,'85', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 20,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 21,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 22,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 23,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 24,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 25,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 26,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 27,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 28,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 29,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 30,'75', 1);

INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 31,'75', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 32,'70', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 33,'65', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 34,'60', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 35,'80', 1);
INSERT INTO table_data (table_id, entry_id, data, type) VALUES (1, 36,'70', 1);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 1, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 2, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 3, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 4, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 5, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 6, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 7, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 8, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 9, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 10, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 11, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 12, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 13, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 14, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 15, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 16, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 17, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 4, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 18, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 19, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 20, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 21, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 22, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 23, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 6, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 24, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 25, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 26, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 27, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 28, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 29, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 7, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 30, 16, 3);



INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 31, 10, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 32, 11, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 9, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 33, 12, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 34, 14, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 13, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 35, 15, 3);

INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 1, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 5, 1);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 2, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 8, 2);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 3, 3);
INSERT INTO data_access_path (table_id, entry_id, category_id, tree_id) VALUES (1, 36, 16, 3);


/*
 * This is a 2-Dimensional Table
 */
INSERT INTO table_list (user_id, table_name, tags, public) VALUES (1, 'Quantifying Fuel-Saving Opportunities from Specific Driving', 'FuelSaving, Driving', true);

INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 1, 1, 'VH', null);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 2, 2, 'Cycle Name', null);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 3, 1, 'KI (1/km)', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 4, 1, 'Distance (mi)', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 5, 1, 'Percent Fuel Savings', 1);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 6, 2, '2012_2', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 7, 2, '2145_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 8, 2, '4234_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 9, 2, '2032_2', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 10, 2, '4171_1', 2);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 11, 1, 'Improved Speed', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 12, 1, 'Decreased Accel', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 13, 1, 'Eliminate Stops', 5);
INSERT INTO categories (table_id, category_id, tree_id, attribute_name, parent_id) VALUES (2, 14, 1, 'Decreased Idle', 5);

INSERT INTO table_data (entry_id, table_id, data, type) VALUES (37, 2, '3.30', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (38, 2, '1.3', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (39, 2, '5.9%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (40, 2, '9.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (41, 2, '29.2%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (42, 2, '17.4%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (43, 2, '0.68', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (44, 2, '11.2', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (45, 2, '2.4%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (46, 2, '0.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (47, 2, '9.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (48, 2, '2.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (49, 2, '0.59', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (50, 2, '58.7', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (51, 2, '8.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (52, 2, '1.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (53, 2, '8.5%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (54, 2, '3.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (55, 2, '0.17', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (56, 2, '57.8', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (57, 2, '21.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (58, 2, '0.3%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (59, 2, '2.7%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (60, 2, '1.2%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (61, 2, '0.07', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (62, 2, '173.9', 6);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (63, 2, '58.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (64, 2, '1.6%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (65, 2, '2.1%', 4);
INSERT INTO table_data (entry_id, table_id, data, type) VALUES (66, 2, '0.5%', 4);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 37, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 37, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 37, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 37, 3, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 38, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 38, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 38, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 38, 4, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 39, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 39, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 39, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 39, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 39, 11, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 40, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 40, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 40, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 40, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 40, 12, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 41, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 41, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 41, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 41, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 41, 13, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 42, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 42, 6, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 42, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 42, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 42, 14, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 43, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 43, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 43, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 43, 3, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 44, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 44, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 44, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 44, 4, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 45, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 45, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 45, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 45, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 45, 11, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 46, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 46, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 46, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 46, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 46, 12, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 47, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 47, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 47, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 47, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 47, 13, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 48, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 48, 7, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 48, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 48, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 48, 14, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 49, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 49, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 49, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 49, 3, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 50, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 50, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 50, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 50, 4, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 51, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 51, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 51, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 51, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 51, 11, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 52, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 52, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 52, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 52, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 52, 12, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 53, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 53, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 53, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 53, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 53, 13, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 54, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 54, 8, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 54, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 54, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 54, 14, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 55, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 55, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 55, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 55, 3, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 56, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 56, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 56, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 56, 4, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 57, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 57, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 57, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 57, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 57, 11, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 58, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 58, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 58, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 58, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 58, 12, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 59, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 59, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 59, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 59, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 59, 13, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 60, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 60, 9, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 60, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 60, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 60, 14, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 61, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 61, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 61, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 61, 3, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 62, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 62, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 62, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 62, 4, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 63, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 63, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 63, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 63, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 63, 11, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 64, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 64, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 64, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 64, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 64, 12, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 65, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 65, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 65, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 65, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 65, 13, 1);

INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 66, 2, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 66, 10, 2);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 66, 1, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 66, 5, 1);
INSERT INTO data_access_path(table_id, entry_id, category_id, tree_id) VALUES (2, 66, 14, 1);


