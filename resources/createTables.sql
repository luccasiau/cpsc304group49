CREATE TABLE customer (
  dlicense    VARCHAR(10) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  cellNum     INT,
  address     VARCHAR(255),
  PRIMARY KEY(dlicense)
);

CREATE TABLE reservation (
  confno      VARCHAR(20)  NOT NULL,
  dlicense    VARCHAR(20)  NOT NULL,
  vtname      VARCHAR(20)  NOT NULL,
  location    VARCHAR(255) NOT NULL,
  city        VARCHAR(255) NOT NULL,
  startdate   DATE         NOT NULL,
  enddate     DATE         NOT NULL,
  PRIMARY KEY(confno)
);