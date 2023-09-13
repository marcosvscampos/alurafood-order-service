CREATE TABLE order_items (
  id varchar(36) NOT NULL,
  description varchar(255) DEFAULT NULL,
  amount int(11) NOT NULL,
  order_id varchar(36) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id)
)