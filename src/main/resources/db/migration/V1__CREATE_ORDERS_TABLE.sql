CREATE TABLE orders (
  id varchar(36) NOT NULL,
  date_hour datetime NOT NULL,
  status varchar(255) NOT NULL,
  PRIMARY KEY (id)
)