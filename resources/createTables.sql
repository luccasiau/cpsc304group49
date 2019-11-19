CREATE TABLE customer (
  dlicense    VARCHAR(10) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  cellNum     INT,
  address     VARCHAR(255),
  PRIMARY KEY(dlicense)
);